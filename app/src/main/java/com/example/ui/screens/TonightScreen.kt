package com.example.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.localization.LocalLunarStrings
import com.example.ui.components.*
import com.example.ui.theme.ClearSkyEmerald
import com.example.ui.theme.WarningAmber
import com.example.ui.viewmodel.LunarUiState
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TonightScreen(
    uiState: LunarUiState,
    hasLocationPermission: Boolean,
    onRequestGpsLocation: () -> Unit,
    onSelectManualLocation: (UserLocation) -> Unit,
    onSearchCities: (String) -> Unit,
    onRefreshWeather: () -> Unit
) {
    val strings = LocalLunarStrings.current
    val snapshot = uiState.currentSnapshot
    val twilight = uiState.twilightInfo
    val settings = uiState.settings

    var showLocationRationale by remember { mutableStateOf(false) }
    var showManualCityDialog by remember { mutableStateOf(false) }
    var selectedTypeFilter by remember { mutableStateOf<SkyObjectType?>(null) }
    var expandedObjectId by remember { mutableStateOf<String?>(null) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        val granted = grants[Manifest.permission.ACCESS_COARSE_LOCATION] == true ||
            grants[Manifest.permission.ACCESS_FINE_LOCATION] == true
        if (granted) {
            onRequestGpsLocation()
        }
    }

    val filteredObjects = remember(uiState.tonightSkyObjects, selectedTypeFilter) {
        if (selectedTypeFilter == null) uiState.tonightSkyObjects
        else uiState.tonightSkyObjects.filter { it.type == selectedTypeFilter }
    }

    if (showLocationRationale) {
        AlertDialog(
            onDismissRequest = { showLocationRationale = false },
            title = { Text("Why LUNAR Uses Location") },
            text = {
                Text(
                    "Your latitude and longitude let LUNAR calculate exact local moonrise, moonset, Moon altitude, twilight windows, and real-time cloud cover. Location stays on your device—and you can always pick any city manually instead."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLocationRationale = false
                        locationPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        )
                    }
                ) {
                    Text("Allow Location")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showLocationRationale = false
                        showManualCityDialog = true
                    }
                ) {
                    Text("Choose Manually")
                }
            }
        )
    }

    if (showManualCityDialog) {
        ManualCityPickerDialog(
            query = uiState.citySearchQuery,
            results = uiState.citySearchResults,
            isSearching = uiState.isSearchingCities,
            onQueryChange = onSearchCities,
            onSelectCity = { loc ->
                onSelectManualLocation(loc)
                showManualCityDialog = false
            },
            onDismiss = { showManualCityDialog = false }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("tonight_screen"),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        item(key = "tonight_header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .displayCutoutPadding()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp)
            ) {
                Text(
                    text = strings.navTonight.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = strings.tonightQuestion,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))

                // Location strip with GPS + Manual controls
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = settings.location.displayLabel,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = String.format(
                                    "%.2f°, %.2f° • %s",
                                    settings.location.latitude,
                                    settings.location.longitude,
                                    if (settings.location.isAutomaticGps) "GPS Active" else "Manual Location"
                                ),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        TextButton(
                            onClick = { showManualCityDialog = true },
                            modifier = Modifier.testTag("tonight_manual_location_btn")
                        ) {
                            Text("City")
                        }
                        IconButton(
                            onClick = {
                                if (hasLocationPermission) onRequestGpsLocation()
                                else showLocationRationale = true
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("tonight_gps_button")
                        ) {
                            Icon(Icons.Outlined.MyLocation, contentDescription = "Use device GPS location")
                        }
                    }
                }
            }
        }

        // SECTION 1: CALCULATED ASTRONOMICAL DATA (100% deterministic offline)
        item(key = "calculated_moon_visibility") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.calculatedAstronomy,
                    title = strings.altitudeNow
                )
                Spacer(modifier = Modifier.height(12.dp))
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Sky Horizon Arc Visualization
                        MoonSkyArcVisualizer(
                            altitudeDeg = snapshot.altitudeDeg,
                            compassDirection = snapshot.compassDirection,
                            isAboveHorizon = snapshot.isAboveHorizon,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TelemetryStatBlock("Moonrise", formatInstantTime(snapshot.moonriseMillis, settings.use24HourTime), modifier = Modifier.weight(1f))
                            TelemetryStatBlock("Moonset", formatInstantTime(snapshot.moonsetMillis, settings.use24HourTime), modifier = Modifier.weight(1f))
                            TelemetryStatBlock("Illumination", "${snapshot.illuminationPercent.roundToInt()}%", modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TelemetryStatBlock("Altitude", "${snapshot.altitudeDeg.roundToInt()}°", modifier = Modifier.weight(1f))
                            TelemetryStatBlock(
                                label = "Direction",
                                value = if (settings.showHorizonCompass) "${snapshot.compassDirection} (${snapshot.azimuthDeg.roundToInt()}°)" else "${snapshot.azimuthDeg.roundToInt()}°",
                                modifier = Modifier.weight(1f)
                            )
                            TelemetryStatBlock("Visibility", if (snapshot.isAboveHorizon) "Up Now" else "Set", modifier = Modifier.weight(1f))
                        }
                        if (settings.showAtmosphericRefractionNote) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Calculated geometrically for your horizon using Meeus lunar ephemeris (includes standard atmospheric refraction and parallax).",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // SECTION 2: TWILIGHT & DARKNESS SCHEDULE (Calculated Astronomy)
        item(key = "twilight_schedule") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.calculatedAstronomy,
                    title = strings.twilightWindow
                )
                Spacer(modifier = Modifier.height(10.dp))
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("CURRENT SKY STAGE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                Text(twilight.currentSkyStage, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                            }
                            LunarPillBadge(text = "BEST WINDOW")
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Astronomical darkness window: ${twilight.bestObservationWindow}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        LunarHairlineDivider()
                        Spacer(modifier = Modifier.height(12.dp))
                        TwilightRow("Sunset", formatInstantTime(twilight.sunsetMillis, settings.use24HourTime), "Sun crosses horizon")
                        TwilightRow("Civil Twilight Ends", formatInstantTime(twilight.civilDuskMillis, settings.use24HourTime), "Sun -6° • Bright planets appear")
                        TwilightRow("Nautical Twilight Ends", formatInstantTime(twilight.nauticalDuskMillis, settings.use24HourTime), "Sun -12° • Constellations clear")
                        TwilightRow("Astronomical Darkness", formatInstantTime(twilight.astronomicalDuskMillis, settings.use24HourTime), "Sun -18° • Deep-sky peak")
                        TwilightRow("Astronomical Dawn", formatInstantTime(twilight.astronomicalDawnMillis, settings.use24HourTime), "Sky begins brightening")
                    }
                }
            }
        }

        // SECTION 3: WEATHER-DEPENDENT CONDITIONS (Live Open-Meteo, never fabricated!)
        item(key = "weather_conditions") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.weatherConditions,
                    title = strings.weatherConditions,
                    actionLabel = strings.refresh,
                    onActionClick = onRefreshWeather
                )
                Spacer(modifier = Modifier.height(10.dp))
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        when (val wState = uiState.weatherState) {
                            is WeatherUiState.Loading -> {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                    Text("Fetching live cloud cover for ${settings.location.name}...", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                            is WeatherUiState.Available -> {
                                val w = wState.weather
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(w.weatherDescription, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                                        Text(
                                            text = if (w.isFromCache) "Cached observation • Tap Refresh when online" else "Live atmospheric reading",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (w.isFromCache) WarningAmber else ClearSkyEmerald
                                        )
                                    }
                                    LunarPillBadge(
                                        text = "${w.cloudCoverPercent}% CLOUDS",
                                        containerColor = (if (w.cloudCoverPercent <= 35) ClearSkyEmerald else WarningAmber).copy(alpha = 0.16f),
                                        contentColor = if (w.cloudCoverPercent <= 35) ClearSkyEmerald else WarningAmber
                                    )
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    TelemetryStatBlock("Cloud Cover", "${w.cloudCoverPercent}%", modifier = Modifier.weight(1f))
                                    TelemetryStatBlock("Temperature", formatTemperature(w.temperatureCelsius, settings.distanceUnit), modifier = Modifier.weight(1f))
                                    TelemetryStatBlock("Wind", "${w.windSpeedKmh.roundToInt()} km/h", modifier = Modifier.weight(1f))
                                }
                            }
                            is WeatherUiState.Unavailable -> {
                                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Icon(Icons.Outlined.CloudOff, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Column {
                                        Text("Live Weather Offline", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(wState.reason, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // SECTION 4: CELESTIAL OBJECTS OBSERVABLE TONIGHT
        item(key = "sky_objects_header") {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 8.dp)) {
                LunarSectionHeader(
                    eyebrow = strings.calculatedAstronomy,
                    title = strings.celestialObjects
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = selectedTypeFilter == null,
                            onClick = { selectedTypeFilter = null },
                            label = { Text("${strings.all} (${uiState.tonightSkyObjects.size})") }
                        )
                    }
                    items(SkyObjectType.entries) { type ->
                        val count = uiState.tonightSkyObjects.count { it.type == type }
                        if (count > 0) {
                            FilterChip(
                                selected = selectedTypeFilter == type,
                                onClick = { selectedTypeFilter = if (selectedTypeFilter == type) null else type },
                                label = { Text("${type.label} ($count)") }
                            )
                        }
                    }
                }
            }
        }

        items(filteredObjects, key = { it.id }) { obj ->
            val expanded = expandedObjectId == obj.id
            LunarSurfaceContainer(
                onClick = { expandedObjectId = if (expanded) null else obj.id },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .testTag("sky_object_${obj.id}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(obj.type.label.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                Text("•  ${obj.magnitudeLabel}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(obj.name, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                            Text(obj.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        LunarPillBadge(
                            text = if (obj.isCurrentlyVisible) "VISIBLE" else "BELOW",
                            containerColor = (if (obj.isCurrentlyVisible) ClearSkyEmerald else MaterialTheme.colorScheme.onSurfaceVariant).copy(alpha = 0.16f),
                            contentColor = if (obj.isCurrentlyVisible) ClearSkyEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${obj.visibilityLabel}  •  Direction: ${obj.compassDirection}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                    )

                    AnimatedVisibility(visible = expanded) {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LunarHairlineDivider()
                            SkyGuideRow("What It Is", obj.whatItIs)
                            SkyGuideRow("When To Observe", obj.whenToObserve)
                            SkyGuideRow("Where To Look", obj.whereToLook)
                            SkyGuideRow("How To Find It", obj.howToFind)
                            SkyGuideRow("Beginner Tip", obj.beginnerTips)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MoonSkyArcVisualizer(
    altitudeDeg: Double,
    compassDirection: String,
    isAboveHorizon: Boolean,
    modifier: Modifier = Modifier
) {
    val accent = MaterialTheme.colorScheme.primary
    val outline = MaterialTheme.colorScheme.outline
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val horizonY = h * 0.78f
            val arcRadius = (w * 0.40f).coerceAtMost(h * 0.68f)
            val cx = w / 2f

            // Horizon line
            drawLine(
                color = outline,
                start = Offset(12f, horizonY),
                end = Offset(w - 12f, horizonY),
                strokeWidth = 1.5f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
            )

            // Sky semicircle arc
            drawArc(
                color = accent.copy(alpha = 0.35f),
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(cx - arcRadius, horizonY - arcRadius),
                size = androidx.compose.ui.geometry.Size(arcRadius * 2, arcRadius * 2),
                style = Stroke(width = 2.5f)
            )

            // Plot Moon position along arc based on altitude (-90..+90)
            val clampedAlt = altitudeDeg.coerceIn(-35.0, 90.0)
            val norm = ((clampedAlt + 15.0) / 105.0).coerceIn(0.0, 1.0)
            val angleRad = PI * (1.0 - norm * 0.85 - 0.075)
            val mx = cx + (cos(angleRad) * arcRadius).toFloat()
            val my = if (isAboveHorizon) horizonY - (sin(angleRad) * arcRadius).toFloat() else horizonY + 14f

            drawCircle(
                color = if (isAboveHorizon) Color(0xFFF3F6FF) else textColor,
                radius = 9f,
                center = Offset(mx, my)
            )
            if (isAboveHorizon) {
                drawCircle(
                    color = accent.copy(alpha = 0.3f),
                    radius = 16f,
                    center = Offset(mx, my)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("EAST", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = if (isAboveHorizon) "MOON AT ${altitudeDeg.roundToInt()}° $compassDirection" else "BELOW HORIZON (${altitudeDeg.roundToInt()}°)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text("WEST", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun TwilightRow(label: String, time: String, note: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
            Text(note, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(time, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun SkyGuideRow(label: String, value: String) {
    Column {
        Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(2.dp))
        Text(value, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun ManualCityPickerDialog(
    query: String,
    results: List<UserLocation>,
    isSearching: Boolean,
    onQueryChange: (String) -> Unit,
    onSelectCity: (UserLocation) -> Unit,
    onDismiss: () -> Unit
) {
    val strings = LocalLunarStrings.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(strings.selectCity) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    placeholder = { Text(strings.searchCityHint) },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("city_search_input")
                )
                Spacer(modifier = Modifier.height(10.dp))
                if (isSearching) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                }
                LazyColumn(modifier = Modifier.heightIn(max = 260.dp)) {
                    items(results, key = { "${it.name}_${it.latitude}_${it.longitude}" }) { city ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectCity(city) }
                                .padding(vertical = 10.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(city.name, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                                Text(
                                    text = "${city.region} (${String.format("%.1f°, %.1f°", city.latitude, city.longitude)})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        LunarHairlineDivider()
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(strings.close) }
        }
    )
}
