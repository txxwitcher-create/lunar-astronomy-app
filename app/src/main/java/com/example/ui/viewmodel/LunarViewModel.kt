package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.astronomy.*
import com.example.data.*
import com.example.notifications.LunarNotificationManager
import com.example.widget.refreshAllLunarWidgets
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId

data class LunarUiState(
    val isSettingsLoaded: Boolean = false,
    val nowMillis: Long = System.currentTimeMillis(),
    val settings: AppSettings = AppSettings(),
    val currentSnapshot: LunarSnapshot = AstronomyEngine.getLunarSnapshot(System.currentTimeMillis(), UserLocation.DEFAULT),
    val twilightInfo: TwilightInfo = AstronomyEngine.calculateTwilight(LocalDate.now(), UserLocation.DEFAULT, System.currentTimeMillis(), false),
    val upcomingEvents: List<LunarEvent> = AstronomyEngine.getUpcomingEvents(System.currentTimeMillis()),
    val tonightSkyObjects: List<SkyObject> = AstronomyEngine.getTonightSkyObjects(System.currentTimeMillis(), UserLocation.DEFAULT),
    val weatherState: WeatherUiState = WeatherUiState.Loading,
    val selectedYearMonth: YearMonth = YearMonth.now(),
    val calendarDays: List<CalendarDayData> = AstronomyEngine.buildCalendarMonth(YearMonth.now()),
    val citySearchQuery: String = "",
    val citySearchResults: List<UserLocation> = LunarContentCatalog.presetCities,
    val isSearchingCities: Boolean = false,
    val cacheSizeBytes: Long = 0L,
    val statusMessage: String? = null
)

class LunarViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext: Context = application.applicationContext
    private val settingsRepository = SettingsRepository(appContext)
    private val locationProvider = LocationProvider(appContext)
    private val repository = LunarRepository(appContext, settingsRepository, locationProvider)

    private val _uiState = MutableStateFlow(LunarUiState())
    val uiState: StateFlow<LunarUiState> = _uiState.asStateFlow()

    private var lastWeatherLocationKey: String = ""

    init {
        observeSettings()
        startMinuteTicker()
        refreshCacheSize()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsRepository.settingsFlow.collect { settings ->
                val now = System.currentTimeMillis()
                recomputeAstronomy(now, settings, settingsLoaded = true)

                val locKey = "${settings.location.latitude}_${settings.location.longitude}"
                if (locKey != lastWeatherLocationKey) {
                    lastWeatherLocationKey = locKey
                    refreshWeather(settings.location)
                }

                // If automatic GPS is enabled and permission granted, refresh GPS once
                if (settings.useAutomaticLocation && locationProvider.hasLocationPermission() && !settings.location.isAutomaticGps) {
                    detectGpsLocation()
                }
            }
        }
    }

    private fun startMinuteTicker() {
        viewModelScope.launch {
            while (true) {
                delay(30_000L) // Battery-conscious 30s tick for countdown & live altitude
                val now = System.currentTimeMillis()
                recomputeAstronomy(now, _uiState.value.settings, settingsLoaded = _uiState.value.isSettingsLoaded)
            }
        }
    }

    private fun recomputeAstronomy(nowMillis: Long, settings: AppSettings, settingsLoaded: Boolean) {
        val loc = settings.location
        val today = LocalDate.now()
        val snapshot = AstronomyEngine.getLunarSnapshot(nowMillis, loc)
        val twilight = AstronomyEngine.calculateTwilight(today, loc, nowMillis, settings.use24HourTime)
        val sky = AstronomyEngine.getTonightSkyObjects(nowMillis, loc)
        val events = AstronomyEngine.getUpcomingEvents(nowMillis)
        val month = _uiState.value.selectedYearMonth
        val calDays = AstronomyEngine.buildCalendarMonth(month)

        _uiState.update { state ->
            state.copy(
                isSettingsLoaded = settingsLoaded,
                nowMillis = nowMillis,
                settings = settings,
                currentSnapshot = snapshot,
                twilightInfo = twilight,
                upcomingEvents = events,
                tonightSkyObjects = sky,
                calendarDays = calDays
            )
        }
    }

    fun refreshWeather(location: UserLocation = _uiState.value.settings.location) {
        viewModelScope.launch {
            _uiState.update { it.copy(weatherState = WeatherUiState.Loading) }
            val result = repository.fetchLiveWeather(location)
            _uiState.update { it.copy(weatherState = result) }
            refreshCacheSize()
        }
    }

    fun hasLocationPermission(): Boolean = locationProvider.hasLocationPermission()

    fun detectGpsLocation() {
        viewModelScope.launch {
            val detected = locationProvider.fetchCurrentLocation()
            if (detected != null) {
                settingsRepository.setLocation(detected, automatic = true)
                refreshAllLunarWidgets(appContext)
                postMessage("Location updated to ${detected.displayLabel}")
            } else {
                postMessage("Could not acquire GPS fix. You can select any city manually.")
            }
        }
    }

    fun selectManualLocation(location: UserLocation) {
        viewModelScope.launch {
            settingsRepository.setLocation(location.copy(isAutomaticGps = false), automatic = false)
            refreshAllLunarWidgets(appContext)
            postMessage("Location set to ${location.displayLabel}")
        }
    }

    fun searchCities(query: String) {
        _uiState.update { it.copy(citySearchQuery = query, isSearchingCities = true) }
        viewModelScope.launch {
            val results = repository.searchCities(query)
            _uiState.update { it.copy(citySearchResults = results, isSearchingCities = false) }
        }
    }

    // Calendar navigation
    fun previousMonth() {
        val target = _uiState.value.selectedYearMonth.minusMonths(1)
        _uiState.update {
            it.copy(
                selectedYearMonth = target,
                calendarDays = AstronomyEngine.buildCalendarMonth(target)
            )
        }
    }

    fun nextMonth() {
        val target = _uiState.value.selectedYearMonth.plusMonths(1)
        _uiState.update {
            it.copy(
                selectedYearMonth = target,
                calendarDays = AstronomyEngine.buildCalendarMonth(target)
            )
        }
    }

    fun jumpToTodayMonth() {
        val target = YearMonth.now()
        _uiState.update {
            it.copy(
                selectedYearMonth = target,
                calendarDays = AstronomyEngine.buildCalendarMonth(target)
            )
        }
    }

    fun getSnapshotForDate(date: LocalDate): LunarSnapshot {
        val noonMillis = date.atTime(12, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return AstronomyEngine.getLunarSnapshot(noonMillis, _uiState.value.settings.location)
    }

    // Settings mutations
    fun setThemeMode(mode: LunarThemeMode) {
        viewModelScope.launch { settingsRepository.setThemeMode(mode) }
    }

    fun setFontStyle(style: LunarFontStyle) {
        viewModelScope.launch { settingsRepository.setFontStyle(style) }
    }

    fun setIntroEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setIntroEnabled(enabled) }
    }

    fun markIntroCompleted() {
        viewModelScope.launch { settingsRepository.markIntroSeenAndAdvanceVariant() }
    }

    fun setIntroStyle(style: IntroMoonStyle) {
        viewModelScope.launch { settingsRepository.setIntroStyle(style) }
    }

    fun setReducedMotion(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setReducedMotion(enabled) }
    }

    fun resolveIntroStyle(): IntroMoonStyle {
        val s = _uiState.value.settings
        return if (s.introStyle == IntroMoonStyle.AUTO_ROTATE) {
            val concrete = IntroMoonStyle.concreteStyles
            concrete[s.introVariantIndex.mod(concrete.size)]
        } else {
            s.introStyle
        }
    }

    fun setDistanceUnit(unit: DistanceUnit) {
        viewModelScope.launch { settingsRepository.setDistanceUnit(unit) }
    }

    fun setUse24HourTime(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setUse24HourTime(enabled)
            refreshAllLunarWidgets(appContext)
        }
    }

    fun updateNotificationPrefs(update: (NotificationPreferences) -> NotificationPreferences) {
        viewModelScope.launch {
            settingsRepository.updateNotifications(update)
            val fresh = settingsRepository.getCurrentSettings()
            LunarNotificationManager.syncScheduledAlerts(appContext, fresh.notifications, fresh.location)
        }
    }

    fun sendPreviewNotification() {
        val snap = _uiState.value.currentSnapshot
        val sent = LunarNotificationManager.sendNotificationNow(
            context = appContext,
            id = 4199,
            title = "LUNAR • ${snap.phaseType.displayName}",
            body = "Currently ${snap.illuminationPercent.toInt()}% illuminated (Day ${String.format("%.1f", snap.lunarAgeDays)}). Quiet alerts are configured."
        )
        if (sent) {
            postMessage("Preview notification delivered")
        } else {
            postMessage("Grant notification permission to receive Lunar alerts")
        }
    }

    fun setCalendarPrefs(showIllum: Boolean, showEclipses: Boolean, startMonday: Boolean) {
        viewModelScope.launch { settingsRepository.setCalendarPrefs(showIllum, showEclipses, startMonday) }
    }

    fun setObservationPrefs(showCompass: Boolean, showRefractionNote: Boolean) {
        viewModelScope.launch { settingsRepository.setObservationPrefs(showCompass, showRefractionNote) }
    }

    fun setWallpaperPrefs(quality: WallpaperQuality, wifiOnly: Boolean) {
        viewModelScope.launch { settingsRepository.setWallpaperPrefs(quality, wifiOnly) }
    }

    fun setWidgetTheme(theme: WidgetTheme) {
        viewModelScope.launch {
            settingsRepository.setWidgetTheme(theme)
            refreshAllLunarWidgets(appContext)
            postMessage("Widget theme updated to ${theme.label}")
        }
    }

    // Favorites
    fun toggleFavoriteWallpaper(id: String) = viewModelScope.launch { settingsRepository.toggleFavoriteWallpaper(id) }
    fun toggleFavoriteArticle(id: String) = viewModelScope.launch { settingsRepository.toggleFavoriteArticle(id) }
    fun toggleFavoriteFeature(id: String) = viewModelScope.launch { settingsRepository.toggleFavoriteFeature(id) }
    fun toggleFavoriteEvent(id: String) = viewModelScope.launch { settingsRepository.toggleFavoriteEvent(id) }

    // Wallpaper actions
    fun saveWallpaper(item: WallpaperItem) {
        viewModelScope.launch {
            val settings = _uiState.value.settings
            if (settings.wifiOnlyDownloads && item.imageUrl.isNotBlank() && !repository.isOnWifi()) {
                postMessage("Wi-Fi-only downloads enabled in Settings. Saving offline high-res artwork.")
            }
            val result = WallpaperManagerHelper.saveToGallery(appContext, item, settings.wallpaperQuality)
            result.onSuccess {
                settingsRepository.recordDownloadedWallpaper(item.id)
                postMessage(it)
            }.onFailure { postMessage("Could not save wallpaper: ${it.localizedMessage ?: "error"}") }
        }
    }

    fun applyDeviceWallpaper(item: WallpaperItem, flags: Int) {
        viewModelScope.launch {
            val result = WallpaperManagerHelper.setDeviceWallpaper(appContext, item, _uiState.value.settings.wallpaperQuality, flags)
            result.onSuccess { postMessage(it) }
                .onFailure { postMessage("Could not set wallpaper: ${it.localizedMessage ?: "error"}") }
        }
    }

    fun shareWallpaper(item: WallpaperItem) {
        viewModelScope.launch {
            val result = WallpaperManagerHelper.shareWallpaper(appContext, item, _uiState.value.settings.wallpaperQuality)
            result.onFailure { postMessage("Unable to share image right now") }
        }
    }

    // Storage & Cache
    fun refreshCacheSize() {
        viewModelScope.launch {
            val size = repository.calculateCacheSizeBytes()
            _uiState.update { it.copy(cacheSizeBytes = size) }
        }
    }

    fun clearCachePreservingFavorites() {
        viewModelScope.launch {
            val newSize = repository.clearAppCachePreservingFavorites()
            _uiState.update { it.copy(cacheSizeBytes = newSize) }
            postMessage("Cached images & temporary files cleared. Favorites preserved.")
        }
    }

    // External intents (Creator Instagram & Feedback)
    fun openCreatorInstagram(context: Context) {
        val url = "https://www.instagram.com/unfiltered.java/"
        val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            setPackage("com.instagram.android")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(instagramIntent)
        } catch (_: Exception) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            runCatching { context.startActivity(browserIntent) }
                .onFailure { postMessage("Could not open browser for Instagram") }
        }
    }

    fun sendFeedbackEmail(context: Context, subject: String) {
        val deviceInfo = "Device: ${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE})\nApp: LUNAR v1.0.0\n\n"
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, "[LUNAR App] $subject")
            putExtra(Intent.EXTRA_TEXT, deviceInfo)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        runCatching {
            context.startActivity(Intent.createChooser(intent, subject).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }.onFailure {
            postMessage("No email client found on device")
        }
    }

    fun postMessage(msg: String) {
        _uiState.update { it.copy(statusMessage = msg) }
    }

    fun consumeMessage() {
        _uiState.update { it.copy(statusMessage = null) }
    }
}
