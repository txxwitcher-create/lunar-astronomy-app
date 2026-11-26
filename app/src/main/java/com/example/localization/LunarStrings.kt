package com.example.localization

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.astronomy.LunarPhaseType

data class LunarStrings(
    // Brand & Navigation
    val tagline: String,
    val navHome: String,
    val navExplore: String,
    val navCalendar: String,
    val navTonight: String,
    val navSettings: String,

    // Common actions & labels
    val back: String,
    val cancel: String,
    val done: String,
    val close: String,
    val save: String,
    val share: String,
    val search: String,
    val refresh: String,
    val reset: String,
    val skip: String,
    val all: String,
    val today: String,
    val allow: String,
    val clear: String,
    val favorites: String,
    val noResults: String,

    // Moon Phases (all 8)
    val phaseNewMoon: String,
    val phaseWaxingCrescent: String,
    val phaseFirstQuarter: String,
    val phaseWaxingGibbous: String,
    val phaseFullMoon: String,
    val phaseWaningGibbous: String,
    val phaseLastQuarter: String,
    val phaseWaningCrescent: String,

    // Lunar telemetry
    val illuminated: String,
    val lunarAge: String,
    val waxing: String,
    val waning: String,
    val moonrise: String,
    val moonset: String,
    val distance: String,
    val altitudeNow: String,
    val aboveHorizon: String,
    val belowHorizon: String,
    val nextPhase: String,
    val daysUnit: String,

    // Home sections
    val homePhaseGuide: String,
    val homeNextEvent: String,
    val homeTonightViewing: String,
    val homeExploreMoon: String,
    val homeDidYouKnow: String,
    val homeAnotherFact: String,
    val homeFeaturedDiscovery: String,
    val homePhotography: String,
    val homeWallpapers: String,
    val homeAllEvents: String,
    val homeFullGuide: String,
    val homeOpenAtlas: String,
    val homeLearnLibrary: String,

    // Explore & Moon Atlas & Astronomy Categories
    val exploreTitle: String,
    val exploreSubtitle: String,
    val moonAtlas: String,
    val craters: String,
    val lunarMaria: String,
    val mountainsValleys: String,
    val lunarPoles: String,
    val rillesChannels: String,
    val landingSites: String,
    val lunarGeology: String,
    val planets: String,
    val constellations: String,
    val deepSpace: String,
    val meteorShowers: String,
    val eclipses: String,
    val spaceMissions: String,
    val milkyWay: String,
    val astronomyKnowledge: String,
    val labelsOn: String,
    val labelsOff: String,
    val rotate: String,

    // Calendar & Tonight
    val lunarCalendar: String,
    val monthHighlights: String,
    val tonightQuestion: String,
    val calculatedAstronomy: String,
    val weatherConditions: String,
    val twilightWindow: String,
    val celestialObjects: String,
    val visibleNow: String,
    val selectCity: String,
    val searchCityHint: String,

    // Wallpapers & Learn & Photography
    val wallpapersTitle: String,
    val setWallpaper: String,
    val homeAndLock: String,
    val homeOnly: String,
    val lockOnly: String,
    val searchWallpapers: String,
    val learnTitle: String,
    val photoGuideTitle: String,
    val smartphone: String,
    val cameraDslr: String,
    val savedFavorites: String,
    val noFavoritesYet: String,

    // Settings categories & items
    val settingsTitle: String,
    val catPersonalization: String,
    val catAstronomy: String,
    val catVisualExperience: String,
    val catApp: String,
    val catAbout: String,
    val catCreator: String,
    val languageLabel: String,
    val languageSearchHint: String,
    val appearanceLabel: String,
    val fontLabel: String,
    val locationLabel: String,
    val automaticGps: String,
    val unitsTimeLabel: String,
    val notificationsLabel: String,
    val calendarPrefsLabel: String,
    val observationPrefsLabel: String,
    val widgetsLabel: String,
    val introAnimationLabel: String,
    val replayIntroLabel: String,
    val introStyleLabel: String,
    val wallpaperQualityLabel: String,
    val reducedMotionLabel: String,
    val dataStorageLabel: String,
    val clearCacheTitle: String,
    val clearCacheConfirm: String,
    val privacyLabel: String,
    val feedbackLabel: String,
    val aboutLunarLabel: String,
    val createdBy: String,

    // Mandatory Language Change Restart Confirmation Dialog
    val restartDialogTitle: String,
    val restartDialogMessage: String,
    val restartDialogConfirm: String,

    // Intro artistic notice
    val introArtisticBadge: String
) {
    fun phaseName(type: LunarPhaseType): String = when (type) {
        LunarPhaseType.NEW_MOON -> phaseNewMoon
        LunarPhaseType.WAXING_CRESCENT -> phaseWaxingCrescent
        LunarPhaseType.FIRST_QUARTER -> phaseFirstQuarter
        LunarPhaseType.WAXING_GIBBOUS -> phaseWaxingGibbous
        LunarPhaseType.FULL_MOON -> phaseFullMoon
        LunarPhaseType.WANING_GIBBOUS -> phaseWaningGibbous
        LunarPhaseType.LAST_QUARTER -> phaseLastQuarter
        LunarPhaseType.WANING_CRESCENT -> phaseWaningCrescent
    }
}

val LocalLunarStrings = staticCompositionLocalOf<LunarStrings> {
    LunarTranslationsPart1.ENGLISH
}

object LunarStringCatalog {

    private val allTranslations: Map<String, LunarStrings> by lazy {
        LunarTranslationsPart1.translations + LunarTranslationsPart2.translations
    }

    fun forCode(code: String?): LunarStrings {
        val base = LunarTranslationsPart1.ENGLISH
        val target = allTranslations[code] ?: return base
        return target.withEnglishFallback(base)
    }

    fun hasTranslationFor(code: String): Boolean = allTranslations.containsKey(code)

    private fun LunarStrings.withEnglishFallback(en: LunarStrings): LunarStrings {
        fun String.fb(fallback: String) = if (isBlank()) fallback else this
        return copy(
            tagline = tagline.fb(en.tagline),
            navHome = navHome.fb(en.navHome),
            navExplore = navExplore.fb(en.navExplore),
            navCalendar = navCalendar.fb(en.navCalendar),
            navTonight = navTonight.fb(en.navTonight),
            navSettings = navSettings.fb(en.navSettings),
            back = back.fb(en.back),
            cancel = cancel.fb(en.cancel),
            done = done.fb(en.done),
            close = close.fb(en.close),
            save = save.fb(en.save),
            share = share.fb(en.share),
            search = search.fb(en.search),
            refresh = refresh.fb(en.refresh),
            reset = reset.fb(en.reset),
            skip = skip.fb(en.skip),
            all = all.fb(en.all),
            today = today.fb(en.today),
            allow = allow.fb(en.allow),
            clear = clear.fb(en.clear),
            favorites = favorites.fb(en.favorites),
            noResults = noResults.fb(en.noResults),
            restartDialogTitle = restartDialogTitle.fb(en.restartDialogTitle),
            restartDialogMessage = restartDialogMessage.fb(en.restartDialogMessage),
            restartDialogConfirm = restartDialogConfirm.fb(en.restartDialogConfirm)
        )
    }
}
