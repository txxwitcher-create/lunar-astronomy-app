package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.data.LunarContentCatalog
import com.example.localization.LocalLunarStrings
import com.example.ui.components.*
import com.example.ui.theme.ClearSkyEmerald
import com.example.ui.theme.EclipseCopper
import com.example.ui.theme.EditorialSerif
import com.example.ui.theme.TelemetryMono
import com.example.ui.viewmodel.LunarUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    uiState: LunarUiState,
    onOpenCalendar: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenPhaseDetail: (LunarPhaseType) -> Unit,
    onOpenAllPhases: () -> Unit,
    onOpenExplore: () -> Unit,
    onOpenFeatureDetail: (String) -> Unit,
    onOpenTonight: () -> Unit,
    onOpenEvents: () -> Unit,
    onOpenEventDetail: (String) -> Unit,
    onOpenLearn: () -> Unit,
    onOpenArticleDetail: (String) -> Unit,
    onOpenPhotography: () -> Unit,
    onOpenWallpapers: () -> Unit,
    onOpenFavorites: () -> Unit
) {
    val strings = LocalLunarStrings.current
    val snapshot = uiState.currentSnapshot
    val settings = uiState.settings
    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.getDefault()) }
    val dateText = remember(snapshot.localDate) { snapshot.localDate.format(dateFormatter) }
    val nextEvent = uiState.upcomingEvents.firstOrNull()
    val nextMajorPhase = remember(uiState.nowMillis) { AstronomyEngine.findNextMajorPhase(uiState.nowMillis) }

    var factIndex by remember { mutableIntStateOf(snapshot.localDate.dayOfYear % LunarContentCatalog.astronomyFacts.size) }
    val currentFact = LunarContentCatalog.astronomyFacts[factIndex % LunarContentCatalog.astronomyFacts.size]
    val featuredLocation = remember(snapshot.localDate) {
        LunarContentCatalog.lunarFeatures[snapshot.localDate.dayOfYear % LunarContentCatalog.lunarFeatures.size]
    }
    val featuredDiscovery = remember(snapshot.localDate) {
        LunarContentCatalog.featuredDiscoveries[snapshot.localDate.dayOfYear % LunarContentCatalog.featuredDiscoveries.size]
    }
    val featuredPhotoGuide = LunarContentCatalog.photoGuides.first()

    val atmosphericHeading = remember(snapshot.phaseType) {
        when (snapshot.phaseType) {
            LunarPhaseType.NEW_MOON -> "Velvet Darkness, Stars Awakened"
            LunarPhaseType.WAXING_CRESCENT -> "A Silver Bow Returns at Dusk"
            LunarPhaseType.FIRST_QUARTER -> "Crisp Shadows Across the Terminator"
            LunarPhaseType.WAXING_GIBBOUS -> "Gathering Light Toward Opposition"
            LunarPhaseType.FULL_MOON -> "Sovereign Radiance Across the Night"
            LunarPhaseType.WANING_GIBBOUS -> "Golden Dusk Sweeps the Lunar Seas"
            LunarPhaseType.LAST_QUARTER -> "Silent Beacon of the Pre-Dawn Sky"
            LunarPhaseType.WANING_CRESCENT -> "Delicate Sliver Before Renewal"
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("home_screen_list"),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        // TOP MINIMAL BRAND BAR
        item(key = "top_bar") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .displayCutoutPadding()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "LUNAR",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = EditorialSerif,
                            letterSpacing = 6.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = strings.tagline,
                        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.4.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = onOpenCalendar,
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("home_calendar_shortcut")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = strings.navCalendar,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("home_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = strings.navSettings,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }

        // A. MAIN MOON HERO
        item(key = "moon_hero") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.55f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Date & Location strip
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable(onClick = onOpenSettings)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = dateText.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            letterSpacing = 1.2.sp
                        )
                        Text("•", color = MaterialTheme.colorScheme.primary)
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = settings.location.name,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = atmosphericHeading,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Centerpiece Realistic Moon Visual
                    Box(
                        modifier = Modifier
                            .size(248.dp)
                            .clip(CircleShape)
                            .clickable { onOpenPhaseDetail(snapshot.phaseType) }
                            .testTag("home_moon_hero_disc"),
                        contentAlignment = Alignment.Center
                    ) {
                        RealisticMoonDisc(
                            phaseFraction = snapshot.phaseFraction,
                            sizeDp = 248.dp,
                            includeOuterHalo = true,
                            contentDescription = "${snapshot.phaseType.displayName}, ${snapshot.illuminationPercent.roundToInt()}% illuminated",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = strings.phaseName(snapshot.phaseType),
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Illumination, Age & Waxing/Waning inline telemetry
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LunarPillBadge(
                            text = "${snapshot.illuminationPercent.roundToInt()}% ${strings.illuminated.uppercase()}",
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${strings.lunarAge} ${String.format("%.1f", snapshot.lunarAgeDays)}${strings.daysUnit}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text("•", color = MaterialTheme.colorScheme.outline)
                        Text(
                            text = if (snapshot.isWaxing) strings.waxing else strings.waning,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // B. TODAY'S LUNAR DETAILS (Compact architectural telemetry strip)
        item(key = "lunar_details_strip") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                LunarHairlineDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TelemetryStatBlock(
                        label = strings.moonrise,
                        value = formatInstantTime(snapshot.moonriseMillis, settings.use24HourTime),
                        sublabel = settings.location.name,
                        modifier = Modifier.weight(1f)
                    )
                    TelemetryStatBlock(
                        label = strings.moonset,
                        value = formatInstantTime(snapshot.moonsetMillis, settings.use24HourTime),
                        sublabel = settings.location.name,
                        modifier = Modifier.weight(1f)
                    )
                    TelemetryStatBlock(
                        label = strings.distance,
                        value = formatDistance(snapshot.distanceKm, settings.distanceUnit),
                        sublabel = String.format("Ø %.1f'", snapshot.angularDiameterArcmin),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TelemetryStatBlock(
                        label = strings.altitudeNow,
                        value = "${snapshot.altitudeDeg.roundToInt()}° ${snapshot.compassDirection}",
                        sublabel = if (snapshot.isAboveHorizon) strings.aboveHorizon else strings.belowHorizon,
                        modifier = Modifier.weight(1f)
                    )
                    TelemetryStatBlock(
                        label = strings.lunarAge,
                        value = if (snapshot.isWaxing) strings.waxing else strings.waning,
                        sublabel = snapshot.zodiacSign,
                        modifier = Modifier.weight(1f)
                    )
                    TelemetryStatBlock(
                        label = strings.nextPhase,
                        value = strings.phaseName(nextMajorPhase.first),
                        sublabel = AstronomyEngine.formatCountdown(nextMajorPhase.second, uiState.nowMillis),
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LunarHairlineDivider()
            }
        }

        // QUICK ACCESS GATEWAYS (Phases, Events, Learn, Photography, Wallpapers, Favorites)
        item(key = "quick_gateways") {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                item { QuickGatewayChip(Icons.Outlined.Brightness2, strings.homePhaseGuide, onOpenAllPhases, "gateway_phases") }
                item { QuickGatewayChip(Icons.Outlined.AutoAwesome, strings.eclipses, onOpenEvents, "gateway_events") }
                item { QuickGatewayChip(Icons.Outlined.MenuBook, strings.learnTitle, onOpenLearn, "gateway_learn") }
                item { QuickGatewayChip(Icons.Outlined.PhotoCamera, strings.homePhotography, onOpenPhotography, "gateway_photo") }
                item { QuickGatewayChip(Icons.Outlined.Wallpaper, strings.homeWallpapers, onOpenWallpapers, "gateway_wallpapers") }
                item { QuickGatewayChip(Icons.Outlined.BookmarkBorder, strings.favorites, onOpenFavorites, "gateway_favorites") }
            }
        }

        // 1. CURRENT MOON OVERVIEW
        item(key = "current_moon_overview") {
            val phaseContent = LunarContentCatalog.phaseDetails[snapshot.phaseType]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                LunarSectionHeader(
                    eyebrow = strings.homePhaseGuide,
                    title = phaseContent?.headline ?: strings.phaseName(snapshot.phaseType),
                    actionLabel = strings.homeFullGuide,
                    onActionClick = { onOpenPhaseDetail(snapshot.phaseType) }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = phaseContent?.explanation ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f)
                )
            }
        }

        // 2. NEXT LUNAR EVENT WITH COUNTDOWN (Photographic Hero Card)
        if (nextEvent != null) {
            item(key = "next_lunar_event") {
                val eventDateStr = remember(nextEvent.timestampMillis, settings.use24HourTime) {
                    val dt = Instant.ofEpochMilli(nextEvent.timestampMillis).atZone(ZoneId.systemDefault())
                    "${dt.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))} • ${formatInstantTime(nextEvent.timestampMillis, settings.use24HourTime)}"
                }
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                    LunarSectionHeader(
                        eyebrow = strings.homeNextEvent,
                        title = strings.eclipses,
                        actionLabel = strings.homeAllEvents,
                        onActionClick = onOpenEvents
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(MaterialTheme.shapes.large)
                            .border(0.75.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.65f), MaterialTheme.shapes.large)
                            .clickable { onOpenEventDetail(nextEvent.id) }
                            .testTag("home_next_event_card")
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = nextEvent.imageUrl,
                            fallbackType = nextEvent.fallbackVisualType,
                            contentDescription = nextEvent.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color(0x3304060B), Color(0x8804060B), Color(0xEE04060B))
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LunarPillBadge(
                                    text = nextEvent.type.label.uppercase(),
                                    containerColor = if (nextEvent.eclipseStages != null) EclipseCopper.copy(alpha = 0.28f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.22f),
                                    contentColor = if (nextEvent.eclipseStages != null) EclipseCopper else Color(0xFFDCE7FF)
                                )
                                LunarPillBadge(
                                    text = "IN ${AstronomyEngine.formatCountdown(nextEvent.timestampMillis, uiState.nowMillis).uppercase()}",
                                    containerColor = Color(0xAA04060B),
                                    contentColor = Color(0xFFF5F7FC)
                                )
                            }
                            Column {
                                Text(
                                    text = nextEvent.title,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color(0xFFF5F7FC)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = eventDateStr,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color(0xFFBAC7E0)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = nextEvent.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFD0D9EB),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. TONIGHT'S VIEWING & TODAY'S SKY
        item(key = "tonight_viewing") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.homeTonightViewing,
                    title = strings.celestialObjects,
                    actionLabel = strings.homeFullGuide,
                    onActionClick = onOpenTonight
                )
                Spacer(modifier = Modifier.height(12.dp))
                LunarSurfaceContainer(
                    onClick = onOpenTonight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_tonight_card")
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = uiState.twilightInfo.currentSkyStage,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "Dark window: ${uiState.twilightInfo.bestObservationWindow}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            when (val w = uiState.weatherState) {
                                is WeatherUiState.Available -> {
                                    val clear = w.weather.cloudCoverPercent <= 35
                                    LunarPillBadge(
                                        text = "${w.weather.cloudCoverPercent}% CLOUDS",
                                        containerColor = (if (clear) ClearSkyEmerald else MaterialTheme.colorScheme.primary).copy(alpha = 0.16f),
                                        contentColor = if (clear) ClearSkyEmerald else MaterialTheme.colorScheme.primary
                                    )
                                }
                                is WeatherUiState.Loading -> {
                                    LunarPillBadge(text = "CHECKING SKY")
                                }
                                is WeatherUiState.Unavailable -> {
                                    LunarPillBadge(
                                        text = "ASTRONOMY ONLY",
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        val advice = when (val w = uiState.weatherState) {
                            is WeatherUiState.Available -> {
                                val skyNote = when {
                                    w.weather.cloudCoverPercent <= 25 -> "Clear skies (${w.weather.weatherDescription.lowercase()}) favor sharp lunar and planetary viewing."
                                    w.weather.cloudCoverPercent <= 60 -> "Partly cloudy (${w.weather.cloudCoverPercent}% cover)—watch for clear breaks around moonrise."
                                    else -> "Heavy cloud cover (${w.weather.cloudCoverPercent}%) currently limits viewing; check back after astronomical twilight."
                                }
                                "$skyNote ${formatTemperature(w.weather.temperatureCelsius, settings.distanceUnit)}."
                            }
                            else -> "Moon is currently ${if (snapshot.isAboveHorizon) "above the ${snapshot.compassDirection} horizon at ${snapshot.altitudeDeg.roundToInt()}°" else "below your local horizon"}. Tap to view calculated twilight and visible sky targets."
                        }
                        Text(
                            text = advice,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Today's Sky horizontal strip of calculated celestial objects
                val visibleTargets = uiState.tonightSkyObjects.take(5)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(visibleTargets, key = { it.id }) { obj ->
                        LunarSurfaceContainer(
                            onClick = onOpenTonight,
                            modifier = Modifier.width(176.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = obj.type.label.uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = obj.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = obj.visibilityLabel,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }

        // 4 & 5. EXPLORE THE MOON & FEATURED LUNAR LOCATION
        item(key = "explore_and_featured_location") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.homeExploreMoon,
                    title = strings.exploreTitle,
                    actionLabel = strings.homeOpenAtlas,
                    onActionClick = onOpenExplore
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .clip(MaterialTheme.shapes.large)
                        .border(0.75.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.65f), MaterialTheme.shapes.large)
                        .clickable { onOpenFeatureDetail(featuredLocation.id) }
                        .testTag("home_featured_location_card")
                ) {
                    AstronomyArtworkImage(
                        imageUrl = featuredLocation.imageUrl,
                        fallbackType = featuredLocation.fallbackVisualType,
                        contentDescription = featuredLocation.name,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(listOf(Color(0x2204060B), Color(0x9904060B), Color(0xF004060B)))
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            LunarPillBadge(
                                text = featuredLocation.type.label.uppercase(),
                                containerColor = Color(0xAA04060B),
                                contentColor = Color(0xFFDCE7FF)
                            )
                            Text(
                                text = featuredLocation.coordinates,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFFC9D4EA)
                            )
                        }
                        Column {
                            Text(
                                text = featuredLocation.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color(0xFFF5F7FC)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = featuredLocation.summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFD3DCF0),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        // 6. DID YOU KNOW? (Minimal typographic editorial block)
        item(key = "did_you_know") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                LunarHairlineDivider()
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.homeDidYouKnow.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 1.8.sp
                    )
                    TextButton(
                        onClick = { factIndex = (factIndex + 1) % LunarContentCatalog.astronomyFacts.size },
                        modifier = Modifier.testTag("next_fact_button")
                    ) {
                        Text(strings.homeAnotherFact, style = MaterialTheme.typography.labelLarge)
                    }
                }
                Text(
                    text = currentFact.first,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentFact.second,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(18.dp))
                LunarHairlineDivider()
            }
        }

        // 7. FEATURED ASTRONOMY DISCOVERY
        item(key = "featured_discovery") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.homeFeaturedDiscovery,
                    title = strings.learnTitle,
                    actionLabel = strings.homeLearnLibrary,
                    onActionClick = onOpenLearn
                )
                Spacer(modifier = Modifier.height(12.dp))
                LunarSurfaceContainer(
                    onClick = { onOpenArticleDetail(featuredDiscovery.articleId) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(155.dp)
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = featuredDiscovery.imageUrl,
                            fallbackType = featuredDiscovery.fallbackVisualType,
                            contentDescription = featuredDiscovery.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(14.dp)
                        ) {
                            LunarPillBadge(
                                text = featuredDiscovery.tag,
                                containerColor = Color(0xBB04060B),
                                contentColor = Color(0xFFE2EAFA)
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = featuredDiscovery.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = featuredDiscovery.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // 8. MOON PHOTOGRAPHY & WALLPAPERS FEATURE
        item(key = "photography_and_wallpapers") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.homePhotography,
                    title = strings.photoGuideTitle,
                    actionLabel = strings.homeWallpapers,
                    onActionClick = onOpenWallpapers
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Photography card
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                            .clip(MaterialTheme.shapes.large)
                            .border(0.75.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), MaterialTheme.shapes.large)
                            .clickable(onClick = onOpenPhotography)
                            .testTag("home_photo_guide_card")
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = featuredPhotoGuide.imageUrl,
                            fallbackType = VisualFallbackType.MOON_OCEAN,
                            contentDescription = featuredPhotoGuide.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xE804060B))))
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(14.dp)
                        ) {
                            Text("SMARTPHONE & DSLR", style = MaterialTheme.typography.labelSmall, color = Color(0xFFAEC6FF))
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "Moon Exposure & Composition",
                                style = MaterialTheme.typography.titleSmall,
                                color = Color(0xFFF5F7FC),
                                maxLines = 2
                            )
                        }
                    }

                    // Wallpapers card
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(180.dp)
                            .clip(MaterialTheme.shapes.large)
                            .border(0.75.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), MaterialTheme.shapes.large)
                            .clickable(onClick = onOpenWallpapers)
                            .testTag("home_wallpapers_card")
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = "",
                            fallbackType = VisualFallbackType.BLOOD_MOON,
                            contentDescription = "Lunar Wallpapers",
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xE804060B))))
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(14.dp)
                        ) {
                            Text("4K COLLECTION", style = MaterialTheme.typography.labelSmall, color = EclipseCopper)
                            Spacer(modifier = Modifier.height(3.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Astronomy Wallpapers",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color(0xFFF5F7FC),
                                    modifier = Modifier.weight(1f),
                                    maxLines = 2
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                                    contentDescription = null,
                                    tint = Color(0xFFF5F7FC),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
