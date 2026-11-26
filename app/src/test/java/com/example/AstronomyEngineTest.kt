package com.example

import com.example.astronomy.AstronomyEngine
import com.example.astronomy.LunarPhaseType
import com.example.astronomy.UserLocation
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset
import java.time.ZonedDateTime

class AstronomyEngineTest {

    @Test
    fun fullMoonMarch2026_isClassifiedAsFullMoonWithHighIllumination() {
        // Total Lunar Eclipse / Full Moon on March 3, 2026 at 11:33 UTC
        val ts = ZonedDateTime.of(2026, 3, 3, 11, 33, 0, 0, ZoneOffset.UTC).toInstant().toEpochMilli()
        val snap = AstronomyEngine.getLunarSnapshot(ts, UserLocation.DEFAULT, ZoneOffset.UTC)

        assertEquals(LunarPhaseType.FULL_MOON, snap.phaseType)
        assertTrue("Expected illumination > 98%, got ${snap.illuminationPercent}", snap.illuminationPercent > 98.0)
        assertTrue("Distance should be within orbital bounds", snap.distanceKm in 350_000.0..410_000.0)
    }

    @Test
    fun nextFullMoonAfterNewMoon_isRoughlyHalfSynodicMonthAhead() {
        val start = ZonedDateTime.of(2026, 5, 1, 12, 0, 0, 0, ZoneOffset.UTC).toInstant().toEpochMilli()
        val nextNew = AstronomyEngine.findNextPhaseInstant(start, 0.0)
        val nextFull = AstronomyEngine.findNextPhaseInstant(nextNew + 60_000L, 180.0)
        val diffDays = (nextFull - nextNew) / 86_400_000.0

        assertTrue("Expected ~14.76 days between New and Full Moon, got $diffDays", diffDays in 13.5..16.0)
    }

    @Test
    fun calendarMonth_buildsAllDaysAndMarksMajorPhases() {
        val days = AstronomyEngine.buildCalendarMonth(YearMonth.of(2026, 3), ZoneOffset.UTC)
        assertEquals(31, days.size)
        assertTrue("March 2026 should contain the March 3 Total Lunar Eclipse", days.any { it.date == LocalDate.of(2026, 3, 3) && it.hasEclipse })
        assertTrue("Month should flag at least 3 major phase days", days.count { it.isMajorPhaseDay } >= 3)
    }

    @Test
    fun twilightAndTonightSkyObjects_returnValidStructuredData() {
        val ts = ZonedDateTime.of(2026, 11, 23, 22, 0, 0, 0, ZoneOffset.UTC).toInstant().toEpochMilli()
        val twilight = AstronomyEngine.calculateTwilight(LocalDate.of(2026, 11, 23), UserLocation.DEFAULT, ts, true, ZoneOffset.UTC)
        val skyObjects = AstronomyEngine.getTonightSkyObjects(ts, UserLocation.DEFAULT, ZoneOffset.UTC)

        assertNotNull(twilight.sunsetMillis)
        assertNotNull(twilight.astronomicalDuskMillis)
        assertTrue("Should calculate planets, stars, constellations", skyObjects.size >= 8)
    }
}
