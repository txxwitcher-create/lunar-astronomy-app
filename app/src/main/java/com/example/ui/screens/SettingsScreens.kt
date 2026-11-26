package com.example.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.localization.LocalLunarStrings
import com.example.localization.LunarLocaleManager
import com.example.notifications.LunarNotificationManager
import com.example.ui.components.*
import com.example.ui.theme.EditorialSerif
import com.example.ui.viewmodel.LunarUiState
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    uiState: LunarUiState,
    activeLanguageCode: String,
    hasLocationPermission: Boolean,
    onBack: () -> Unit,
    onOpenLanguageSelector: () -> Unit,
    onSelectFontStyle: (LunarFontStyle) -> Unit,
    onToggleIntroEnabled: (Boolean) -> Unit,
    onReplayIntro: () -> Unit,
    onSelectIntroStyle: (IntroMoonStyle) -> Unit,
    onToggleReducedMotion: (Boolean) -> Unit,
    onRequestGpsLocation: () -> Unit,
    onSelectManualLocation: (UserLocation) -> Unit,
    onSearchCities: (String) -> Unit,
    onSelectTheme: (LunarThemeMode) -> Unit,
    onSelectUnit: (DistanceUnit) -> Unit,
    onToggle24Hour: (Boolean) -> Unit,
    onUpdateCalendarPrefs: (Boolean, Boolean, Boolean) -> Unit,
    onUpdateObservationPrefs: (Boolean, Boolean) -> Unit,
    onUpdateWallpaperPrefs: (WallpaperQuality, Boolean) -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenWidgets: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenWallpapers: () -> Unit,
    onClearCache: () -> Unit,
    onOpenAbout: () -> Unit,
    onOpenInstagram: () -> Unit,
    onSendFeedback: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    val currentLanguage = remember(activeLanguageCode) {
        LunarLocaleManager.findLanguage(activeLanguageCode)
    }
    val settings = uiState.settings
    val totalFavorites = settings.favoriteWallpaperIds.size +
        settings.favoriteArticleIds.size +
        settings.favoriteFeatureIds.size +
        settings.favoriteEventIds.size

    var showCityPicker by remember { mutableStateOf(false) }
    var showLocationRationale by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showFontDialog by remember { mutableStateOf(false) }
    var showIntroStyleDialog by remember { mutableStateOf(false) }
    var showUnitsDialog by remember { mutableStateOf(false) }
    var showCalendarPrefsDialog by remember { mutableStateOf(false) }
    var showObservationPrefsDialog by remember { mutableStateOf(false) }
    var showWallpaperPrefsDialog by remember { mutableStateOf(false) }
    var showClearCacheDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showFeedbackDialog by remember { mutableStateOf(false) }

    val locPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        val ok = grants[Manifest.permission.ACCESS_COARSE_LOCATION] == true ||
            grants[Manifest.permission.ACCESS_FINE_LOCATION] == true
        if (ok) onRequestGpsLocation()
    }

    // DIALOGS
    if (showLocationRationale) {
        AlertDialog(
            onDismissRequest = { showLocationRationale = false },
            title = { Text("Location Permission") },
            text = { Text("LUNAR uses your approximate coordinates strictly to calculate accurate local moonrise, moonset, twilight windows, and sky cloud cover. Coordinates are never uploaded to user accounts or tracking servers.") },
            confirmButton = {
                Button(onClick = {
                    showLocationRationale = false
                    locPermissionLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                }) { Text("Allow GPS") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showLocationRationale = false
                    showCityPicker = true
                }) { Text("Select Manually") }
            }
        )
    }

    if (showCityPicker) {
        ManualCityPickerDialog(
            query = uiState.citySearchQuery,
            results = uiState.citySearchResults,
            isSearching = uiState.isSearchingCities,
            onQueryChange = onSearchCities,
            onSelectCity = {
                onSelectManualLocation(it)
                showCityPicker = false
            },
            onDismiss = { showCityPicker = false }
        )
    }

    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("Appearance & Theme") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    LunarThemeMode.entries.forEach { mode ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onSelectTheme(mode)
                                    showThemeDialog = false
                                }
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = settings.themeMode == mode, onClick = {
                                onSelectTheme(mode)
                                showThemeDialog = false
                            })
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(mode.label, style = MaterialTheme.typography.titleSmall)
                                Text(mode.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showThemeDialog = false }) { Text(strings.done) } }
        )
    }

    if (showFontDialog) {
        AlertDialog(
            onDismissRequest = { showFontDialog = false },
            title = { Text(strings.fontLabel) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    LunarFontStyle.entries.forEach { font ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onSelectFontStyle(font)
                                    showFontDialog = false
                                }
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                                .testTag("font_option_${font.name}"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settings.fontStyle == font,
                                onClick = {
                                    onSelectFontStyle(font)
                                    showFontDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(font.label, style = MaterialTheme.typography.titleSmall)
                                Text(font.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showFontDialog = false }) { Text(strings.done) } }
        )
    }

    if (showIntroStyleDialog) {
        AlertDialog(
            onDismissRequest = { showIntroStyleDialog = false },
            title = { Text(strings.introStyleLabel) },
            text = {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 360.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(IntroMoonStyle.entries.size) { idx ->
                        val style = IntroMoonStyle.entries[idx]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    onSelectIntroStyle(style)
                                    showIntroStyleDialog = false
                                }
                                .padding(vertical = 7.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settings.introStyle == style,
                                onClick = {
                                    onSelectIntroStyle(style)
                                    showIntroStyleDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(style.label, style = MaterialTheme.typography.titleSmall)
                                Text(style.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showIntroStyleDialog = false }) { Text(strings.done) } }
        )
    }

    if (showUnitsDialog) {
        AlertDialog(
            onDismissRequest = { showUnitsDialog = false },
            title = { Text("Units & Time Format") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DistanceUnit.entries.forEach { u ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectUnit(u) }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = settings.distanceUnit == u, onClick = { onSelectUnit(u) })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(u.label, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                    LunarHairlineDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("24-Hour Clock", style = MaterialTheme.typography.titleSmall)
                            Text("Use 24h format for moonrise & twilight", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = settings.use24HourTime, onCheckedChange = onToggle24Hour)
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showUnitsDialog = false }) { Text("Done") } }
        )
    }

    if (showCalendarPrefsDialog) {
        AlertDialog(
            onDismissRequest = { showCalendarPrefsDialog = false },
            title = { Text("Calendar Preferences") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SettingsSwitchRow("Show Illumination %", "Display daily lit percentage in grid", settings.showCalendarIllumination) {
                        onUpdateCalendarPrefs(it, settings.showCalendarEclipses, settings.startWeekOnMonday)
                    }
                    SettingsSwitchRow("Highlight Lunar Eclipses", "Show copper badge on eclipse dates", settings.showCalendarEclipses) {
                        onUpdateCalendarPrefs(settings.showCalendarIllumination, it, settings.startWeekOnMonday)
                    }
                    SettingsSwitchRow("Start Week on Monday", "ISO Monday-first calendar grid", settings.startWeekOnMonday) {
                        onUpdateCalendarPrefs(settings.showCalendarIllumination, settings.showCalendarEclipses, it)
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showCalendarPrefsDialog = false }) { Text("Done") } }
        )
    }

    if (showObservationPrefsDialog) {
        AlertDialog(
            onDismissRequest = { showObservationPrefsDialog = false },
            title = { Text("Observation Preferences") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SettingsSwitchRow("Compass Cardinal Bearings", "Show direction letters alongside azimuth degrees", settings.showHorizonCompass) {
                        onUpdateObservationPrefs(it, settings.showAtmosphericRefractionNote)
                    }
                    SettingsSwitchRow("Refraction & Ephemeris Notes", "Display calculation notes in Tonight's Sky", settings.showAtmosphericRefractionNote) {
                        onUpdateObservationPrefs(settings.showHorizonCompass, it)
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showObservationPrefsDialog = false }) { Text("Done") } }
        )
    }

    if (showWallpaperPrefsDialog) {
        AlertDialog(
            onDismissRequest = { showWallpaperPrefsDialog = false },
            title = { Text("Wallpaper Download Preferences") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    WallpaperQuality.entries.forEach { q ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onUpdateWallpaperPrefs(q, settings.wifiOnlyDownloads) }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = settings.wallpaperQuality == q, onClick = { onUpdateWallpaperPrefs(q, settings.wifiOnlyDownloads) })
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(q.label, style = MaterialTheme.typography.titleSmall)
                                Text(q.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                    LunarHairlineDivider()
                    SettingsSwitchRow("Prefer Wi-Fi Downloads", "Use offline artwork when on cellular data", settings.wifiOnlyDownloads) {
                        onUpdateWallpaperPrefs(settings.wallpaperQuality, it)
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showWallpaperPrefsDialog = false }) { Text("Done") } }
        )
    }

    if (showClearCacheDialog) {
        AlertDialog(
            onDismissRequest = { showClearCacheDialog = false },
            title = { Text("Clear Cached Data?") },
            text = { Text("This removes cached astronomy photos and temporary weather data (${formatBytes(uiState.cacheSizeBytes)}). Your saved Favorites, location, and preferences will NOT be deleted.") },
            confirmButton = {
                Button(onClick = {
                    showClearCacheDialog = false
                    onClearCache()
                }) { Text("Clear Cache") }
            },
            dismissButton = { TextButton(onClick = { showClearCacheDialog = false }) { Text("Cancel") } }
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy & Permissions") },
            text = {
                Text(
                    "LUNAR is designed to respect your privacy:\n\n" +
                        "• No Account Required: All settings and favorites are stored locally on your device.\n" +
                        "• Optional Location: Only requested if you tap GPS; manual city selection works identically.\n" +
                        "• Weather & Geocoding: Coordinates are sent only to Open-Meteo to fetch public sky cloud cover.\n" +
                        "• Zero Tracking: No advertising SDKs, behavioral analytics, or data brokers."
                )
            },
            confirmButton = { TextButton(onClick = { showPrivacyDialog = false }) { Text("Understood") } }
        )
    }

    if (showFeedbackDialog) {
        AlertDialog(
            onDismissRequest = { showFeedbackDialog = false },
            title = { Text("Feedback & Support") },
            text = { Text("Share feature ideas or report an astronomical/UI bug. Your email client will open with basic device version metadata pre-filled.") },
            confirmButton = {
                Button(onClick = {
                    showFeedbackDialog = false
                    onSendFeedback("Feature Suggestion / Feedback")
                }) { Text("Send Feedback") }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showFeedbackDialog = false
                    onSendFeedback("Bug Report")
                }) { Text("Report a Problem") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = strings.settingsTitle, onBack = onBack)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("settings_screen_list"),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // PERSONALIZATION
            item {
                SettingsGroupCard(title = strings.catPersonalization.uppercase()) {
                    SettingsActionRow(
                        icon = Icons.Outlined.Language,
                        title = strings.languageLabel,
                        subtitle = "${currentLanguage.nativeName} (${currentLanguage.englishName})",
                        onClick = onOpenLanguageSelector,
                        testTagName = "settings_language_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.DarkMode,
                        title = strings.appearanceLabel,
                        subtitle = settings.themeMode.label,
                        onClick = { showThemeDialog = true },
                        testTagName = "settings_theme_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.TextFields,
                        title = strings.fontLabel,
                        subtitle = settings.fontStyle.label,
                        onClick = { showFontDialog = true },
                        testTagName = "settings_font_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.LocationOn,
                        title = strings.locationLabel,
                        subtitle = "${settings.location.displayLabel} (${if (settings.location.isAutomaticGps) "GPS" else "Manual"})",
                        onClick = { showCityPicker = true },
                        testTagName = "settings_location_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.MyLocation,
                        title = strings.automaticGps,
                        subtitle = if (hasLocationPermission) "GPS enabled • Tap to refresh" else "Tap to enable GPS",
                        onClick = {
                            if (hasLocationPermission) onRequestGpsLocation()
                            else showLocationRationale = true
                        },
                        testTagName = "settings_gps_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.Straighten,
                        title = strings.unitsTimeLabel,
                        subtitle = "${settings.distanceUnit.label} • ${if (settings.use24HourTime) "24h" else "12h"}",
                        onClick = { showUnitsDialog = true },
                        testTagName = "settings_units_row"
                    )
                }
            }

            // ASTRONOMY
            item {
                val activeNotifs = listOf(
                    settings.notifications.moonriseAlerts,
                    settings.notifications.fullMoonAlerts,
                    settings.notifications.newMoonAlerts,
                    settings.notifications.lunarEclipseAlerts,
                    settings.notifications.majorEventsAlerts,
                    settings.notifications.observationOpportunities,
                    settings.notifications.dailyMoonUpdate
                ).count { it }

                SettingsGroupCard(title = strings.catAstronomy.uppercase()) {
                    SettingsActionRow(
                        icon = Icons.Outlined.Notifications,
                        title = strings.notificationsLabel,
                        subtitle = "$activeNotifs / 7",
                        onClick = onOpenNotifications,
                        testTagName = "settings_notifications_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.CalendarMonth,
                        title = strings.calendarPrefsLabel,
                        subtitle = "Illumination, eclipses & week start",
                        onClick = { showCalendarPrefsDialog = true },
                        testTagName = "settings_calendar_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.Explore,
                        title = strings.observationPrefsLabel,
                        subtitle = "Horizon compass & ephemeris display",
                        onClick = { showObservationPrefsDialog = true },
                        testTagName = "settings_observation_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.Widgets,
                        title = strings.widgetsLabel,
                        subtitle = "${settings.widgetTheme.label} • 2×2, 4×2 & 4×3",
                        onClick = onOpenWidgets,
                        testTagName = "settings_widgets_row"
                    )
                }
            }

            // VISUAL EXPERIENCE
            item {
                SettingsGroupCard(title = strings.catVisualExperience.uppercase()) {
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        SettingsSwitchRow(
                            title = strings.introAnimationLabel,
                            subtitle = "Cinematic 10-second Moon launch sequence",
                            checked = settings.introEnabled,
                            onCheckedChange = onToggleIntroEnabled
                        )
                    }
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.PlayCircle,
                        title = strings.replayIntroLabel,
                        subtitle = settings.introStyle.label,
                        onClick = onReplayIntro,
                        testTagName = "settings_replay_intro_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.AutoAwesome,
                        title = strings.introStyleLabel,
                        subtitle = "${settings.introStyle.label} — ${settings.introStyle.subtitle}",
                        onClick = { showIntroStyleDialog = true },
                        testTagName = "settings_intro_style_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.Wallpaper,
                        title = strings.wallpaperQualityLabel,
                        subtitle = "${settings.wallpaperQuality.label}${if (settings.wifiOnlyDownloads) " • Wi-Fi" else ""}",
                        onClick = { showWallpaperPrefsDialog = true },
                        testTagName = "settings_wallpapers_row"
                    )
                    LunarHairlineDivider()
                    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        SettingsSwitchRow(
                            title = strings.reducedMotionLabel,
                            subtitle = "Shorter intro & minimal transitions",
                            checked = settings.reducedMotion,
                            onCheckedChange = onToggleReducedMotion
                        )
                    }
                }
            }

            // APP
            item {
                SettingsGroupCard(title = strings.catApp.uppercase()) {
                    SettingsActionRow(
                        icon = Icons.Outlined.BookmarkBorder,
                        title = strings.favorites,
                        subtitle = "$totalFavorites saved",
                        onClick = onOpenFavorites,
                        testTagName = "settings_favorites_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.DownloadForOffline,
                        title = strings.wallpapersTitle,
                        subtitle = "${settings.downloadedWallpaperIds.size} saved to gallery • Browse all",
                        onClick = onOpenWallpapers,
                        testTagName = "settings_downloaded_wallpapers_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.Storage,
                        title = strings.dataStorageLabel,
                        subtitle = "${formatBytes(uiState.cacheSizeBytes)} • ${strings.clearCacheTitle}",
                        onClick = { showClearCacheDialog = true },
                        testTagName = "settings_storage_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.PrivacyTip,
                        title = strings.privacyLabel,
                        subtitle = "On-device astronomy & zero tracking",
                        onClick = { showPrivacyDialog = true },
                        testTagName = "settings_privacy_row"
                    )
                    LunarHairlineDivider()
                    SettingsActionRow(
                        icon = Icons.Outlined.Feedback,
                        title = strings.feedbackLabel,
                        subtitle = "Share suggestions or report an issue",
                        onClick = { showFeedbackDialog = true },
                        testTagName = "settings_feedback_row"
                    )
                }
            }

            // ABOUT
            item {
                SettingsGroupCard(title = strings.catAbout.uppercase()) {
                    SettingsActionRow(
                        icon = Icons.Outlined.Info,
                        title = strings.aboutLunarLabel,
                        subtitle = "Version 2.0 • NASA, LRO & Open-Meteo",
                        onClick = onOpenAbout,
                        testTagName = "settings_about_row"
                    )
                }
            }

            // CREATOR INSTAGRAM PROFILE — TASTEFUL, INTEGRATED AT BOTTOM OF SETTINGS
            item {
                Column(modifier = Modifier.padding(top = 2.dp, bottom = 36.dp)) {
                    Text(
                        text = strings.catCreator.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.6.sp,
                        modifier = Modifier.padding(start = 6.dp, bottom = 8.dp)
                    )
                    LunarSurfaceContainer(
                        onClick = onOpenInstagram,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("settings_creator_instagram")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InstagramGlyphAvatar()
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = strings.createdBy,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "@unfiltered.java",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "instagram.com/unfiltered.java",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.OpenInNew,
                                contentDescription = "Open @unfiltered.java on Instagram",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InstagramGlyphAvatar() {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF1E2942), Color(0xFF282042))
                )
            )
            .border(0.8.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.45f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(20.dp)) {
            val stroke = Stroke(width = 1.9.dp.toPx())
            drawRoundRect(
                color = Color(0xFFE6ECF9),
                topLeft = Offset(1.dp.toPx(), 1.dp.toPx()),
                size = Size(size.width - 2.dp.toPx(), size.height - 2.dp.toPx()),
                cornerRadius = CornerRadius(5.5.dp.toPx(), 5.5.dp.toPx()),
                style = stroke
            )
            drawCircle(
                color = Color(0xFFE6ECF9),
                radius = size.minDimension * 0.23f,
                center = center,
                style = stroke
            )
            drawCircle(
                color = Color(0xFF8AAEFF),
                radius = 1.4.dp.toPx(),
                center = Offset(size.width * 0.75f, size.height * 0.25f)
            )
        }
    }
}

@Composable
private fun SettingsGroupCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.6.sp,
            modifier = Modifier.padding(start = 6.dp, bottom = 8.dp)
        )
        LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}

@Composable
private fun SettingsActionRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    testTagName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .minimumInteractiveComponentSize()
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag(testTagName),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(21.dp))
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SettingsSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun NotificationsSettingsScreen(
    uiState: LunarUiState,
    onBack: () -> Unit,
    onUpdatePrefs: ((NotificationPreferences) -> NotificationPreferences) -> Unit,
    onSendPreview: () -> Unit
) {
    val context = LocalContext.current
    val prefs = uiState.settings.notifications
    var hasPermission by remember { mutableStateOf(LunarNotificationManager.hasNotificationPermission(context)) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
    }

    fun toggleWithPermission(update: (NotificationPreferences) -> NotificationPreferences) {
        if (!hasPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        onUpdatePrefs(update)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = "Notifications", onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "LUNAR alerts are completely optional, quiet, and designed strictly around real astronomical events—never streaks or spam.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (!hasPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                item {
                    LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Notification Permission Needed", style = MaterialTheme.typography.titleSmall)
                                Text("Allow Android notifications so scheduled alerts can reach you.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }) {
                                Text("Allow")
                            }
                        }
                    }
                }
            }

            item {
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                        SettingsSwitchRow("Moonrise Alerts", "15 minutes before local moonrise", prefs.moonriseAlerts) { v ->
                            toggleWithPermission { it.copy(moonriseAlerts = v) }
                        }
                        LunarHairlineDivider()
                        SettingsSwitchRow("Full Moon Alerts", "Reminder ahead of Full Moon opposition", prefs.fullMoonAlerts) { v ->
                            toggleWithPermission { it.copy(fullMoonAlerts = v) }
                        }
                        LunarHairlineDivider()
                        SettingsSwitchRow("New Moon Alerts", "Dark-sky stargazing peak reminder", prefs.newMoonAlerts) { v ->
                            toggleWithPermission { it.copy(newMoonAlerts = v) }
                        }
                        LunarHairlineDivider()
                        SettingsSwitchRow("Lunar Eclipse Alerts", "3 hours before penumbral eclipse start", prefs.lunarEclipseAlerts) { v ->
                            toggleWithPermission { it.copy(lunarEclipseAlerts = v) }
                        }
                        LunarHairlineDivider()
                        SettingsSwitchRow("Major Astronomy Events", "Supermoons & major meteor peaks", prefs.majorEventsAlerts) { v ->
                            toggleWithPermission { it.copy(majorEventsAlerts = v) }
                        }
                        LunarHairlineDivider()
                        SettingsSwitchRow("Observation Opportunities", "Optimal twilight & lunar terminator nights", prefs.observationOpportunities) { v ->
                            toggleWithPermission { it.copy(observationOpportunities = v) }
                        }
                        LunarHairlineDivider()
                        SettingsSwitchRow("Daily Moon Update", "One quiet daily phase summary", prefs.dailyMoonUpdate) { v ->
                            toggleWithPermission { it.copy(dailyMoonUpdate = v) }
                        }
                    }
                }
            }

            item {
                OutlinedButton(
                    onClick = {
                        if (!hasPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            onSendPreview()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("send_preview_notification_btn")
                ) {
                    Text("Send Quiet Preview Notification")
                }
            }
        }
    }
}

@Composable
fun WidgetSettingsScreen(
    uiState: LunarUiState,
    onBack: () -> Unit,
    onSelectWidgetTheme: (WidgetTheme) -> Unit
) {
    val snapshot = uiState.currentSnapshot
    val selectedTheme = uiState.settings.widgetTheme
    val widgetBg = when (selectedTheme) {
        WidgetTheme.BLACK -> Color(0xFF04060B)
        WidgetTheme.MIDNIGHT_BLUE -> Color(0xFF0C1630)
        WidgetTheme.TRANSPARENT -> Color(0x660B101C)
        WidgetTheme.MINIMAL -> Color(0xFF0F131C)
        WidgetTheme.IMAGE_FOCUSED -> Color(0xFF020408)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = "Home Screen Widgets", onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "LUNAR provides Small (2×2), Medium (4×2), and Large (4×3) native Android widgets. Long-press your phone's home screen → Widgets → Lunar to add them.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Live interactive preview reflecting actual current widget theme
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .background(widgetBg)
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.7f), RoundedCornerShape(22.dp))
                        .padding(18.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RealisticMoonDisc(
                            phaseFraction = snapshot.phaseFraction,
                            sizeDp = 76.dp,
                            includeOuterHalo = selectedTheme != WidgetTheme.MINIMAL,
                            modifier = Modifier.size(76.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("LUNAR", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 2.sp)
                            Text(snapshot.phaseType.displayName, style = MaterialTheme.typography.titleLarge, color = Color(0xFFF5F7FC))
                            Text(
                                text = "${snapshot.illuminationPercent.roundToInt()}% Illuminated • Age ${String.format("%.1f", snapshot.lunarAgeDays)}d",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFB5C2DA)
                            )
                            Text(
                                text = "Rise ${formatInstantTime(snapshot.moonriseMillis, uiState.settings.use24HourTime)}  •  Set ${formatInstantTime(snapshot.moonsetMillis, uiState.settings.use24HourTime)}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF93A3C2)
                            )
                        }
                    }
                }
            }

            item {
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("WIDGET THEME", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        WidgetTheme.entries.forEachIndexed { idx, theme ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSelectWidgetTheme(theme) }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(selected = selectedTheme == theme, onClick = { onSelectWidgetTheme(theme) })
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(theme.label, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                                    Text(theme.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            if (idx < WidgetTheme.entries.lastIndex) LunarHairlineDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AboutLunarScreen(
    onBack: () -> Unit,
    onOpenInstagram: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = "About LUNAR", onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RealisticMoonDisc(phaseFraction = 0.28, sizeDp = 110.dp, modifier = Modifier.size(110.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "LUNAR",
                        style = MaterialTheme.typography.displaySmall.copy(fontFamily = EditorialSerif, letterSpacing = 6.sp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text("THE MOON, CLOSER THAN EVER.", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Version 1.0.0", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            item {
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("ASTRONOMY DATA SOURCES", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "• On-device lunar & solar ephemeris algorithms based on Jean Meeus (Astronomical Algorithms) & USNO celestial mechanics.\n" +
                                "• Lunar Eclipse timestamps sourced from NASA Goddard Five Millennium Canon of Lunar Eclipses.\n" +
                                "• Real-time sky cloud cover & geocoding powered by Open-Meteo.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("PHOTOGRAPHY & IMAGE CREDITS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "Public-domain scientific photography courtesy of NASA, Lunar Reconnaissance Orbiter (LRO), Apollo Program Archives, NASA Goddard Space Flight Center, JPL-Caltech, and ESA/CSA/JWST, paired with LUNAR's high-precision procedural selenography renderer.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                LunarSurfaceContainer(
                    onClick = onOpenInstagram,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        InstagramGlyphAvatar()
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("DEVELOPER", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            Text("@unfiltered.java", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                            Text("Follow on Instagram", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Icon(Icons.AutoMirrored.Rounded.OpenInNew, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

private fun formatBytes(bytes: Long): String {
    return when {
        bytes < 1024L -> "$bytes B"
        bytes < 1024L * 1024L -> "${bytes / 1024L} KB"
        else -> String.format("%.1f MB", bytes / (1024.0 * 1024.0))
    }
}
