package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.astronomy.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.lunarDataStore: DataStore<Preferences> by preferencesDataStore(name = "lunar_preferences")

class SettingsRepository(private val context: Context) {

    private object Keys {
        val LOCATION_NAME = stringPreferencesKey("loc_name")
        val LOCATION_REGION = stringPreferencesKey("loc_region")
        val LOCATION_LAT = doublePreferencesKey("loc_lat")
        val LOCATION_LON = doublePreferencesKey("loc_lon")
        val USE_AUTO_LOCATION = booleanPreferencesKey("use_auto_location")

        val THEME_MODE = stringPreferencesKey("theme_mode")
        val FONT_STYLE = stringPreferencesKey("font_style")
        val DISTANCE_UNIT = stringPreferencesKey("distance_unit")
        val USE_24_HOUR = booleanPreferencesKey("use_24_hour")

        val INTRO_ENABLED = booleanPreferencesKey("intro_enabled")
        val HAS_SEEN_INTRO = booleanPreferencesKey("has_seen_intro")
        val INTRO_STYLE = stringPreferencesKey("intro_style")
        val INTRO_VARIANT_INDEX = intPreferencesKey("intro_variant_index")
        val REDUCED_MOTION = booleanPreferencesKey("reduced_motion")

        val NOTIF_MOONRISE = booleanPreferencesKey("notif_moonrise")
        val NOTIF_FULL_MOON = booleanPreferencesKey("notif_full_moon")
        val NOTIF_NEW_MOON = booleanPreferencesKey("notif_new_moon")
        val NOTIF_ECLIPSE = booleanPreferencesKey("notif_eclipse")
        val NOTIF_MAJOR_EVENTS = booleanPreferencesKey("notif_major_events")
        val NOTIF_OBSERVATION = booleanPreferencesKey("notif_observation")
        val NOTIF_DAILY = booleanPreferencesKey("notif_daily")

        val CAL_SHOW_ILLUM = booleanPreferencesKey("cal_show_illum")
        val CAL_SHOW_ECLIPSES = booleanPreferencesKey("cal_show_eclipses")
        val CAL_START_MONDAY = booleanPreferencesKey("cal_start_monday")

        val OBS_SHOW_COMPASS = booleanPreferencesKey("obs_show_compass")
        val OBS_SHOW_REFRACTION = booleanPreferencesKey("obs_show_refraction")

        val WALLPAPER_QUALITY = stringPreferencesKey("wallpaper_quality")
        val WIFI_ONLY_DOWNLOADS = booleanPreferencesKey("wifi_only_downloads")
        val WIDGET_THEME = stringPreferencesKey("widget_theme")

        val FAV_WALLPAPERS = stringSetPreferencesKey("fav_wallpapers")
        val FAV_ARTICLES = stringSetPreferencesKey("fav_articles")
        val FAV_FEATURES = stringSetPreferencesKey("fav_features")
        val FAV_EVENTS = stringSetPreferencesKey("fav_events")
        val DOWNLOADED_WALLPAPERS = stringSetPreferencesKey("downloaded_wallpapers")

        val CACHE_CLOUD = intPreferencesKey("cache_weather_cloud")
        val CACHE_TEMP = doublePreferencesKey("cache_weather_temp")
        val CACHE_VIS = doublePreferencesKey("cache_weather_vis")
        val CACHE_PRECIP = intPreferencesKey("cache_weather_precip")
        val CACHE_WIND = doublePreferencesKey("cache_weather_wind")
        val CACHE_DESC = stringPreferencesKey("cache_weather_desc")
        val CACHE_TIME = longPreferencesKey("cache_weather_time")
        val CACHE_LOC = stringPreferencesKey("cache_weather_loc")
    }

    val settingsFlow: Flow<AppSettings> = context.lunarDataStore.data.map { prefs ->
        val loc = UserLocation(
            name = prefs[Keys.LOCATION_NAME] ?: UserLocation.DEFAULT.name,
            region = prefs[Keys.LOCATION_REGION] ?: UserLocation.DEFAULT.region,
            latitude = prefs[Keys.LOCATION_LAT] ?: UserLocation.DEFAULT.latitude,
            longitude = prefs[Keys.LOCATION_LON] ?: UserLocation.DEFAULT.longitude,
            isAutomaticGps = prefs[Keys.USE_AUTO_LOCATION] ?: false
        )
        AppSettings(
            location = loc,
            useAutomaticLocation = prefs[Keys.USE_AUTO_LOCATION] ?: false,
            themeMode = prefs[Keys.THEME_MODE]?.let { runCatching { LunarThemeMode.valueOf(it) }.getOrNull() }
                ?: LunarThemeMode.OBSIDIAN,
            fontStyle = prefs[Keys.FONT_STYLE]?.let { runCatching { LunarFontStyle.valueOf(it) }.getOrNull() }
                ?: LunarFontStyle.ROBOTO,
            distanceUnit = prefs[Keys.DISTANCE_UNIT]?.let { runCatching { DistanceUnit.valueOf(it) }.getOrNull() }
                ?: DistanceUnit.KILOMETERS,
            use24HourTime = prefs[Keys.USE_24_HOUR] ?: false,
            introEnabled = prefs[Keys.INTRO_ENABLED] ?: true,
            hasSeenIntro = prefs[Keys.HAS_SEEN_INTRO] ?: false,
            introStyle = prefs[Keys.INTRO_STYLE]?.let { runCatching { IntroMoonStyle.valueOf(it) }.getOrNull() }
                ?: IntroMoonStyle.AUTO_ROTATE,
            introVariantIndex = prefs[Keys.INTRO_VARIANT_INDEX] ?: 0,
            reducedMotion = prefs[Keys.REDUCED_MOTION] ?: false,
            notifications = NotificationPreferences(
                moonriseAlerts = prefs[Keys.NOTIF_MOONRISE] ?: false,
                fullMoonAlerts = prefs[Keys.NOTIF_FULL_MOON] ?: true,
                newMoonAlerts = prefs[Keys.NOTIF_NEW_MOON] ?: false,
                lunarEclipseAlerts = prefs[Keys.NOTIF_ECLIPSE] ?: true,
                majorEventsAlerts = prefs[Keys.NOTIF_MAJOR_EVENTS] ?: true,
                observationOpportunities = prefs[Keys.NOTIF_OBSERVATION] ?: false,
                dailyMoonUpdate = prefs[Keys.NOTIF_DAILY] ?: false
            ),
            showCalendarIllumination = prefs[Keys.CAL_SHOW_ILLUM] ?: true,
            showCalendarEclipses = prefs[Keys.CAL_SHOW_ECLIPSES] ?: true,
            startWeekOnMonday = prefs[Keys.CAL_START_MONDAY] ?: false,
            showHorizonCompass = prefs[Keys.OBS_SHOW_COMPASS] ?: true,
            showAtmosphericRefractionNote = prefs[Keys.OBS_SHOW_REFRACTION] ?: true,
            wallpaperQuality = prefs[Keys.WALLPAPER_QUALITY]?.let { runCatching { WallpaperQuality.valueOf(it) }.getOrNull() }
                ?: WallpaperQuality.ORIGINAL,
            wifiOnlyDownloads = prefs[Keys.WIFI_ONLY_DOWNLOADS] ?: false,
            widgetTheme = prefs[Keys.WIDGET_THEME]?.let { runCatching { WidgetTheme.valueOf(it) }.getOrNull() }
                ?: WidgetTheme.BLACK,
            favoriteWallpaperIds = prefs[Keys.FAV_WALLPAPERS] ?: emptySet(),
            favoriteArticleIds = prefs[Keys.FAV_ARTICLES] ?: emptySet(),
            favoriteFeatureIds = prefs[Keys.FAV_FEATURES] ?: emptySet(),
            favoriteEventIds = prefs[Keys.FAV_EVENTS] ?: emptySet(),
            downloadedWallpaperIds = prefs[Keys.DOWNLOADED_WALLPAPERS] ?: emptySet()
        )
    }

    suspend fun getCurrentSettings(): AppSettings = settingsFlow.first()

    suspend fun setLocation(location: UserLocation, automatic: Boolean) {
        context.lunarDataStore.edit { p ->
            p[Keys.LOCATION_NAME] = location.name
            p[Keys.LOCATION_REGION] = location.region
            p[Keys.LOCATION_LAT] = location.latitude
            p[Keys.LOCATION_LON] = location.longitude
            p[Keys.USE_AUTO_LOCATION] = automatic
        }
    }

    suspend fun setThemeMode(mode: LunarThemeMode) {
        context.lunarDataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }

    suspend fun setFontStyle(style: LunarFontStyle) {
        context.lunarDataStore.edit { it[Keys.FONT_STYLE] = style.name }
    }

    suspend fun setDistanceUnit(unit: DistanceUnit) {
        context.lunarDataStore.edit { it[Keys.DISTANCE_UNIT] = unit.name }
    }

    suspend fun setUse24HourTime(enabled: Boolean) {
        context.lunarDataStore.edit { it[Keys.USE_24_HOUR] = enabled }
    }

    suspend fun setIntroEnabled(enabled: Boolean) {
        context.lunarDataStore.edit { it[Keys.INTRO_ENABLED] = enabled }
    }

    suspend fun markIntroSeenAndAdvanceVariant() {
        context.lunarDataStore.edit { p ->
            p[Keys.HAS_SEEN_INTRO] = true
            val currentIdx = p[Keys.INTRO_VARIANT_INDEX] ?: 0
            p[Keys.INTRO_VARIANT_INDEX] = (currentIdx + 1) % IntroMoonStyle.concreteStyles.size
        }
    }

    suspend fun setIntroStyle(style: IntroMoonStyle) {
        context.lunarDataStore.edit { it[Keys.INTRO_STYLE] = style.name }
    }

    suspend fun setReducedMotion(enabled: Boolean) {
        context.lunarDataStore.edit { it[Keys.REDUCED_MOTION] = enabled }
    }

    suspend fun recordDownloadedWallpaper(id: String) {
        context.lunarDataStore.edit { p ->
            val current = p[Keys.DOWNLOADED_WALLPAPERS] ?: emptySet()
            p[Keys.DOWNLOADED_WALLPAPERS] = current + id
        }
    }

    suspend fun updateNotifications(update: (NotificationPreferences) -> NotificationPreferences) {
        context.lunarDataStore.edit { p ->
            val curr = NotificationPreferences(
                moonriseAlerts = p[Keys.NOTIF_MOONRISE] ?: false,
                fullMoonAlerts = p[Keys.NOTIF_FULL_MOON] ?: true,
                newMoonAlerts = p[Keys.NOTIF_NEW_MOON] ?: false,
                lunarEclipseAlerts = p[Keys.NOTIF_ECLIPSE] ?: true,
                majorEventsAlerts = p[Keys.NOTIF_MAJOR_EVENTS] ?: true,
                observationOpportunities = p[Keys.NOTIF_OBSERVATION] ?: false,
                dailyMoonUpdate = p[Keys.NOTIF_DAILY] ?: false
            )
            val next = update(curr)
            p[Keys.NOTIF_MOONRISE] = next.moonriseAlerts
            p[Keys.NOTIF_FULL_MOON] = next.fullMoonAlerts
            p[Keys.NOTIF_NEW_MOON] = next.newMoonAlerts
            p[Keys.NOTIF_ECLIPSE] = next.lunarEclipseAlerts
            p[Keys.NOTIF_MAJOR_EVENTS] = next.majorEventsAlerts
            p[Keys.NOTIF_OBSERVATION] = next.observationOpportunities
            p[Keys.NOTIF_DAILY] = next.dailyMoonUpdate
        }
    }

    suspend fun setCalendarPrefs(showIllum: Boolean, showEclipses: Boolean, startMonday: Boolean) {
        context.lunarDataStore.edit { p ->
            p[Keys.CAL_SHOW_ILLUM] = showIllum
            p[Keys.CAL_SHOW_ECLIPSES] = showEclipses
            p[Keys.CAL_START_MONDAY] = startMonday
        }
    }

    suspend fun setObservationPrefs(showCompass: Boolean, showRefractionNote: Boolean) {
        context.lunarDataStore.edit { p ->
            p[Keys.OBS_SHOW_COMPASS] = showCompass
            p[Keys.OBS_SHOW_REFRACTION] = showRefractionNote
        }
    }

    suspend fun setWallpaperPrefs(quality: WallpaperQuality, wifiOnly: Boolean) {
        context.lunarDataStore.edit { p ->
            p[Keys.WALLPAPER_QUALITY] = quality.name
            p[Keys.WIFI_ONLY_DOWNLOADS] = wifiOnly
        }
    }

    suspend fun setWidgetTheme(theme: WidgetTheme) {
        context.lunarDataStore.edit { it[Keys.WIDGET_THEME] = theme.name }
    }

    suspend fun toggleFavoriteWallpaper(id: String) = toggleSetItem(Keys.FAV_WALLPAPERS, id)
    suspend fun toggleFavoriteArticle(id: String) = toggleSetItem(Keys.FAV_ARTICLES, id)
    suspend fun toggleFavoriteFeature(id: String) = toggleSetItem(Keys.FAV_FEATURES, id)
    suspend fun toggleFavoriteEvent(id: String) = toggleSetItem(Keys.FAV_EVENTS, id)

    private suspend fun toggleSetItem(key: Preferences.Key<Set<String>>, id: String) {
        context.lunarDataStore.edit { p ->
            val current = p[key] ?: emptySet()
            p[key] = if (id in current) current - id else current + id
        }
    }

    suspend fun saveCachedWeather(snapshot: WeatherSnapshot) {
        context.lunarDataStore.edit { p ->
            p[Keys.CACHE_CLOUD] = snapshot.cloudCoverPercent
            p[Keys.CACHE_TEMP] = snapshot.temperatureCelsius
            snapshot.visibilityMeters?.let { p[Keys.CACHE_VIS] = it }
            p[Keys.CACHE_PRECIP] = snapshot.precipitationProbPercent
            p[Keys.CACHE_WIND] = snapshot.windSpeedKmh
            p[Keys.CACHE_DESC] = snapshot.weatherDescription
            p[Keys.CACHE_TIME] = snapshot.fetchedAtMillis
            p[Keys.CACHE_LOC] = snapshot.locationLabel
        }
    }

    suspend fun getCachedWeather(): WeatherSnapshot? {
        val p = context.lunarDataStore.data.first()
        val time = p[Keys.CACHE_TIME] ?: return null
        return WeatherSnapshot(
            cloudCoverPercent = p[Keys.CACHE_CLOUD] ?: return null,
            temperatureCelsius = p[Keys.CACHE_TEMP] ?: 15.0,
            visibilityMeters = p[Keys.CACHE_VIS],
            precipitationProbPercent = p[Keys.CACHE_PRECIP] ?: 0,
            windSpeedKmh = p[Keys.CACHE_WIND] ?: 0.0,
            weatherDescription = p[Keys.CACHE_DESC] ?: "Cached conditions",
            fetchedAtMillis = time,
            isFromCache = true,
            locationLabel = p[Keys.CACHE_LOC] ?: ""
        )
    }

    suspend fun clearWeatherCacheOnly() {
        context.lunarDataStore.edit { p ->
            p.remove(Keys.CACHE_CLOUD)
            p.remove(Keys.CACHE_TEMP)
            p.remove(Keys.CACHE_VIS)
            p.remove(Keys.CACHE_PRECIP)
            p.remove(Keys.CACHE_WIND)
            p.remove(Keys.CACHE_DESC)
            p.remove(Keys.CACHE_TIME)
            p.remove(Keys.CACHE_LOC)
        }
    }
}
