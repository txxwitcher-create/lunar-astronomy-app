package com.example.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.astronomy.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class LunarRepository(
    private val context: Context,
    val settingsRepository: SettingsRepository,
    val locationProvider: LocationProvider
) {

    suspend fun fetchLiveWeather(location: UserLocation): WeatherUiState = withContext(Dispatchers.IO) {
        try {
            val response = NetworkClient.weatherApi.getCurrentWeather(
                latitude = location.latitude,
                longitude = location.longitude
            )
            val current = response.current
            if (current != null && current.cloudCover != null && current.temperature2m != null) {
                val snapshot = WeatherSnapshot(
                    cloudCoverPercent = current.cloudCover.coerceIn(0, 100),
                    temperatureCelsius = current.temperature2m,
                    visibilityMeters = null,
                    precipitationProbPercent = if ((current.precipitation ?: 0.0) > 0.0) 80 else 10,
                    windSpeedKmh = current.windSpeed10m ?: 0.0,
                    weatherDescription = NetworkClient.wmoCodeDescription(current.weatherCode),
                    fetchedAtMillis = System.currentTimeMillis(),
                    isFromCache = false,
                    locationLabel = location.displayLabel
                )
                settingsRepository.saveCachedWeather(snapshot)
                return@withContext WeatherUiState.Available(snapshot)
            }
        } catch (_: Exception) {
            // Fall through gracefully to offline cache if available
        }

        val cached = settingsRepository.getCachedWeather()
        if (cached != null) {
            WeatherUiState.Available(cached.copy(isFromCache = true))
        } else {
            WeatherUiState.Unavailable(
                "Weather data unavailable offline. Calculated astronomical data remains fully accurate."
            )
        }
    }

    suspend fun searchCities(query: String): List<UserLocation> = withContext(Dispatchers.IO) {
        val trimmed = query.trim()
        if (trimmed.length < 2) return@withContext LunarContentCatalog.presetCities
        try {
            val res = NetworkClient.geocodingApi.searchCity(trimmed)
            val remote = res.results.orEmpty().map { r ->
                UserLocation(
                    name = r.name,
                    region = listOfNotNull(r.admin1, r.country).distinct().joinToString(", "),
                    latitude = r.latitude,
                    longitude = r.longitude,
                    isAutomaticGps = false
                )
            }
            if (remote.isNotEmpty()) return@withContext remote
        } catch (_: Exception) {
            // Fallback to local city filtering offline
        }
        LunarContentCatalog.presetCities.filter {
            it.name.contains(trimmed, ignoreCase = true) || it.region.contains(trimmed, ignoreCase = true)
        }
    }

    fun isOnWifi(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return true
        val net = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(net) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    suspend fun calculateCacheSizeBytes(): Long = withContext(Dispatchers.IO) {
        dirSize(context.cacheDir)
    }

    suspend fun clearAppCachePreservingFavorites(): Long = withContext(Dispatchers.IO) {
        // Clear Coil image disk cache & temp shared files, plus weather cache — NEVER touches DataStore favorites or preferences
        context.cacheDir.listFiles()?.forEach { child ->
            child.deleteRecursively()
        }
        settingsRepository.clearWeatherCacheOnly()
        dirSize(context.cacheDir)
    }

    private fun dirSize(file: File?): Long {
        if (file == null || !file.exists()) return 0L
        if (file.isFile) return file.length()
        return file.listFiles()?.sumOf { dirSize(it) } ?: 0L
    }
}
