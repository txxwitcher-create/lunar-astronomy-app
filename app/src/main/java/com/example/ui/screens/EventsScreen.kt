package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.ui.components.*
import com.example.ui.theme.EclipseCopper
import com.example.ui.viewmodel.LunarUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private enum class EventFilter(val label: String) {
    ALL("All"),
    ECLIPSES("Eclipses"),
    FULL_MOONS("Full & Super"),
    NEW_MOONS("New Moons"),
    METEORS("Meteor Showers")
}

@Composable
fun EventsListScreen(
    uiState: LunarUiState,
    onBack: () -> Unit,
    onSelectEvent: (String) -> Unit
) {
    var filter by remember { mutableStateOf(EventFilter.ALL) }
    val filtered = remember(uiState.upcomingEvents, filter) {
        when (filter) {
            EventFilter.ALL -> uiState.upcomingEvents
            EventFilter.ECLIPSES -> uiState.upcomingEvents.filter { it.eclipseStages != null }
            EventFilter.FULL_MOONS -> uiState.upcomingEvents.filter {
                it.type == LunarEventType.FULL_MOON || it.type == LunarEventType.SUPERMOON
            }
            EventFilter.NEW_MOONS -> uiState.upcomingEvents.filter { it.type == LunarEventType.NEW_MOON }
            EventFilter.METEORS -> uiState.upcomingEvents.filter { it.type == LunarEventType.METEOR_SHOWER }
        }
    }
    val dateFmt = remember { DateTimeFormatter.ofPattern("EEE, MMM d, yyyy", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = "Astronomy Events", onBack = onBack)

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(EventFilter.entries) { f ->
                FilterChip(
                    selected = filter == f,
                    onClick = { filter = f },
                    label = { Text(f.label) }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filtered, key = { it.id }) { ev ->
                val dt = Instant.ofEpochMilli(ev.timestampMillis).atZone(ZoneId.systemDefault())
                val isEclipse = ev.eclipseStages != null

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(195.dp)
                        .clip(MaterialTheme.shapes.large)
                        .border(
                            0.75.dp,
                            if (isEclipse) EclipseCopper.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.65f),
                            MaterialTheme.shapes.large
                        )
                        .clickable { onSelectEvent(ev.id) }
                        .testTag("event_card_${ev.id}")
                ) {
                    AstronomyArtworkImage(
                        imageUrl = ev.imageUrl,
                        fallbackType = ev.fallbackVisualType,
                        contentDescription = ev.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(Color(0x3304060B), Color(0x8804060B), Color(0xEE04060B))))
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LunarPillBadge(
                                text = ev.type.label.uppercase(),
                                containerColor = if (isEclipse) EclipseCopper.copy(alpha = 0.28f) else Color(0xAA04060B),
                                contentColor = if (isEclipse) EclipseCopper else Color(0xFFDDE7FF)
                            )
                            LunarPillBadge(
                                text = AstronomyEngine.formatCountdown(ev.timestampMillis, uiState.nowMillis).uppercase(),
                                containerColor = Color(0xAA04060B),
                                contentColor = Color(0xFFF5F7FC)
                            )
                        }
                        Column {
                            Text(ev.title, style = MaterialTheme.typography.headlineSmall, color = Color(0xFFF5F7FC))
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "${dt.format(dateFmt)} • ${formatInstantTime(ev.timestampMillis, uiState.settings.use24HourTime)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFB8C7E5)
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = ev.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFD5DEF0),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun EventDetailScreen(
    eventId: String,
    uiState: LunarUiState,
    onBack: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onEnableEventNotification: (LunarEvent) -> Unit
) {
    val allEvents = remember(uiState.upcomingEvents) {
        (uiState.upcomingEvents + AstronomyEngine.getKnownLunarEclipses()).distinctBy { it.id }
    }
    val event = remember(eventId, allEvents) {
        allEvents.find { it.id == eventId } ?: allEvents.first()
    }
    val settings = uiState.settings
    val isFav = event.id in settings.favoriteEventIds
    val zone = ZoneId.systemDefault()
    val dt = remember(event.timestampMillis) { Instant.ofEpochMilli(event.timestampMillis).atZone(zone) }
    val dateFmt = remember { DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.getDefault()) }

    val alertsActive = when (event.type) {
        LunarEventType.FULL_MOON, LunarEventType.SUPERMOON -> settings.notifications.fullMoonAlerts
        LunarEventType.NEW_MOON -> settings.notifications.newMoonAlerts
        LunarEventType.TOTAL_LUNAR_ECLIPSE,
        LunarEventType.PARTIAL_LUNAR_ECLIPSE,
        LunarEventType.PENUMBRAL_LUNAR_ECLIPSE -> settings.notifications.lunarEclipseAlerts
        else -> settings.notifications.majorEventsAlerts
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(
            title = event.type.label,
            onBack = onBack,
            isFavorite = isFav,
            onToggleFavorite = { onToggleFavorite(event.id) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = event.imageUrl,
                        fallbackType = event.fallbackVisualType,
                        contentDescription = event.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.background)))
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        LunarPillBadge(
                            text = "IN ${AstronomyEngine.formatCountdown(event.timestampMillis, uiState.nowMillis).uppercase()}",
                            containerColor = if (event.eclipseStages != null) EclipseCopper.copy(alpha = 0.25f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            contentColor = if (event.eclipseStages != null) EclipseCopper else MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(event.title, style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onBackground)
                        Text(event.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    LunarHairlineDivider()
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TelemetryStatBlock("Exact Date", dt.format(dateFmt), modifier = Modifier.weight(1.3f))
                        TelemetryStatBlock(
                            label = "Local Time",
                            value = formatInstantTime(event.timestampMillis, settings.use24HourTime),
                            sublabel = zone.id,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    event.distanceKm?.let { dist ->
                        Spacer(modifier = Modifier.height(12.dp))
                        TelemetryStatBlock("Earth–Moon Distance", formatDistance(dist, settings.distanceUnit))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = { onEnableEventNotification(event) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("event_reminder_button")
                    ) {
                        Icon(
                            imageVector = if (alertsActive) Icons.Outlined.NotificationsActive else Icons.Outlined.NotificationsNone,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (alertsActive) "Alerts Active For This Event Category" else "Enable Reminder Alert")
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    LunarHairlineDivider()
                }
            }

            // Detailed Lunar Eclipse Stage Timeline (Earth's shadow, penumbra, partial, totality, max, end)
            event.eclipseStages?.let { stages ->
                item {
                    LunarSurfaceContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "ECLIPSE STAGES (YOUR LOCAL TIMEZONE)",
                                style = MaterialTheme.typography.labelSmall,
                                color = EclipseCopper,
                                letterSpacing = 1.4.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Visibility region: ${stages.visibilityRegion}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            EclipseStageRow("Penumbral Eclipse Begins", formatInstantTime(stages.penumbralStartMillis, settings.use24HourTime), "Moon enters Earth's faint outer shadow")
                            stages.partialStartMillis?.let {
                                EclipseStageRow("Partial Eclipse Begins", formatInstantTime(it, settings.use24HourTime), "Earth's dark umbra bites across the limb")
                            }
                            stages.totalityStartMillis?.let {
                                EclipseStageRow("Totality Begins", formatInstantTime(it, settings.use24HourTime), "Full immersion in umbra — red glow begins", highlight = true)
                            }
                            EclipseStageRow("Maximum Eclipse", formatInstantTime(stages.maximumEclipseMillis, settings.use24HourTime), "Deepest shadow immersion", highlight = true)
                            stages.totalityEndMillis?.let {
                                EclipseStageRow("Totality Ends", formatInstantTime(it, settings.use24HourTime), "Bright sunlit crescent re-emerges")
                            }
                            stages.partialEndMillis?.let {
                                EclipseStageRow("Partial Eclipse Ends", formatInstantTime(it, settings.use24HourTime), "Moon exits Earth's dark umbra")
                            }
                            EclipseStageRow("Penumbral Eclipse Ends", formatInstantTime(stages.penumbralEndMillis, settings.use24HourTime), "Full normal lunar brightness restored")
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    EventInfoSection("Visibility", event.visibilitySummary)
                    EventInfoSection("Scientific Explanation", event.scientificExplanation)
                    EventInfoSection("Observation Guide", event.observationGuide)
                    EventInfoSection("Photography Tips", event.photographyTips)
                }
            }
        }
    }
}

@Composable
private fun EclipseStageRow(stage: String, time: String, desc: String, highlight: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stage,
                style = MaterialTheme.typography.titleSmall,
                color = if (highlight) EclipseCopper else MaterialTheme.colorScheme.onSurface
            )
            Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = time,
            style = MaterialTheme.typography.labelMedium,
            color = if (highlight) EclipseCopper else MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun EventInfoSection(title: String, body: String) {
    Column {
        Text(title.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
        Spacer(modifier = Modifier.height(6.dp))
        Text(body, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f))
    }
}
