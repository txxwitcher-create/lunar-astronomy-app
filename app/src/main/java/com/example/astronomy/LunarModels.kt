package com.example.astronomy

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

enum class LunarPhaseType(
    val displayName: String,
    val shortName: String,
    val nominalAgeDays: Double,
    val nominalIllumination: Int,
    val isWaxing: Boolean?
) {
    NEW_MOON("New Moon", "New", 0.0, 0, null),
    WAXING_CRESCENT("Waxing Crescent", "Wax. Cresc.", 3.7, 25, true),
    FIRST_QUARTER("First Quarter", "First Qtr", 7.4, 50, true),
    WAXING_GIBBOUS("Waxing Gibbous", "Wax. Gibb.", 11.1, 78, true),
    FULL_MOON("Full Moon", "Full", 14.8, 100, null),
    WANING_GIBBOUS("Waning Gibbous", "Wan. Gibb.", 18.5, 78, false),
    LAST_QUARTER("Last Quarter", "Last Qtr", 22.1, 50, false),
    WANING_CRESCENT("Waning Crescent", "Wan. Cresc.", 25.8, 22, false);

    fun previous(): LunarPhaseType = entries[(ordinal - 1 + entries.size) % entries.size]
    fun next(): LunarPhaseType = entries[(ordinal + 1) % entries.size]
}

enum class LunarFontStyle(val label: String, val description: String) {
    ROBOTO("Roboto (Default)", "Balanced modern UI & crisp readability"),
    SPACE_GROTESK("Space Grotesk", "Editorial astronomy geometry across headings & UI"),
    IBM_PLEX_MONO("IBM Plex Mono", "Observatory instrument & scientific telemetry aesthetic")
}

enum class IntroMoonStyle(
    val label: String,
    val subtitle: String,
    val phaseFraction: Double
) {
    AUTO_ROTATE("Auto-Rotate Styles", "Cycles across all 10 cinematic variants", 0.50),
    FULL_MOON("Full Moon", "Complete illuminated Moon with detailed craters", 0.50),
    CRESCENT_MOON("Crescent Moon", "Dramatic silver crescent emerging from darkness", 0.14),
    BLOOD_MOON("Blood Moon", "Deep crimson lunar appearance", 0.50),
    BLUE_MOON("Blue Moon", "Cool cobalt cinematic illumination", 0.50),
    GOLDEN_MOON("Golden Moon", "Warm amber horizon glow", 0.44),
    LUNAR_ECLIPSE("Lunar Eclipse", "Earth's shadow gradually crossing the disc", 0.50),
    SHADOW_MOON("Shadow Moon", "Emerging dramatically from deep umbra", 0.28),
    CRATER_MOON("Crater Moon", "High-contrast close-up crater relief", 0.30),
    MOONRISE("Moonrise", "Ascending above a dark mountain horizon", 0.48),
    LUNAR_SURFACE("Lunar Surface", "Orbital pass over ancient basalt terrain", 0.34);

    companion object {
        val concreteStyles: List<IntroMoonStyle> = entries.filter { it != AUTO_ROTATE }
    }
}

data class UserLocation(
    val name: String,
    val region: String,
    val latitude: Double,
    val longitude: Double,
    val isAutomaticGps: Boolean = false
) {
    val displayLabel: String
        get() = if (region.isNotBlank()) "$name, $region" else name

    companion object {
        val DEFAULT = UserLocation("Greenwich", "United Kingdom", 51.4769, -0.0005, false)
    }
}

data class LunarSnapshot(
    val timestampMillis: Long,
    val localDate: LocalDate,
    val phaseType: LunarPhaseType,
    val phaseFraction: Double,
    val illuminationPercent: Double,
    val lunarAgeDays: Double,
    val isWaxing: Boolean,
    val distanceKm: Double,
    val angularDiameterArcmin: Double,
    val altitudeDeg: Double,
    val azimuthDeg: Double,
    val compassDirection: String,
    val isAboveHorizon: Boolean,
    val moonriseMillis: Long?,
    val moonsetMillis: Long?,
    val eclipticLongitudeDeg: Double,
    val zodiacSign: String,
    val isSupermoonCandidate: Boolean
)

data class TwilightInfo(
    val sunriseMillis: Long?,
    val sunsetMillis: Long?,
    val civilDuskMillis: Long?,
    val nauticalDuskMillis: Long?,
    val astronomicalDuskMillis: Long?,
    val astronomicalDawnMillis: Long?,
    val nauticalDawnMillis: Long?,
    val civilDawnMillis: Long?,
    val currentSkyStage: String,
    val bestObservationWindow: String
)

enum class LunarEventType(val label: String) {
    FULL_MOON("Full Moon"),
    NEW_MOON("New Moon"),
    SUPERMOON("Supermoon"),
    TOTAL_LUNAR_ECLIPSE("Total Lunar Eclipse"),
    PARTIAL_LUNAR_ECLIPSE("Partial Lunar Eclipse"),
    PENUMBRAL_LUNAR_ECLIPSE("Penumbral Eclipse"),
    QUARTER_PHASE("Quarter Moon"),
    METEOR_SHOWER("Meteor Shower")
}

data class EclipseStages(
    val penumbralStartMillis: Long,
    val partialStartMillis: Long?,
    val totalityStartMillis: Long?,
    val maximumEclipseMillis: Long,
    val totalityEndMillis: Long?,
    val partialEndMillis: Long?,
    val penumbralEndMillis: Long,
    val umbralMagnitude: Double,
    val visibilityRegion: String
)

data class LunarEvent(
    val id: String,
    val title: String,
    val subtitle: String,
    val type: LunarEventType,
    val timestampMillis: Long,
    val visibilitySummary: String,
    val scientificExplanation: String,
    val observationGuide: String,
    val photographyTips: String,
    val imageUrl: String,
    val fallbackVisualType: VisualFallbackType,
    val distanceKm: Double? = null,
    val eclipseStages: EclipseStages? = null
)

data class CalendarDayData(
    val date: LocalDate,
    val phaseType: LunarPhaseType,
    val phaseFraction: Double,
    val illuminationPercent: Double,
    val lunarAgeDays: Double,
    val isMajorPhaseDay: Boolean,
    val majorPhaseLabel: String?,
    val hasEclipse: Boolean,
    val hasEvent: Boolean,
    val eventTitle: String?
)

enum class LunarFeatureType(val label: String) {
    CRATER("Crater Atlas"),
    MARE("Lunar Maria"),
    MOUNTAIN("Mountains & Valleys"),
    LUNAR_POLE("Lunar Poles"),
    RILLE_CHANNEL("Rilles & Channels"),
    LANDING_SITE("Historic Landing Sites"),
    LUNAR_GEOLOGY("Lunar Geology"),
    OCEANUS("Basalt Plain")
}

data class LunarFeature(
    val id: String,
    val name: String,
    val latinMeaning: String,
    val type: LunarFeatureType,
    val mapX: Float,
    val mapY: Float,
    val coordinates: String,
    val diameterKm: Double,
    val ageEstimate: String,
    val summary: String,
    val formation: String,
    val characteristics: String,
    val scientificSignificance: String,
    val explorationHistory: String,
    val relatedMissions: List<String>,
    val relatedFeatureIds: List<String>,
    val bestViewingPhase: String,
    val imageUrl: String,
    val fallbackVisualType: VisualFallbackType
)

data class PlanetInfo(
    val id: String,
    val name: String,
    val subtitle: String,
    val orderFromSun: Int,
    val diameterKm: Double,
    val distanceFromSunAu: String,
    val moonsCount: String,
    val rotationPeriod: String,
    val orbitalPeriod: String,
    val atmosphere: String,
    val surface: String,
    val missions: List<String>,
    val facts: List<String>,
    val imageUrl: String,
    val imageCredit: String,
    val accentHex: Long
)

data class ConstellationInfo(
    val id: String,
    val name: String,
    val latinMeaning: String,
    val season: String,
    val hemisphere: String,
    val mainStars: List<String>,
    val starPattern: String,
    val howToLocate: String,
    val originStory: String,
    val scientificNotes: String,
    val imageUrl: String,
    val imageCredit: String
)

data class DeepSpaceObject(
    val id: String,
    val name: String,
    val catalogId: String,
    val category: String,
    val distanceLabel: String,
    val constellation: String,
    val summary: String,
    val scienceDetail: String,
    val observationNotes: String,
    val imageUrl: String,
    val imageCredit: String
)

data class SpaceMissionInfo(
    val id: String,
    val name: String,
    val agency: String,
    val era: String,
    val year: String,
    val target: String,
    val summary: String,
    val achievements: List<String>,
    val imageUrl: String,
    val imageCredit: String
)

enum class SkyObjectType(val label: String) {
    PLANET("Planet"),
    CONSTELLATION("Constellation"),
    BRIGHT_STAR("Bright Star"),
    DEEP_SKY("Deep Sky"),
    METEOR_SHOWER("Meteor Shower")
}

data class SkyObject(
    val id: String,
    val name: String,
    val type: SkyObjectType,
    val subtitle: String,
    val altitudeDeg: Double?,
    val azimuthDeg: Double?,
    val compassDirection: String,
    val isCurrentlyVisible: Boolean,
    val visibilityLabel: String,
    val whatItIs: String,
    val whenToObserve: String,
    val whereToLook: String,
    val howToFind: String,
    val beginnerTips: String,
    val magnitudeLabel: String
)

enum class LearnCategory(val title: String, val subtitle: String) {
    MOON_BASICS("Moon Science", "Origin, internal structure, gravity, dust & water ice"),
    MOON_MYSTERIES("Moon Mysteries", "Far side, Moon illusion, red eclipses & tides"),
    MOON_PHASES("Moon Phases", "Sunlight geometry, waxing, waning & quarters"),
    MOON_MOTION("Moon Motion", "Orbits, synodic vs sidereal months & libration"),
    EARTH_AND_MOON("Earth & Moon", "Gravity, ocean tides & tidal locking"),
    MOON_EXPLORATION("Human Exploration", "Apollo 11–17, lunar rovers, robotic & Artemis"),
    MILKY_WAY("Milky Way", "Galactic structure, dark skies & photography")
}

data class ArticleSection(
    val heading: String,
    val body: String
)

data class LearnArticle(
    val id: String,
    val title: String,
    val category: LearnCategory,
    val readTimeMinutes: Int,
    val introduction: String,
    val sections: List<ArticleSection>,
    val keyFacts: List<String>,
    val relatedArticleIds: List<String>,
    val imageUrl: String,
    val imageCredit: String,
    val fallbackVisualType: VisualFallbackType
)

enum class PhotoGuideTrack(val label: String) {
    SMARTPHONE("Smartphone"),
    CAMERA("DSLR / Mirrorless")
}

data class PhotoSettingItem(
    val name: String,
    val value: String,
    val explanation: String
)

data class PhotoGuide(
    val id: String,
    val title: String,
    val track: PhotoGuideTrack,
    val difficulty: String,
    val summary: String,
    val settings: List<PhotoSettingItem>,
    val steps: List<String>,
    val commonMistakes: List<String>,
    val expectedResult: String,
    val imageUrl: String,
    val imageCredit: String,
    val fallbackVisualType: VisualFallbackType
)

enum class WallpaperCategory(val label: String) {
    ALL("All"),
    MOON("Moon"),
    LUNAR_SURFACE("Lunar Surface"),
    BLOOD_MOON("Blood Moon"),
    SUPERMOON("Supermoon & Blue"),
    EARTHRISE("Earthrise"),
    MOON_MOUNTAINS("Moon & Mountains"),
    MOON_OCEAN("Moon & Ocean"),
    PLANETS("Planets & Saturn"),
    GALAXIES("Galaxies & Milky Way"),
    NEBULAE("Nebulae & Deep Space"),
    DEEP_SPACE("Deep Space"),
    NIGHT_SKY("Night Sky & Lighting"),
    MINIMAL("Minimal Astronomy"),
    SPACE_PHOTOGRAPHY("Space Photography")
}

enum class VisualFallbackType {
    FULL_MOON,
    CRESCENT_MOON,
    BLOOD_MOON,
    BLUE_MOON,
    GOLDEN_MOON,
    LUNAR_SURFACE,
    MOON_OCEAN,
    MOON_MOUNTAINS,
    DEEP_SPACE,
    GALAXY,
    NIGHT_SKY,
    MINIMAL_ORBIT,
    APOLLO_SURFACE
}

data class WallpaperItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: WallpaperCategory,
    val collection: String,
    val resolution: String,
    val credit: String,
    val imageUrl: String,
    val fallbackVisualType: VisualFallbackType,
    val phaseFractionOverride: Double = 0.5,
    val viewingAngle: String = "Telescope View",
    val lightingMood: String = "Deep Night"
)

data class PhaseDetailContent(
    val phase: LunarPhaseType,
    val headline: String,
    val explanation: String,
    val appearanceFromEarth: String,
    val whyItOccurs: String,
    val durationInfo: String,
    val typicalRiseSet: String,
    val observationTips: List<String>,
    val photographyHint: String
)

data class WeatherSnapshot(
    val cloudCoverPercent: Int,
    val temperatureCelsius: Double,
    val visibilityMeters: Double?,
    val precipitationProbPercent: Int,
    val windSpeedKmh: Double,
    val weatherDescription: String,
    val fetchedAtMillis: Long,
    val isFromCache: Boolean,
    val locationLabel: String
)

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Available(val weather: WeatherSnapshot) : WeatherUiState
    data class Unavailable(val reason: String) : WeatherUiState
}

enum class LunarThemeMode(val label: String, val subtitle: String) {
    OBSIDIAN("Pure Obsidian", "True OLED black & crisp silver starlight"),
    MIDNIGHT("Midnight Navy", "Deep observatory indigo & celestial blue"),
    ECLIPSE("Eclipse Charcoal", "Warm slate charcoal & muted violet"),
    SYSTEM("System Default", "Adapts to your Android system appearance")
}

enum class DistanceUnit(val label: String, val unitSuffix: String) {
    KILOMETERS("Metric (km, °C)", "km"),
    MILES("Imperial (mi, °F)", "mi")
}

enum class WallpaperQuality(val label: String, val description: String) {
    ORIGINAL("Ultra / Original", "Highest detail for modern high-DPI displays"),
    BALANCED("High (Balanced)", "Sharp quality with lighter storage footprint"),
    DATA_SAVER("Standard", "Fastest downloads on mobile connections")
}

enum class WidgetTheme(val label: String, val description: String) {
    BLACK("Pure Black", "Seamless OLED dark card with hairline border"),
    MIDNIGHT_BLUE("Midnight Blue", "Deep observatory navy gradient"),
    TRANSPARENT("Transparent", "Translucent dark glass over your wallpaper"),
    MINIMAL("Minimal", "Clean charcoal layout with zero chrome"),
    IMAGE_FOCUSED("Image-Focused", "Highlights the Moon disc with subtle typography")
}

data class NotificationPreferences(
    val moonriseAlerts: Boolean = false,
    val fullMoonAlerts: Boolean = true,
    val newMoonAlerts: Boolean = false,
    val lunarEclipseAlerts: Boolean = true,
    val majorEventsAlerts: Boolean = true,
    val observationOpportunities: Boolean = false,
    val dailyMoonUpdate: Boolean = false
)

data class AppSettings(
    val location: UserLocation = UserLocation.DEFAULT,
    val useAutomaticLocation: Boolean = false,
    val themeMode: LunarThemeMode = LunarThemeMode.OBSIDIAN,
    val fontStyle: LunarFontStyle = LunarFontStyle.ROBOTO,
    val distanceUnit: DistanceUnit = DistanceUnit.KILOMETERS,
    val use24HourTime: Boolean = false,
    val introEnabled: Boolean = true,
    val hasSeenIntro: Boolean = false,
    val introStyle: IntroMoonStyle = IntroMoonStyle.AUTO_ROTATE,
    val introVariantIndex: Int = 0,
    val reducedMotion: Boolean = false,
    val notifications: NotificationPreferences = NotificationPreferences(),
    val showCalendarIllumination: Boolean = true,
    val showCalendarEclipses: Boolean = true,
    val startWeekOnMonday: Boolean = false,
    val showHorizonCompass: Boolean = true,
    val showAtmosphericRefractionNote: Boolean = true,
    val wallpaperQuality: WallpaperQuality = WallpaperQuality.ORIGINAL,
    val wifiOnlyDownloads: Boolean = false,
    val widgetTheme: WidgetTheme = WidgetTheme.BLACK,
    val favoriteWallpaperIds: Set<String> = emptySet(),
    val favoriteArticleIds: Set<String> = emptySet(),
    val favoriteFeatureIds: Set<String> = emptySet(),
    val favoriteEventIds: Set<String> = emptySet(),
    val downloadedWallpaperIds: Set<String> = emptySet()
)

fun formatInstantTime(
    millis: Long?,
    use24Hour: Boolean,
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    if (millis == null) return "—"
    val dt = Instant.ofEpochMilli(millis).atZone(zoneId)
    return if (use24Hour) {
        String.format(Locale.US, "%02d:%02d", dt.hour, dt.minute)
    } else {
        val hour12 = when {
            dt.hour == 0 -> 12
            dt.hour > 12 -> dt.hour - 12
            else -> dt.hour
        }
        val amPm = if (dt.hour < 12) "AM" else "PM"
        String.format(Locale.US, "%d:%02d %s", hour12, dt.minute, amPm)
    }
}

fun formatDistance(distanceKm: Double, unit: DistanceUnit): String {
    val locale = Locale.getDefault()
    return when (unit) {
        DistanceUnit.KILOMETERS -> String.format(locale, "%,.0f km", distanceKm)
        DistanceUnit.MILES -> String.format(locale, "%,.0f mi", distanceKm * 0.621371)
    }
}

fun formatTemperature(celsius: Double, unit: DistanceUnit): String {
    val locale = Locale.getDefault()
    return when (unit) {
        DistanceUnit.KILOMETERS -> String.format(locale, "%.0f°C", celsius)
        DistanceUnit.MILES -> String.format(locale, "%.0f°F", celsius * 9.0 / 5.0 + 32.0)
    }
}
