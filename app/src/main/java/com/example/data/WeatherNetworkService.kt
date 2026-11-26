package com.example.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class OpenMeteoResponse(
    val current: OpenMeteoCurrent? = null
)

@JsonClass(generateAdapter = true)
data class OpenMeteoCurrent(
    @Json(name = "temperature_2m") val temperature2m: Double? = null,
    @Json(name = "cloud_cover") val cloudCover: Int? = null,
    @Json(name = "weather_code") val weatherCode: Int? = null,
    @Json(name = "wind_speed_10m") val windSpeed10m: Double? = null,
    val precipitation: Double? = null
)

@JsonClass(generateAdapter = true)
data class GeocodingResponse(
    val results: List<GeocodingResult>? = null
)

@JsonClass(generateAdapter = true)
data class GeocodingResult(
    val name: String,
    val country: String? = null,
    val admin1: String? = null,
    val latitude: Double,
    val longitude: Double
)

interface OpenMeteoForecastApi {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = "temperature_2m,cloud_cover,weather_code,wind_speed_10m,precipitation",
        @Query("timezone") timezone: String = "auto"
    ): OpenMeteoResponse
}

interface OpenMeteoGeocodingApi {
    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") query: String,
        @Query("count") count: Int = 8,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): GeocodingResponse
}

object NetworkClient {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    val weatherApi: OpenMeteoForecastApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OpenMeteoForecastApi::class.java)
    }

    val geocodingApi: OpenMeteoGeocodingApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(OpenMeteoGeocodingApi::class.java)
    }

    fun wmoCodeDescription(code: Int?): String = when (code) {
        0 -> "Clear sky"
        1 -> "Mainly clear"
        2 -> "Partly cloudy"
        3 -> "Overcast"
        45, 48 -> "Fog / mist"
        51, 53, 55 -> "Light drizzle"
        61, 63, 65 -> "Rain"
        71, 73, 75 -> "Snowfall"
        80, 81, 82 -> "Rain showers"
        95, 96, 99 -> "Thunderstorm"
        else -> "Variable sky"
    }
}
