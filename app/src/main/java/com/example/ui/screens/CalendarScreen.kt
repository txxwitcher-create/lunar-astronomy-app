package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.data.LunarContentCatalog
import com.example.localization.LocalLunarStrings
import com.example.ui.components.*
import com.example.ui.theme.EclipseCopper
import com.example.ui.viewmodel.LunarUiState
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun CalendarScreen(
    uiState: LunarUiState,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onJumpToday: () -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    onOpenEvents: () -> Unit
) {
    val strings = LocalLunarStrings.current
    val yearMonth = uiState.selectedYearMonth
    val today = LocalDate.now()
    val settings = uiState.settings
    val locale = Locale.getDefault()
    val monthFormatter = remember(locale) { DateTimeFormatter.ofPattern("MMMM yyyy", locale) }

    val daysOfWeek = remember(settings.startWeekOnMonday, locale) {
        val ordered = if (settings.startWeekOnMonday) {
            listOf(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
            )
        } else {
            listOf(
                DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY
            )
        }
        ordered.map { it.getDisplayName(TextStyle.SHORT_STANDALONE, locale).uppercase(locale) }
    }

    val leadingEmptySlots = remember(yearMonth, settings.startWeekOnMonday) {
        val firstDow = yearMonth.atDay(1).dayOfWeek
        if (settings.startWeekOnMonday) firstDow.value - 1
        else if (firstDow == DayOfWeek.SUNDAY) 0 else firstDow.value
    }

    val majorHighlights = remember(uiState.calendarDays, settings.showCalendarEclipses) {
        uiState.calendarDays.filter { it.isMajorPhaseDay || (settings.showCalendarEclipses && it.hasEclipse) }
    }

    val paddedCells: List<CalendarDayData?> = remember(uiState.calendarDays, leadingEmptySlots) {
        List(leadingEmptySlots) { null } + uiState.calendarDays
    }
    val rows = remember(paddedCells) { paddedCells.chunked(7) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("calendar_screen"),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        item(key = "calendar_header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .displayCutoutPadding()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp)
            ) {
                Text(
                    text = strings.lunarCalendar.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = yearMonth.format(monthFormatter),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.weight(1f)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(
                            onClick = onJumpToday,
                            modifier = Modifier.testTag("calendar_today_button")
                        ) {
                            Text(strings.today, style = MaterialTheme.typography.labelLarge)
                        }
                        IconButton(
                            onClick = onPreviousMonth,
                            modifier = Modifier
                                .size(48.dp)
                                .testTag("calendar_prev_month")
                        ) {
                            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowLeft, contentDescription = "Previous month")
                        }
                        IconButton(
                            onClick = onNextMonth,
                            modifier = Modifier
                                .size(48.dp)
                                .testTag("calendar_next_month")
                        ) {
                            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = "Next month")
                        }
                    }
                }
            }
        }

        // Weekday headers
        item(key = "weekday_labels") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Calendar grid rows (flat items inside LazyColumn for silky smooth scrolling without nested scroll)
        items(rows.indices.toList(), key = { "week_$it" }) { rowIndex ->
            val week = rows[rowIndex]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 3.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (col in 0 until 7) {
                    val cell = week.getOrNull(col)
                    if (cell == null) {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        val isToday = cell.date == today
                        val showEclipseBadge = settings.showCalendarEclipses && cell.hasEclipse
                        val cellBorderColor = when {
                            isToday -> MaterialTheme.colorScheme.primary
                            showEclipseBadge -> EclipseCopper
                            cell.isMajorPhaseDay -> MaterialTheme.colorScheme.outline
                            else -> Color.Transparent
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isToday) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                    else MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)
                                )
                                .border(
                                    width = if (isToday || showEclipseBadge) 1.2.dp else 0.6.dp,
                                    color = cellBorderColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { onSelectDate(cell.date) }
                                .padding(vertical = 7.dp, horizontal = 2.dp)
                                .testTag("calendar_day_${cell.date}"),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = cell.date.dayOfMonth.toString(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isToday || cell.isMajorPhaseDay) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            RealisticMoonDisc(
                                phaseFraction = cell.phaseFraction,
                                sizeDp = 26.dp,
                                isBloodMoon = showEclipseBadge,
                                includeOuterHalo = false,
                                contentDescription = "${cell.phaseType.displayName} on ${cell.date}",
                                modifier = Modifier.size(26.dp)
                            )
                            if (settings.showCalendarIllumination) {
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = "${cell.illuminationPercent.roundToInt()}%",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.5.sp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                            if (showEclipseBadge || cell.isMajorPhaseDay) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .clip(CircleShape)
                                        .background(if (showEclipseBadge) EclipseCopper else MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Key Lunar Dates This Month
        item(key = "month_highlights_header") {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 8.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.monthHighlights,
                    title = strings.eclipses,
                    actionLabel = strings.homeAllEvents,
                    onActionClick = onOpenEvents
                )
            }
        }

        items(majorHighlights, key = { "hl_${it.date}" }) { day ->
            val fmt = remember { DateTimeFormatter.ofPattern("EEE, MMM d", Locale.getDefault()) }
            LunarSurfaceContainer(
                onClick = { onSelectDate(day.date) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RealisticMoonDisc(
                        phaseFraction = day.phaseFraction,
                        sizeDp = 42.dp,
                        isBloodMoon = day.hasEclipse,
                        includeOuterHalo = false,
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = day.eventTitle ?: strings.phaseName(day.phaseType),
                            style = MaterialTheme.typography.titleMedium,
                            color = if (day.hasEclipse) EclipseCopper else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${day.date.format(fmt)} • ${day.illuminationPercent.roundToInt()}% ${strings.illuminated}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (day.hasEclipse) {
                        LunarPillBadge(
                            text = "ECLIPSE",
                            containerColor = EclipseCopper.copy(alpha = 0.2f),
                            contentColor = EclipseCopper
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDayDetailScreen(
    date: LocalDate,
    uiState: LunarUiState,
    snapshot: LunarSnapshot,
    onBack: () -> Unit,
    onOpenPhaseDetail: (LunarPhaseType) -> Unit,
    onOpenEventDetail: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    val settings = uiState.settings
    val dateTitle = remember(date) { date.format(DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy", Locale.getDefault())) }
    val dayMillis = remember(date) { date.atTime(12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() }
    val prevMajor = remember(dayMillis) { AstronomyEngine.findPreviousMajorPhase(dayMillis) }
    val nextMajor = remember(dayMillis) { AstronomyEngine.findNextMajorPhase(dayMillis) }
    val phaseInfo = LunarContentCatalog.phaseDetails[snapshot.phaseType]

    val matchingEvents = remember(date, uiState.upcomingEvents) {
        val zone = ZoneId.systemDefault()
        val eclipses = AstronomyEngine.getKnownLunarEclipses()
        (uiState.upcomingEvents + eclipses).distinctBy { it.id }.filter {
            Instant.ofEpochMilli(it.timestampMillis).atZone(zone).toLocalDate() == date
        }
    }

    val shortFmt = remember { DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault())), onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(dateTitle.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, letterSpacing = 1.2.sp)
                    Spacer(modifier = Modifier.height(14.dp))
                    RealisticMoonDisc(
                        phaseFraction = snapshot.phaseFraction,
                        sizeDp = 210.dp,
                        isBloodMoon = matchingEvents.any { it.eclipseStages != null },
                        includeOuterHalo = true,
                        modifier = Modifier.size(210.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(strings.phaseName(snapshot.phaseType), style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${snapshot.illuminationPercent.roundToInt()}% ${strings.illuminated} • ${if (snapshot.isWaxing) strings.waxing else strings.waning} • ${strings.lunarAge} ${String.format("%.1f", snapshot.lunarAgeDays)}${strings.daysUnit}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            item {
                Column {
                    LunarHairlineDivider()
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TelemetryStatBlock(strings.moonrise, formatInstantTime(snapshot.moonriseMillis, settings.use24HourTime), modifier = Modifier.weight(1f))
                        TelemetryStatBlock(strings.moonset, formatInstantTime(snapshot.moonsetMillis, settings.use24HourTime), modifier = Modifier.weight(1f))
                        TelemetryStatBlock(strings.distance, formatDistance(snapshot.distanceKm, settings.distanceUnit), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TelemetryStatBlock(
                            label = "Previous Major",
                            value = prevMajor.first.shortName,
                            sublabel = Instant.ofEpochMilli(prevMajor.second).atZone(ZoneId.systemDefault()).toLocalDate().format(shortFmt),
                            modifier = Modifier.weight(1f)
                        )
                        TelemetryStatBlock(
                            label = "Next Major",
                            value = nextMajor.first.shortName,
                            sublabel = Instant.ofEpochMilli(nextMajor.second).atZone(ZoneId.systemDefault()).toLocalDate().format(shortFmt),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    LunarHairlineDivider()
                }
            }

            if (matchingEvents.isNotEmpty()) {
                items(matchingEvents, key = { it.id }) { ev ->
                    LunarSurfaceContainer(
                        onClick = { onOpenEventDetail(ev.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            LunarPillBadge(
                                text = ev.type.label.uppercase(),
                                containerColor = EclipseCopper.copy(alpha = 0.2f),
                                contentColor = EclipseCopper
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(ev.title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(ev.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            if (phaseInfo != null) {
                item {
                    Column {
                        Text("PHASE EXPLANATION", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(phaseInfo.explanation, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(
                            onClick = { onOpenPhaseDetail(snapshot.phaseType) },
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Read Full ${snapshot.phaseType.displayName} Guide")
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
