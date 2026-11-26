package com.example

import com.example.astronomy.*
import com.example.data.AstronomyEncyclopediaCatalog
import com.example.data.LunarContentCatalog
import com.example.localization.LunarLocaleManager
import com.example.localization.LunarStringCatalog
import org.junit.Assert.*
import org.junit.Test
import java.util.Locale

class ExampleRobolectricTest {

    @Test
    fun allEightMoonPhasesHaveScientificContentAndCyclicNavigation() {
        assertEquals(8, LunarPhaseType.entries.size)
        LunarPhaseType.entries.forEach { phase ->
            val detail = LunarContentCatalog.phaseDetails[phase]
            assertNotNull("Missing phase content for $phase", detail)
            assertTrue(detail!!.explanation.isNotBlank())
            assertTrue(detail.observationTips.isNotEmpty())
            assertEquals(phase, phase.next().previous())
            assertEquals(phase, phase.previous().next())
        }
    }

    @Test
    fun completeMoonAtlasCoversAllSevenCategories() {
        val typesPresent = LunarContentCatalog.lunarFeatures.map { it.type }.toSet()
        LunarFeatureType.entries.forEach { expectedType ->
            assertTrue("Missing Moon Atlas category: $expectedType", expectedType in typesPresent)
        }
        val featureNames = LunarContentCatalog.lunarFeatures.map { it.name }
        assertTrue(featureNames.any { it.contains("Tycho") })
        assertTrue(featureNames.any { it.contains("Copernicus") })
        assertTrue(featureNames.any { it.contains("Aristarchus") })
        assertTrue(featureNames.any { it.contains("Mare Tranquillitatis") })
        assertTrue(featureNames.any { it.contains("Mare Imbrium") })
        assertTrue(featureNames.any { it.contains("Mare Serenitatis") })
        assertTrue(featureNames.any { it.contains("Oceanus Procellarum") })
    }

    @Test
    fun expandedEncyclopediaWallpapersIntroStylesAndFontsAreComplete() {
        assertEquals("All 8 planets must be present", 8, AstronomyEncyclopediaCatalog.planets.size)
        assertTrue("Constellations must be populated", AstronomyEncyclopediaCatalog.constellations.size >= 8)
        assertTrue("Deep Space objects must be populated", AstronomyEncyclopediaCatalog.deepSpaceObjects.size >= 6)
        assertTrue("Space Missions must be populated", AstronomyEncyclopediaCatalog.spaceMissions.size >= 8)

        assertEquals("10 artistic Moon intro variants required", 10, IntroMoonStyle.concreteStyles.size)
        assertEquals("3 curated fonts required", 3, LunarFontStyle.entries.size)

        assertTrue("Learn articles should be populated", LunarContentCatalog.learnArticles.size >= 8)
        assertTrue("Photography guides should cover smartphone & camera", LunarContentCatalog.photoGuides.size >= 4)
        assertTrue("Expanded wallpaper library should contain >= 20 items", LunarContentCatalog.wallpapers.size >= 20)
        assertTrue(LunarContentCatalog.wallpapers.all { it.viewingAngle.isNotBlank() && it.lightingMood.isNotBlank() })
        assertTrue("Wallpaper categories include Blood Moon", WallpaperCategory.BLOOD_MOON in LunarContentCatalog.wallpapers.map { it.category })
        assertTrue("Verified lunar eclipses should be present", AstronomyEngine.getKnownLunarEclipses().size >= 5)
    }

    @Test
    fun allFiftyLanguagesHaveValidMetadataTranslationsAndRtlFlags() {
        val expectedCodes = setOf(
            "en", "zh-CN", "es", "hi", "ar", "bn", "pt", "ru", "ja", "pa",
            "de", "jv", "ko", "fr", "te", "mr", "tr", "ta", "vi", "ur",
            "id", "it", "th", "gu", "fa", "pl", "uk", "ml", "kn", "my",
            "sw", "yo", "ha", "am", "ne", "si", "km", "lo", "fil", "nl",
            "el", "he", "ro", "cs", "hu", "sv", "da", "fi", "nb", "sr"
        )
        val supported = LunarLocaleManager.supportedLanguages
        assertEquals("Exactly 50 languages must be supported", 50, supported.size)
        assertEquals("Language codes must match the exact 50-language specification", expectedCodes, supported.map { it.code }.toSet())

        // English is strictly default
        assertEquals("en", LunarLocaleManager.DEFAULT_LANGUAGE_CODE)
        assertEquals("en", LunarLocaleManager.findLanguage("unknown-xx").code)
        assertEquals("en", LunarLocaleManager.findLanguage(null).code)

        // RTL flags strictly for Arabic, Urdu, Persian, Hebrew
        val rtlCodes = supported.filter { it.isRtl }.map { it.code }.toSet()
        assertEquals(setOf("ar", "ur", "fa", "he"), rtlCodes)

        // Every one of the 50 languages has dedicated translations and non-blank restart confirmation strings
        supported.forEach { lang ->
            assertTrue("Native name blank for ${lang.code}", lang.nativeName.isNotBlank())
            assertTrue("English name blank for ${lang.code}", lang.englishName.isNotBlank())
            assertTrue("Missing translation table for ${lang.code}", LunarStringCatalog.hasTranslationFor(lang.code))

            val strings = LunarStringCatalog.forCode(lang.code)
            assertTrue("navHome blank for ${lang.code}", strings.navHome.isNotBlank())
            assertTrue("settingsTitle blank for ${lang.code}", strings.settingsTitle.isNotBlank())
            assertTrue("restartDialogTitle blank for ${lang.code}", strings.restartDialogTitle.isNotBlank())
            assertTrue("restartDialogMessage blank for ${lang.code}", strings.restartDialogMessage.isNotBlank())
            assertTrue("restartDialogConfirm blank for ${lang.code}", strings.restartDialogConfirm.isNotBlank())
            assertTrue("cancel blank for ${lang.code}", strings.cancel.isNotBlank())
            assertTrue("phaseFullMoon blank for ${lang.code}", strings.phaseFullMoon.isNotBlank())
        }

        // Unknown code safely falls back to English
        val fallback = LunarStringCatalog.forCode("zz-ZZ")
        assertEquals(LunarStringCatalog.forCode("en").settingsTitle, fallback.settingsTitle)
    }

    @Test
    fun astronomyEngineRemainsLocaleIndependentAcrossLocales() {
        val originalLocale = Locale.getDefault()
        try {
            val epochMillis = 1767225600000L // 2026-01-01T00:00:00Z
            Locale.setDefault(Locale.US)
            val snapEn = AstronomyEngine.getLunarSnapshot(epochMillis, UserLocation.DEFAULT)

            listOf("ar", "tr", "de", "ja", "hi").forEach { code ->
                Locale.setDefault(LunarLocaleManager.findLanguage(code).toLocale())
                val snapOther = AstronomyEngine.getLunarSnapshot(epochMillis, UserLocation.DEFAULT)
                assertEquals(snapEn.phaseType, snapOther.phaseType)
                assertEquals(snapEn.illuminationPercent, snapOther.illuminationPercent, 0.0001)
                assertEquals(snapEn.distanceKm, snapOther.distanceKm, 0.0001)
                assertEquals(snapEn.lunarAgeDays, snapOther.lunarAgeDays, 0.0001)
            }
        } finally {
            Locale.setDefault(originalLocale)
        }
    }
}
