package com.example.astronomy

import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Scientifically accurate lunar & solar ephemeris engine based on Jean Meeus
 * "Astronomical Algorithms" (2nd Ed.) and USNO low-precision celestial mechanics.
 * Works 100% offline with local timezone conversion.
 */
object AstronomyEngine {

    const val SYNODIC_MONTH_DAYS = 29.530588853
    private const val DEG2RAD = PI / 180.0
    private const val RAD2DEG = 180.0 / PI
    private const val SUPERMOON_THRESHOLD_KM = 362_000.0

    fun toJulianDate(epochMillis: Long): Double {
        return epochMillis / 86_400_000.0 + 2440587.5
    }

    fun fromJulianDate(jd: Double): Long {
        return ((jd - 2440587.5) * 86_400_000.0).toLong()
    }

    private fun normalizeDeg(angle: Double): Double {
        var a = angle % 360.0
        if (a < 0.0) a += 360.0
        return a
    }

    private data class EquatorialCoords(
        val raDeg: Double,
        val decDeg: Double,
        val distanceKm: Double,
        val eclipticLonDeg: Double
    )

    private data class HorizontalCoords(
        val altitudeDeg: Double,
        val azimuthDeg: Double
    )

    /**
     * Geocentric Sun position (Meeus Ch. 25)
     */
    private fun sunPosition(jd: Double): EquatorialCoords {
        val t = (jd - 2451545.0) / 36525.0
        val l0 = normalizeDeg(280.46646 + 36000.76983 * t + 0.0003032 * t * t)
        val m = normalizeDeg(357.52911 + 35999.05029 * t - 0.0001537 * t * t)
        val mRad = m * DEG2RAD

        val c = (1.914602 - 0.004817 * t - 0.000014 * t * t) * sin(mRad) +
            (0.019993 - 0.000101 * t) * sin(2.0 * mRad) +
            0.000289 * sin(3.0 * mRad)

        val sunLon = normalizeDeg(l0 + c)
        val omega = normalizeDeg(125.04 - 1934.136 * t)
        val lambda = normalizeDeg(sunLon - 0.00569 - 0.00478 * sin(omega * DEG2RAD))
        val eps0 = 23.439291 - 0.0130042 * t
        val eps = eps0 + 0.00256 * cos(omega * DEG2RAD)

        val lambdaRad = lambda * DEG2RAD
        val epsRad = eps * DEG2RAD

        val ra = normalizeDeg(atan2(cos(epsRad) * sin(lambdaRad), cos(lambdaRad)) * RAD2DEG)
        val dec = asin(sin(epsRad) * sin(lambdaRad)) * RAD2DEG
        return EquatorialCoords(ra, dec, 149_597_870.7, lambda)
    }

    /**
     * Geocentric Moon position & Earth-Moon distance (Meeus Ch. 47 truncated series, ~0.05° accuracy)
     */
    private fun moonPosition(jd: Double): EquatorialCoords {
        val t = (jd - 2451545.0) / 36525.0

        val lPrime = normalizeDeg(218.3164477 + 481267.88123421 * t - 0.0015786 * t * t)
        val d = normalizeDeg(297.8501921 + 445267.1114034 * t - 0.0018819 * t * t)
        val m = normalizeDeg(357.5291092 + 35999.0502909 * t - 0.0001536 * t * t)
        val mPrime = normalizeDeg(134.9633964 + 477198.8675055 * t + 0.0087414 * t * t)
        val f = normalizeDeg(93.2720950 + 483202.0175233 * t - 0.0036539 * t * t)

        val dR = d * DEG2RAD
        val mR = m * DEG2RAD
        val mpR = mPrime * DEG2RAD
        val fR = f * DEG2RAD

        // Longitude perturbations (degrees)
        val sigmaL = 6.288774 * sin(mpR) +
            1.274027 * sin(2 * dR - mpR) +
            0.658314 * sin(2 * dR) +
            0.213618 * sin(2 * mpR) -
            0.185116 * sin(mR) -
            0.114332 * sin(2 * fR) +
            0.058793 * sin(2 * dR - 2 * mpR) +
            0.057066 * sin(2 * dR - mR - mpR) +
            0.053322 * sin(2 * dR + mpR) +
            0.045758 * sin(2 * dR - mR) -
            0.040923 * sin(mR - mpR) -
            0.034720 * sin(dR) -
            0.030383 * sin(mR + mpR) +
            0.015327 * sin(2 * dR - 2 * fR) -
            0.012528 * sin(mpR + 2 * fR) +
            0.010980 * sin(mpR - 2 * fR)

        // Latitude perturbations (degrees)
        val sigmaB = 5.128122 * sin(fR) +
            0.280602 * sin(mpR + fR) +
            0.277693 * sin(mpR - fR) +
            0.173237 * sin(2 * dR - fR) +
            0.055413 * sin(2 * dR - mpR + fR) +
            0.046271 * sin(2 * dR - mpR - fR) +
            0.032573 * sin(2 * dR + fR) +
            0.017198 * sin(2 * mpR + fR)

        // Distance perturbations (km)
        val sigmaR = -20905.355 * cos(mpR) -
            3699.111 * cos(2 * dR - mpR) -
            2955.968 * cos(2 * dR) -
            569.925 * cos(2 * mpR) +
            48.888 * cos(mR) -
            3.149 * cos(2 * fR) +
            246.158 * cos(2 * dR - 2 * mpR) -
            152.138 * cos(2 * dR - mR - mpR) -
            170.733 * cos(2 * dR + mpR) -
            204.586 * cos(2 * dR - mR) -
            129.620 * cos(mR - mpR) +
            108.743 * cos(dR) +
            104.755 * cos(mR + mpR) +
            79.661 * cos(mpR - 2 * fR)

        val lambda = normalizeDeg(lPrime + sigmaL)
        val beta = sigmaB
        val distanceKm = 385000.56 + sigmaR

        val eps = (23.439291 - 0.0130042 * t) * DEG2RAD
        val lamR = lambda * DEG2RAD
        val betR = beta * DEG2RAD

        val sinDec = sin(betR) * cos(eps) + cos(betR) * sin(eps) * sin(lamR)
        val dec = asin(sinDec.coerceIn(-1.0, 1.0)) * RAD2DEG

        val y = sin(lamR) * cos(eps) - kotlin.math.tan(betR) * sin(eps)
        val x = cos(lamR)
        val ra = normalizeDeg(atan2(y, x) * RAD2DEG)

        return EquatorialCoords(ra, dec, distanceKm, lambda)
    }

    private fun greenwichSiderealTimeDeg(jd: Double): Double {
        val t = (jd - 2451545.0) / 36525.0
        val gmst = 280.46061837 + 360.98564736629 * (jd - 2451545.0) +
            0.000387933 * t * t - (t * t * t) / 38710000.0
        return normalizeDeg(gmst)
    }

    private fun equatorialToHorizontal(
        raDeg: Double,
        decDeg: Double,
        jd: Double,
        latDeg: Double,
        lonDeg: Double
    ): HorizontalCoords {
        val lst = normalizeDeg(greenwichSiderealTimeDeg(jd) + lonDeg)
        val haRad = normalizeDeg(lst - raDeg) * DEG2RAD
        val decRad = decDeg * DEG2RAD
        val latRad = latDeg * DEG2RAD

        val sinAlt = sin(decRad) * sin(latRad) + cos(decRad) * cos(latRad) * cos(haRad)
        val alt = asin(sinAlt.coerceIn(-1.0, 1.0)) * RAD2DEG

        val y = -sin(haRad)
        val x = kotlin.math.tan(decRad) * cos(latRad) - sin(latRad) * cos(haRad)
        val az = normalizeDeg(atan2(y, x) * RAD2DEG)

        return HorizontalCoords(alt, az)
    }

    fun azimuthToCompass(azimuthDeg: Double): String {
        val dirs = listOf("N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW")
        val index = ((normalizeDeg(azimuthDeg) + 11.25) / 22.5).toInt() % 16
        return dirs[index]
    }

    private fun eclipticToZodiac(lonDeg: Double): String {
        val signs = listOf(
            "Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo",
            "Libra", "Scorpius", "Sagittarius", "Capricornus", "Aquarius", "Pisces"
        )
        val idx = (normalizeDeg(lonDeg) / 30.0).toInt().coerceIn(0, 11)
        return signs[idx]
    }

    /**
     * Exact Moon-Sun ecliptic elongation angle in [0, 360)
     * 0° = New Moon, 90° = First Quarter, 180° = Full Moon, 270° = Last Quarter
     */
    fun lunarElongationDeg(epochMillis: Long): Double {
        val jd = toJulianDate(epochMillis)
        val sun = sunPosition(jd)
        val moon = moonPosition(jd)
        return normalizeDeg(moon.eclipticLonDeg - sun.eclipticLonDeg)
    }

    fun classifyPhase(phaseFraction: Double): LunarPhaseType {
        val p = ((phaseFraction % 1.0) + 1.0) % 1.0
        return when {
            p < 0.028 || p >= 0.972 -> LunarPhaseType.NEW_MOON
            p < 0.222 -> LunarPhaseType.WAXING_CRESCENT
            p < 0.278 -> LunarPhaseType.FIRST_QUARTER
            p < 0.472 -> LunarPhaseType.WAXING_GIBBOUS
            p < 0.528 -> LunarPhaseType.FULL_MOON
            p < 0.722 -> LunarPhaseType.WANING_GIBBOUS
            p < 0.778 -> LunarPhaseType.LAST_QUARTER
            else -> LunarPhaseType.WANING_CRESCENT
        }
    }

    fun getLunarSnapshot(
        epochMillis: Long,
        location: UserLocation,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): LunarSnapshot {
        val jd = toJulianDate(epochMillis)
        val sun = sunPosition(jd)
        val moon = moonPosition(jd)
        val elongation = normalizeDeg(moon.eclipticLonDeg - sun.eclipticLonDeg)
        val phaseFraction = elongation / 360.0
        val illumination = ((1.0 - cos(elongation * DEG2RAD)) / 2.0) * 100.0
        val ageDays = phaseFraction * SYNODIC_MONTH_DAYS
        val isWaxing = phaseFraction < 0.5
        val phaseType = classifyPhase(phaseFraction)

        // Moon apparent angular diameter in arcminutes (radius = 1737.4 km)
        val angularDiameterArcmin = 2.0 * asin((1737.4 / moon.distanceKm).coerceIn(-1.0, 1.0)) * RAD2DEG * 60.0

        val horiz = equatorialToHorizontal(moon.raDeg, moon.decDeg, jd, location.latitude, location.longitude)
        val localDate = Instant.ofEpochMilli(epochMillis).atZone(zoneId).toLocalDate()
        val (rise, set) = calculateMoonriseMoonset(localDate, location, zoneId)

        return LunarSnapshot(
            timestampMillis = epochMillis,
            localDate = localDate,
            phaseType = phaseType,
            phaseFraction = phaseFraction,
            illuminationPercent = illumination.coerceIn(0.0, 100.0),
            lunarAgeDays = ageDays,
            isWaxing = isWaxing,
            distanceKm = moon.distanceKm,
            angularDiameterArcmin = angularDiameterArcmin,
            altitudeDeg = horiz.altitudeDeg,
            azimuthDeg = horiz.azimuthDeg,
            compassDirection = azimuthToCompass(horiz.azimuthDeg),
            isAboveHorizon = horiz.altitudeDeg > -0.5,
            moonriseMillis = rise,
            moonsetMillis = set,
            eclipticLongitudeDeg = moon.eclipticLonDeg,
            zodiacSign = eclipticToZodiac(moon.eclipticLonDeg),
            isSupermoonCandidate = phaseType == LunarPhaseType.FULL_MOON && moon.distanceKm <= SUPERMOON_THRESHOLD_KM
        )
    }

    /**
     * Computes Moonrise and Moonset timestamps for a given local date and location
     * by scanning altitude across 24 hours and bisecting threshold crossings (+0.125° effective geometric horizon).
     */
    fun calculateMoonriseMoonset(
        date: LocalDate,
        location: UserLocation,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): Pair<Long?, Long?> {
        val startOfDay = date.atStartOfDay(zoneId).toInstant().toEpochMilli()
        val threshold = 0.125 // accounts for Moon horizontal parallax (~0.95°) minus refraction (0.57°) & semi-diameter (0.26°)
        val stepMillis = 15L * 60L * 1000L // 15-minute intervals
        val steps = 96

        var moonrise: Long? = null
        var moonset: Long? = null

        var prevTime = startOfDay
        var prevAlt = moonAltitudeAt(prevTime, location) - threshold

        for (i in 1..steps) {
            val currTime = startOfDay + i * stepMillis
            val currAlt = moonAltitudeAt(currTime, location) - threshold

            if (prevAlt < 0.0 && currAlt >= 0.0 && moonrise == null) {
                moonrise = bisectHorizonCrossing(prevTime, currTime, threshold, location, isMoon = true, rising = true)
            } else if (prevAlt >= 0.0 && currAlt < 0.0 && moonset == null) {
                moonset = bisectHorizonCrossing(prevTime, currTime, threshold, location, isMoon = true, rising = false)
            }
            prevTime = currTime
            prevAlt = currAlt
        }
        return moonrise to moonset
    }

    private fun moonAltitudeAt(epochMillis: Long, location: UserLocation): Double {
        val jd = toJulianDate(epochMillis)
        val moon = moonPosition(jd)
        return equatorialToHorizontal(moon.raDeg, moon.decDeg, jd, location.latitude, location.longitude).altitudeDeg
    }

    private fun sunAltitudeAt(epochMillis: Long, location: UserLocation): Double {
        val jd = toJulianDate(epochMillis)
        val sun = sunPosition(jd)
        return equatorialToHorizontal(sun.raDeg, sun.decDeg, jd, location.latitude, location.longitude).altitudeDeg
    }

    private fun bisectHorizonCrossing(
        t1: Long,
        t2: Long,
        thresholdDeg: Double,
        location: UserLocation,
        isMoon: Boolean,
        rising: Boolean
    ): Long {
        var lo = t1
        var hi = t2
        repeat(10) {
            val mid = (lo + hi) / 2
            val alt = (if (isMoon) moonAltitudeAt(mid, location) else sunAltitudeAt(mid, location)) - thresholdDeg
            if (rising) {
                if (alt < 0.0) lo = mid else hi = mid
            } else {
                if (alt >= 0.0) lo = mid else hi = mid
            }
        }
        return (lo + hi) / 2
    }

    /**
     * Computes Sunrise, Sunset, Civil (-6°), Nautical (-12°), and Astronomical (-18°) twilight times
     */
    fun calculateTwilight(
        date: LocalDate,
        location: UserLocation,
        nowMillis: Long,
        use24Hour: Boolean,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): TwilightInfo {
        val startOfDay = date.atStartOfDay(zoneId).toInstant().toEpochMilli()
        val sunrise = findSunCrossing(startOfDay, location, -0.833, rising = true)
        val sunset = findSunCrossing(startOfDay, location, -0.833, rising = false)
        val civilDawn = findSunCrossing(startOfDay, location, -6.0, rising = true)
        val civilDusk = findSunCrossing(startOfDay, location, -6.0, rising = false)
        val nauticalDawn = findSunCrossing(startOfDay, location, -12.0, rising = true)
        val nauticalDusk = findSunCrossing(startOfDay, location, -12.0, rising = false)
        val astroDawn = findSunCrossing(startOfDay, location, -18.0, rising = true)
        val astroDusk = findSunCrossing(startOfDay, location, -18.0, rising = false)

        val currentSunAlt = sunAltitudeAt(nowMillis, location)
        val stage = when {
            currentSunAlt > -0.833 -> "Daylight"
            currentSunAlt > -6.0 -> "Civil Twilight"
            currentSunAlt > -12.0 -> "Nautical Twilight"
            currentSunAlt > -18.0 -> "Astronomical Twilight"
            else -> "Astronomical Darkness"
        }

        val bestWindow = when {
            astroDusk != null && astroDawn != null -> {
                "${formatInstantTime(astroDusk, use24Hour, zoneId)} – ${formatInstantTime(astroDawn, use24Hour, zoneId)}"
            }
            nauticalDusk != null -> "After ${formatInstantTime(nauticalDusk, use24Hour, zoneId)}"
            sunset != null -> "After ${formatInstantTime(sunset, use24Hour, zoneId)}"
            else -> "High-latitude twilight regime"
        }

        return TwilightInfo(
            sunriseMillis = sunrise,
            sunsetMillis = sunset,
            civilDuskMillis = civilDusk,
            nauticalDuskMillis = nauticalDusk,
            astronomicalDuskMillis = astroDusk,
            astronomicalDawnMillis = astroDawn,
            nauticalDawnMillis = nauticalDawn,
            civilDawnMillis = civilDawn,
            currentSkyStage = stage,
            bestObservationWindow = bestWindow
        )
    }

    private fun findSunCrossing(
        startOfDayMillis: Long,
        location: UserLocation,
        thresholdDeg: Double,
        rising: Boolean
    ): Long? {
        val stepMillis = 15L * 60L * 1000L
        var prevTime = startOfDayMillis
        var prevAlt = sunAltitudeAt(prevTime, location) - thresholdDeg
        for (i in 1..96) {
            val currTime = startOfDayMillis + i * stepMillis
            val currAlt = sunAltitudeAt(currTime, location) - thresholdDeg
            if (rising && prevAlt < 0.0 && currAlt >= 0.0) {
                return bisectHorizonCrossing(prevTime, currTime, thresholdDeg, location, isMoon = false, rising = true)
            }
            if (!rising && prevAlt >= 0.0 && currAlt < 0.0) {
                return bisectHorizonCrossing(prevTime, currTime, thresholdDeg, location, isMoon = false, rising = false)
            }
            prevTime = currTime
            prevAlt = currAlt
        }
        return null
    }

    /**
     * Finds the exact timestamp of the next occurrence of a target Moon-Sun elongation (0, 90, 180, 270 deg)
     */
    fun findNextPhaseInstant(fromMillis: Long, targetElongationDeg: Double): Long {
        val currentElong = lunarElongationDeg(fromMillis)
        var deltaDeg = normalizeDeg(targetElongationDeg - currentElong)
        if (deltaDeg < 1.0) deltaDeg += 360.0 // ensure strictly next occurrence
        var estimate = fromMillis + ((deltaDeg / 360.0) * SYNODIC_MONTH_DAYS * 86_400_000.0).toLong()

        // Newton-like iterative refinement to within ~1 minute
        repeat(6) {
            val actual = lunarElongationDeg(estimate)
            var diff = targetElongationDeg - actual
            while (diff > 180.0) diff -= 360.0
            while (diff < -180.0) diff += 360.0
            // Average synodic rate is ~12.19075 deg/day
            val correctionMillis = ((diff / 12.19075) * 86_400_000.0).toLong()
            estimate += correctionMillis
        }
        return estimate
    }

    fun findPreviousPhaseInstant(fromMillis: Long, targetElongationDeg: Double): Long {
        val currentElong = lunarElongationDeg(fromMillis)
        var deltaDeg = normalizeDeg(currentElong - targetElongationDeg)
        if (deltaDeg < 1.0) deltaDeg += 360.0
        var estimate = fromMillis - ((deltaDeg / 360.0) * SYNODIC_MONTH_DAYS * 86_400_000.0).toLong()
        repeat(6) {
            val actual = lunarElongationDeg(estimate)
            var diff = targetElongationDeg - actual
            while (diff > 180.0) diff -= 360.0
            while (diff < -180.0) diff += 360.0
            val correctionMillis = ((diff / 12.19075) * 86_400_000.0).toLong()
            estimate += correctionMillis
        }
        return estimate
    }

    fun findNextMajorPhase(fromMillis: Long): Pair<LunarPhaseType, Long> {
        val candidates = listOf(
            LunarPhaseType.NEW_MOON to findNextPhaseInstant(fromMillis, 0.0),
            LunarPhaseType.FIRST_QUARTER to findNextPhaseInstant(fromMillis, 90.0),
            LunarPhaseType.FULL_MOON to findNextPhaseInstant(fromMillis, 180.0),
            LunarPhaseType.LAST_QUARTER to findNextPhaseInstant(fromMillis, 270.0)
        )
        return candidates.minBy { it.second }
    }

    fun findPreviousMajorPhase(fromMillis: Long): Pair<LunarPhaseType, Long> {
        val candidates = listOf(
            LunarPhaseType.NEW_MOON to findPreviousPhaseInstant(fromMillis, 0.0),
            LunarPhaseType.FIRST_QUARTER to findPreviousPhaseInstant(fromMillis, 90.0),
            LunarPhaseType.FULL_MOON to findPreviousPhaseInstant(fromMillis, 180.0),
            LunarPhaseType.LAST_QUARTER to findPreviousPhaseInstant(fromMillis, 270.0)
        )
        return candidates.maxBy { it.second }
    }

    /**
     * Generates a full month of lunar calendar data with accurate phase, illumination,
     * exact major-phase day detection, and real eclipse matching.
     */
    fun buildCalendarMonth(
        yearMonth: YearMonth,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): List<CalendarDayData> {
        val monthStart = yearMonth.atDay(1).atStartOfDay(zoneId).toInstant().toEpochMilli()
        val monthEnd = yearMonth.atEndOfMonth().plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli()

        // Find exact major phase days within this month
        val majorPhaseMap = mutableMapOf<LocalDate, Pair<LunarPhaseType, String>>()
        listOf(
            0.0 to (LunarPhaseType.NEW_MOON to "New Moon"),
            90.0 to (LunarPhaseType.FIRST_QUARTER to "First Quarter"),
            180.0 to (LunarPhaseType.FULL_MOON to "Full Moon"),
            270.0 to (LunarPhaseType.LAST_QUARTER to "Last Quarter")
        ).forEach { (deg, pair) ->
            var t = findNextPhaseInstant(monthStart - 86_400_000L, deg)
            while (t < monthEnd) {
                if (t >= monthStart) {
                    val d = Instant.ofEpochMilli(t).atZone(zoneId).toLocalDate()
                    majorPhaseMap[d] = pair
                }
                t = findNextPhaseInstant(t + 5L * 86_400_000L, deg)
            }
        }

        val eclipseDates = getKnownLunarEclipses().associateBy {
            Instant.ofEpochMilli(it.timestampMillis).atZone(zoneId).toLocalDate()
        }

        return (1..yearMonth.lengthOfMonth()).map { day ->
            val date = yearMonth.atDay(day)
            val noonMillis = date.atTime(12, 0).atZone(zoneId).toInstant().toEpochMilli()
            val elong = lunarElongationDeg(noonMillis)
            val fraction = elong / 360.0
            val illum = ((1.0 - cos(elong * DEG2RAD)) / 2.0) * 100.0
            val major = majorPhaseMap[date]
            val eclipse = eclipseDates[date]

            CalendarDayData(
                date = date,
                phaseType = major?.first ?: classifyPhase(fraction),
                phaseFraction = fraction,
                illuminationPercent = illum.coerceIn(0.0, 100.0),
                lunarAgeDays = fraction * SYNODIC_MONTH_DAYS,
                isMajorPhaseDay = major != null,
                majorPhaseLabel = major?.second,
                hasEclipse = eclipse != null,
                hasEvent = eclipse != null || major != null,
                eventTitle = eclipse?.title ?: major?.second
            )
        }
    }

    /**
     * Generates upcoming & recent major lunar events (Full Moons, Supermoons, New Moons, and verified NASA Lunar Eclipses)
     */
    fun getUpcomingEvents(fromMillis: Long, zoneId: ZoneId = ZoneId.systemDefault()): List<LunarEvent> {
        val events = mutableListOf<LunarEvent>()

        // Next 6 Full Moons (flagging Supermoons accurately from perigee distance)
        var tFull = findNextPhaseInstant(fromMillis, 180.0)
        repeat(6) { idx ->
            val jd = toJulianDate(tFull)
            val moon = moonPosition(jd)
            val isSuper = moon.distanceKm <= SUPERMOON_THRESHOLD_KM
            val localDate = Instant.ofEpochMilli(tFull).atZone(zoneId).toLocalDate()
            val traditionalName = traditionalFullMoonName(localDate.monthValue)
            val title = if (isSuper) "Super $traditionalName" else traditionalName
            events.add(
                LunarEvent(
                    id = "full_${localDate}_$idx",
                    title = title,
                    subtitle = if (isSuper) "Perigee Full Moon • Closer & brighter than average" else "Full Moon • 100% illuminated disc",
                    type = if (isSuper) LunarEventType.SUPERMOON else LunarEventType.FULL_MOON,
                    timestampMillis = tFull,
                    visibilitySummary = "Visible worldwide after local sunset until dawn, weather permitting.",
                    scientificExplanation = if (isSuper) {
                        "A Supermoon occurs when Full Moon coincides with lunar perigee—the Moon's closest orbital approach to Earth (${String.format("%,.0f", moon.distanceKm)} km). Because the Moon follows an elliptical orbit, it can appear up to 14% wider and 30% brighter than at apogee."
                    } else {
                        "At Full Moon, the Moon sits 180° opposite the Sun in ecliptic longitude, illuminating the entire Earth-facing hemisphere. Earth sits nearly between the Sun and Moon, while the Moon's 5.1° orbital inclination prevents a lunar eclipse on most months."
                    },
                    observationGuide = "Watch near local moonrise as the Moon climbs over the eastern horizon. The famous Moon Illusion makes it appear dramatically larger against trees, hills, or city skylines. Later in the night, high glare flattens crater shadows—use sunglasses or a lunar filter with telescopes.",
                    photographyTips = "Use the Looney 11 rule (f/11, ISO 100, 1/100s) to preserve delicate lunar maria rather than blowing out the disc to pure white. Shoot during twilight so the foreground landscape stays softly lit.",
                    imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
                    fallbackVisualType = VisualFallbackType.FULL_MOON,
                    distanceKm = moon.distanceKm
                )
            )
            tFull = findNextPhaseInstant(tFull + 10L * 86_400_000L, 180.0)
        }

        // Next 4 New Moons (prime dark-sky nights)
        var tNew = findNextPhaseInstant(fromMillis, 0.0)
        repeat(4) { idx ->
            val localDate = Instant.ofEpochMilli(tNew).atZone(zoneId).toLocalDate()
            events.add(
                LunarEvent(
                    id = "new_${localDate}_$idx",
                    title = "New Moon — Dark Sky Peak",
                    subtitle = "0% illumination • Optimal window for Milky Way & deep sky",
                    type = LunarEventType.NEW_MOON,
                    timestampMillis = tNew,
                    visibilitySummary = "Moon is near the Sun and not visible; provides the darkest skies of the lunar cycle worldwide.",
                    scientificExplanation = "During New Moon, the Moon lies in conjunction with the Sun (0° elongation). Sunlight strikes the far side while the Earth-facing hemisphere remains in shadow. Without moonlight scattering across Earth's atmosphere, faint galaxies and nebulae reach maximum contrast.",
                    observationGuide = "Travel away from city light domes during the three nights surrounding New Moon. Allow 25 minutes for your eyes to dark-adapt before searching for the Milky Way band, Andromeda Galaxy (M31), or faint meteor streaks.",
                    photographyTips = "Ideal night for wide-field Milky Way long exposures: f/1.8–f/2.8, ISO 1600–3200, 15–20 seconds on a sturdy tripod using the 500 rule to prevent star trailing.",
                    imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
                    fallbackVisualType = VisualFallbackType.DEEP_SPACE
                )
            )
            tNew = findNextPhaseInstant(tNew + 10L * 86_400_000L, 0.0)
        }

        // Add verified NASA Lunar Eclipses (upcoming or within past 14 days for context)
        val cutoff = fromMillis - 14L * 86_400_000L
        events.addAll(getKnownLunarEclipses().filter { it.timestampMillis >= cutoff })

        // Add upcoming major annual meteor showers
        events.addAll(getUpcomingMeteorShowerEvents(fromMillis, zoneId))

        return events.sortedBy { it.timestampMillis }
    }

    private fun traditionalFullMoonName(month: Int): String = when (month) {
        1 -> "Wolf Moon"
        2 -> "Snow Moon"
        3 -> "Worm Moon"
        4 -> "Pink Moon"
        5 -> "Flower Moon"
        6 -> "Strawberry Moon"
        7 -> "Buck Moon"
        8 -> "Sturgeon Moon"
        9 -> "Harvest Moon"
        10 -> "Hunter's Moon"
        11 -> "Beaver Moon"
        else -> "Cold Moon"
    }

    /**
     * Verified NASA Goddard Five Millennium Canon of Lunar Eclipses (UTC converted dynamically to user timezone)
     */
    fun getKnownLunarEclipses(): List<LunarEvent> {
        fun utc(y: Int, m: Int, d: Int, h: Int, min: Int): Long =
            ZonedDateTime.of(y, m, d, h, min, 0, 0, ZoneOffset.UTC).toInstant().toEpochMilli()

        val redMoonExplanation = "During a total lunar eclipse, Earth moves directly between the Sun and the Moon, casting its two-part shadow—the faint outer penumbra and the dark inner umbra—across the lunar surface. Even during totality, the Moon does not vanish completely: Earth's atmosphere bends (refracts) long-wavelength red and orange sunlight into the umbra while scattering shorter blue wavelengths away—the same Rayleigh scattering physics that paints sunrises and sunsets crimson."

        return listOf(
            LunarEvent(
                id = "eclipse_2026_03_03",
                title = "Total Lunar Eclipse — Blood Moon",
                subtitle = "Deep umbral totality • Copper-crimson Moon",
                type = LunarEventType.TOTAL_LUNAR_ECLIPSE,
                timestampMillis = utc(2026, 3, 3, 11, 33),
                visibilitySummary = "Best viewed from Eastern Asia, Australia, the Pacific, and Western North America.",
                scientificExplanation = redMoonExplanation,
                observationGuide = "No special eye protection is needed for lunar eclipses. Watch the subtle penumbral dimming first, then the curved dark bite of Earth's umbra creeping across Tycho and the lunar maria before glowing warm copper during totality.",
                photographyTips = "During partial phases, expose for the bright sunlit limb. Once totality begins, the Moon dims by ~10,000×: raise ISO to 800–1600, open aperture wide (f/2.8–f/5.6), and expose 0.5–2 seconds on a tripod.",
                imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
                fallbackVisualType = VisualFallbackType.BLOOD_MOON,
                eclipseStages = EclipseStages(
                    penumbralStartMillis = utc(2026, 3, 3, 8, 44),
                    partialStartMillis = utc(2026, 3, 3, 9, 50),
                    totalityStartMillis = utc(2026, 3, 3, 11, 4),
                    maximumEclipseMillis = utc(2026, 3, 3, 11, 33),
                    totalityEndMillis = utc(2026, 3, 3, 12, 2),
                    partialEndMillis = utc(2026, 3, 3, 13, 17),
                    penumbralEndMillis = utc(2026, 3, 3, 14, 23),
                    umbralMagnitude = 1.15,
                    visibilityRegion = "East Asia, Australia, Pacific, Americas"
                )
            ),
            LunarEvent(
                id = "eclipse_2026_08_28",
                title = "Deep Partial Lunar Eclipse",
                subtitle = "93% umbral immersion • Dramatic shadowed limb",
                type = LunarEventType.PARTIAL_LUNAR_ECLIPSE,
                timestampMillis = utc(2026, 8, 28, 4, 12),
                visibilitySummary = "Visible across the Americas, Europe, and Africa.",
                scientificExplanation = "Although the Moon stops just short of full umbral immersion (93% umbral magnitude), almost the entire lunar disc slips inside Earth's umbra. Expect a stark contrast between a razor-thin silver crescent and a deep rust-tinted shadowed hemisphere.",
                observationGuide = "Use 7×50 or 10×50 binoculars near maximum eclipse to notice subtle turquoise ozone-band coloration along the boundary between Earth's umbra and sunlit regolith.",
                photographyTips = "Bracket exposures (HDR sequence) near maximum eclipse so you can capture both the bright silver sliver and the dim copper shadowed disc.",
                imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
                fallbackVisualType = VisualFallbackType.BLOOD_MOON,
                eclipseStages = EclipseStages(
                    penumbralStartMillis = utc(2026, 8, 28, 1, 23),
                    partialStartMillis = utc(2026, 8, 28, 2, 33),
                    totalityStartMillis = null,
                    maximumEclipseMillis = utc(2026, 8, 28, 4, 12),
                    totalityEndMillis = null,
                    partialEndMillis = utc(2026, 8, 28, 5, 51),
                    penumbralEndMillis = utc(2026, 8, 28, 7, 1),
                    umbralMagnitude = 0.93,
                    visibilityRegion = "Americas, Europe, Africa"
                )
            ),
            LunarEvent(
                id = "eclipse_2027_02_20",
                title = "Penumbral Lunar Eclipse",
                subtitle = "Subtle dusky shading across northern lunar limb",
                type = LunarEventType.PENUMBRAL_LUNAR_ECLIPSE,
                timestampMillis = utc(2027, 2, 20, 23, 12),
                visibilitySummary = "Visible from Europe, Africa, Asia, and Eastern Americas.",
                scientificExplanation = "The Moon glides exclusively through Earth's outer penumbral shadow where Earth partially blocks the Sun's disc. Unlike umbral eclipses, there is no sharp shadow edge—instead a delicate tea-colored darkening gently dims one side of the Full Moon.",
                observationGuide = "Observe within 30 minutes of maximum eclipse when the penumbral gradient is easiest to detect by eye.",
                photographyTips = "Take two identical manual-exposure frames 90 minutes apart (before eclipse and at maximum) to reveal the subtle brightness drop side by side.",
                imageUrl = "https://images-assets.nasa.gov/image/PIA00405/PIA00405~medium.jpg",
                fallbackVisualType = VisualFallbackType.FULL_MOON,
                eclipseStages = EclipseStages(
                    penumbralStartMillis = utc(2027, 2, 20, 21, 12),
                    partialStartMillis = null,
                    totalityStartMillis = null,
                    maximumEclipseMillis = utc(2027, 2, 20, 23, 12),
                    totalityEndMillis = null,
                    partialEndMillis = null,
                    penumbralEndMillis = utc(2027, 2, 21, 1, 13),
                    umbralMagnitude = -0.05,
                    visibilityRegion = "Europe, Africa, Asia, Eastern Americas"
                )
            ),
            LunarEvent(
                id = "eclipse_2028_01_12",
                title = "Partial Lunar Eclipse",
                subtitle = "Grazing umbral shadow across lunar limb",
                type = LunarEventType.PARTIAL_LUNAR_ECLIPSE,
                timestampMillis = utc(2028, 1, 12, 4, 13),
                visibilitySummary = "Visible from the Americas, Europe, and Western Africa.",
                scientificExplanation = "Earth's inner umbra nicks the Moon's edge while the rest of the disc bathes in the penumbra, producing a clean dark indentation against the bright Full Moon.",
                observationGuide = "Look closely near maximum eclipse for the crisp curved silhouette of Earth's shadow along the Moon's southern edge.",
                photographyTips = "Use a 300mm+ lens or telescope prime focus at 1/250s, ISO 200 to freeze atmospheric turbulence.",
                imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
                fallbackVisualType = VisualFallbackType.BLOOD_MOON,
                eclipseStages = EclipseStages(
                    penumbralStartMillis = utc(2028, 1, 12, 2, 9),
                    partialStartMillis = utc(2028, 1, 12, 3, 41),
                    totalityStartMillis = null,
                    maximumEclipseMillis = utc(2028, 1, 12, 4, 13),
                    totalityEndMillis = null,
                    partialEndMillis = utc(2028, 1, 12, 4, 45),
                    penumbralEndMillis = utc(2028, 1, 12, 6, 16),
                    umbralMagnitude = 0.07,
                    visibilityRegion = "Americas, Europe, West Africa"
                )
            ),
            LunarEvent(
                id = "eclipse_2028_12_31",
                title = "New Year's Eve Total Lunar Eclipse",
                subtitle = "71 minutes of Blood Moon totality",
                type = LunarEventType.TOTAL_LUNAR_ECLIPSE,
                timestampMillis = utc(2028, 12, 31, 16, 52),
                visibilitySummary = "Visible across Europe, Africa, Asia, Australia, and the Pacific.",
                scientificExplanation = redMoonExplanation,
                observationGuide = "Totality lasts over an hour—ideal for relaxed naked-eye viewing and binoculars as surrounding winter constellations brighten when lunar glare fades.",
                photographyTips = "Capture a multi-frame composite sequence spaced every 15 minutes to show the full orbital arc through Earth's shadow.",
                imageUrl = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001982/GSFC_20171208_Archive_e001982~medium.jpg",
                fallbackVisualType = VisualFallbackType.BLOOD_MOON,
                eclipseStages = EclipseStages(
                    penumbralStartMillis = utc(2028, 12, 31, 14, 4),
                    partialStartMillis = utc(2028, 12, 31, 15, 8),
                    totalityStartMillis = utc(2028, 12, 31, 16, 16),
                    maximumEclipseMillis = utc(2028, 12, 31, 16, 52),
                    totalityEndMillis = utc(2028, 12, 31, 17, 28),
                    partialEndMillis = utc(2028, 12, 31, 18, 36),
                    penumbralEndMillis = utc(2028, 12, 31, 19, 40),
                    umbralMagnitude = 1.25,
                    visibilityRegion = "Europe, Africa, Asia, Australia"
                )
            )
        )
    }

    private fun getUpcomingMeteorShowerEvents(fromMillis: Long, zoneId: ZoneId): List<LunarEvent> {
        val nowDate = Instant.ofEpochMilli(fromMillis).atZone(zoneId).toLocalDate()
        data class ShowerDef(val id: String, val name: String, val month: Int, val day: Int, val zhr: Int, val parent: String, val radiant: String)
        val defs = listOf(
            ShowerDef("quadrantids", "Quadrantid Meteor Shower Peak", 1, 4, 80, "Asteroid 2003 EH1", "Boötes"),
            ShowerDef("lyrids", "Lyrid Meteor Shower Peak", 4, 22, 18, "Comet C/1861 G1 Thatcher", "Lyra"),
            ShowerDef("perseids", "Perseid Meteor Shower Peak", 8, 12, 100, "Comet 109P/Swift-Tuttle", "Perseus"),
            ShowerDef("orionids", "Orionid Meteor Shower Peak", 10, 21, 20, "Comet 1P/Halley", "Orion"),
            ShowerDef("leonids", "Leonid Meteor Shower Peak", 11, 17, 15, "Comet 55P/Tempel-Tuttle", "Leo"),
            ShowerDef("geminids", "Geminid Meteor Shower Peak", 12, 14, 120, "Asteroid 3200 Phaethon", "Gemini")
        )
        return defs.mapNotNull { s ->
            var date = LocalDate.of(nowDate.year, s.month, s.day)
            if (date.isBefore(nowDate.minusDays(2))) date = date.plusYears(1)
            val ts = date.atTime(2, 0).atZone(zoneId).toInstant().toEpochMilli()
            if (ts - fromMillis > 190L * 86_400_000L) return@mapNotNull null
            LunarEvent(
                id = "shower_${s.id}_${date.year}",
                title = s.name,
                subtitle = "Up to ${s.zhr} meteors/hour under dark skies • Radiant in ${s.radiant}",
                type = LunarEventType.METEOR_SHOWER,
                timestampMillis = ts,
                visibilitySummary = "Best viewed after midnight until local dawn from dark-sky locations.",
                scientificExplanation = "Every year Earth crosses the dusty orbital debris stream shed by ${s.parent}. Sand-sized particles vaporize at ~100 km altitude in Earth's upper atmosphere, creating luminous trails radiating from the constellation ${s.radiant}.",
                observationGuide = "No telescope or binoculars needed—use your unaided eyes for the widest field of view. Recline comfortably looking 45° away from ${s.radiant} so meteor trails appear longer.",
                photographyTips = "Mount an ultra-wide lens (14–24mm) at f/2.0–f/2.8, ISO 3200, and shoot continuous 15-second exposures with an intervalometer for 1–2 hours.",
                imageUrl = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg",
                fallbackVisualType = VisualFallbackType.NIGHT_SKY
            )
        }
    }

    /**
     * Computes real observable celestial objects for Tonight's Sky based on actual
     * Local Sidereal Time, date, and observer latitude/longitude.
     */
    fun getTonightSkyObjects(
        epochMillis: Long,
        location: UserLocation,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): List<SkyObject> {
        val jd = toJulianDate(epochMillis)
        val localDate = Instant.ofEpochMilli(epochMillis).atZone(zoneId).toLocalDate()
        val objects = mutableListOf<SkyObject>()

        // 1. Bright Naked-Eye Planets (computed via simplified mean orbital elements -> geocentric RA/Dec)
        val planets = computeVisiblePlanets(jd, location)
        objects.addAll(planets)

        // 2. Fixed Deep-Sky, Bright Stars & Constellations computed accurately via real equatorial -> horizontal coordinates
        data class FixedTarget(
            val id: String,
            val name: String,
            val type: SkyObjectType,
            val subtitle: String,
            val raDeg: Double,
            val decDeg: Double,
            val mag: String,
            val what: String,
            val how: String,
            val tips: String
        )

        val fixedTargets = listOf(
            FixedTarget(
                id = "orion_constellation",
                name = "Orion & The Great Nebula (M42)",
                type = SkyObjectType.CONSTELLATION,
                subtitle = "Iconic hourglass constellation & stellar nursery",
                raDeg = 83.82,
                decDeg = -5.39,
                mag = "Mag 0.1–4.0",
                what = "One of the most recognizable constellations in both hemispheres, containing supergiants Betelgeuse and Rigel plus the Orion Nebula (M42), a massive star-forming gas cloud 1,344 light-years away.",
                how = "Locate the three evenly spaced blue-white stars of Orion's Belt. Drop straight below the center belt star to find the hazy glow of the Orion Nebula inside Orion's Sword.",
                tips = "Even simple 7×50 binoculars resolve the glowing wispy cloud of M42; a small telescope reveals the four newborn Trapezium stars at its core."
            ),
            FixedTarget(
                id = "sirius_star",
                name = "Sirius (Alpha Canis Majoris)",
                type = SkyObjectType.BRIGHT_STAR,
                subtitle = "Brightest star in Earth's night sky",
                raDeg = 101.28,
                decDeg = -16.71,
                mag = "Mag -1.46",
                what = "A blazing blue-white binary star system just 8.6 light-years from Earth—twice as massive as our Sun and 25 times as luminous.",
                how = "Follow the line of Orion's three belt stars downward and to the left (south-east in northern skies) straight to Sirius's unmistakable diamond twinkle.",
                tips = "When low near the horizon, atmospheric turbulence splits Sirius's light into flashing sparks of blue, white, and red."
            ),
            FixedTarget(
                id = "pleiades_m45",
                name = "The Pleiades (Seven Sisters, M45)",
                type = SkyObjectType.DEEP_SKY,
                subtitle = "Sparkling open star cluster in Taurus",
                raDeg = 56.75,
                decDeg = 24.11,
                mag = "Mag 1.6",
                what = "A young open cluster of hot blue B-type stars born ~100 million years ago, gliding through a faint wispy reflection nebula 444 light-years away.",
                how = "Follow Orion's Belt upward past bright orange Aldebaran in Taurus to spot a tiny shimmering dipper-shaped knot of stars.",
                tips = "Binoculars deliver the finest view of the Pleiades, framing dozens of sapphire stars in one field—even better than a high-power telescope."
            ),
            FixedTarget(
                id = "andromeda_m31",
                name = "Andromeda Galaxy (M31)",
                type = SkyObjectType.DEEP_SKY,
                subtitle = "Nearest major spiral galaxy • 2.5M light-years",
                raDeg = 10.68,
                decDeg = 41.27,
                mag = "Mag 3.4",
                what = "A trillion-star spiral galaxy larger than the Milky Way and the most distant object routinely visible to the unaided human eye under dark skies.",
                how = "Find the W-shape of Cassiopeia; its sharper V points directly toward Andromeda, or hop two stars northeast from the Great Square of Pegasus.",
                tips = "Use averted vision (look slightly to the side of the smudge) and wait for a Moon-free night to see its soft elongated core."
            ),
            FixedTarget(
                id = "vega_lyra",
                name = "Vega & Lyra",
                type = SkyObjectType.BRIGHT_STAR,
                subtitle = "Sapphire anchor of the Summer Triangle",
                raDeg = 279.23,
                decDeg = 38.78,
                mag = "Mag 0.03",
                what = "A rapidly spinning blue-white star only 25 light-years away that served as Earth's northern pole star 14,000 years ago and will again in 13,727 CE.",
                how = "Look high overhead in summer and autumn evenings for its crisp blue-white brilliance flanked by a compact parallelogram of four fainter stars.",
                tips = "Near Vega lies Epsilon Lyrae, the famous 'Double Double'—binoculars split it into two stars, and a telescope splits each again."
            ),
            FixedTarget(
                id = "cassiopeia",
                name = "Cassiopeia",
                type = SkyObjectType.CONSTELLATION,
                subtitle = "Circumpolar W-shaped northern guidepost",
                raDeg = 14.17,
                decDeg = 60.71,
                mag = "Mag 2.2–3.4",
                what = "A prominent Milky Way constellation packed with rich open star clusters like the Owl Cluster (NGC 457) and M52.",
                how = "Look toward the northern sky opposite the Big Dipper across Polaris for five bright stars forming a distinct 'W' or 'M'.",
                tips = "Sweep slowly across the W with binoculars to uncover dozens of tiny glittering Milky Way star clusters."
            )
        )

        fixedTargets.forEach { target ->
            val h = equatorialToHorizontal(target.raDeg, target.decDeg, jd, location.latitude, location.longitude)
            val compass = azimuthToCompass(h.azimuthDeg)
            val above = h.altitudeDeg > 8.0
            val visibility = when {
                h.altitudeDeg >= 45.0 -> "High & Prime (${h.altitudeDeg.roundToInt()}° alt)"
                h.altitudeDeg >= 20.0 -> "Well Placed (${h.altitudeDeg.roundToInt()}° alt)"
                h.altitudeDeg > 8.0 -> "Low on Horizon (${h.altitudeDeg.roundToInt()}° alt)"
                h.altitudeDeg > 0.0 -> "Very Low (${h.altitudeDeg.roundToInt()}° alt)"
                else -> "Below Horizon Now"
            }
            objects.add(
                SkyObject(
                    id = target.id,
                    name = target.name,
                    type = target.type,
                    subtitle = target.subtitle,
                    altitudeDeg = h.altitudeDeg,
                    azimuthDeg = h.azimuthDeg,
                    compassDirection = compass,
                    isCurrentlyVisible = above,
                    visibilityLabel = visibility,
                    whatItIs = target.what,
                    whenToObserve = if (above) "Observable now while skies are dark." else "Rises later tonight or in its prime season.",
                    whereToLook = if (h.altitudeDeg > 0.0) {
                        "Look ${compass} at ${h.altitudeDeg.roundToInt()}° altitude above the horizon."
                    } else {
                        "Currently below your local horizon (${h.altitudeDeg.roundToInt()}°)."
                    },
                    howToFind = target.how,
                    beginnerTips = target.tips,
                    magnitudeLabel = target.mag
                )
            )
        }

        // 3. Active Meteor Shower check for current date
        val activeShower = activeMeteorShowerForDate(localDate)
        if (activeShower != null) {
            objects.add(0, activeShower)
        }

        return objects.sortedWith(
            compareByDescending<SkyObject> { it.isCurrentlyVisible }
                .thenByDescending { it.altitudeDeg ?: -90.0 }
        )
    }

    private fun computeVisiblePlanets(jd: Double, location: UserLocation): List<SkyObject> {
        val d = jd - 2451543.5
        val eps = 23.4393 * DEG2RAD

        // Earth heliocentric ecliptic longitude (approx)
        val earthM = normalizeDeg(356.0470 + 0.9856002585 * d)
        val earthLon = normalizeDeg(earthM + 1.9148 * sin(earthM * DEG2RAD) + 0.02 * sin(2 * earthM * DEG2RAD) + 282.9404)
        val earthR = 1.00014 - 0.01671 * cos(earthM * DEG2RAD)
        val xe = earthR * cos(earthLon * DEG2RAD)
        val ye = earthR * sin(earthLon * DEG2RAD)

        data class PlanetElem(
            val id: String, val name: String, val subtitle: String,
            val a: Double, val e: Double, val n: Double, val l0: Double, val wBar: Double,
            val mag: String, val what: String, val how: String, val tips: String
        )

        val defs = listOf(
            PlanetElem(
                "venus", "Venus", "Evening / Morning Star • Brilliant white beacon",
                0.72333, 0.00677, 1.60213034, 181.9798, 131.5637, "Mag -4.0",
                "Earth's shrouded sister planet—thick sulfuric acid clouds reflect ~75% of sunlight, making Venus brighter than every star in the sky.",
                "Never wanders far from the Sun: look low toward the western horizon after sunset or eastern horizon before dawn.",
                "A small telescope reveals Venus's changing crescent and gibbous phases, first observed by Galileo in 1610."
            ),
            PlanetElem(
                "mars", "Mars", "The Red Planet • Rusty ochre glow",
                1.52368, 0.09340, 0.52403295, 355.4330, 336.0602, "Mag -0.2 to +1.4",
                "A cold desert world colored rust-orange by iron oxide dust across its canyons, polar ice caps, and giant shield volcanoes.",
                "Trace the ecliptic plane (the same path the Sun and Moon follow) and look for a steady amber-red point of light that does not twinkle like stars.",
                "Near opposition Mars gleams brightly; a 4-inch+ telescope can reveal its bright polar caps and dark Syrtis Major plains."
            ),
            PlanetElem(
                "jupiter", "Jupiter", "Gas Giant • Steadfast ivory beacon",
                5.20260, 0.04849, 0.08309121, 34.3515, 14.3312, "Mag -2.5",
                "The Solar System's largest planet—more massive than all other planets combined—crowned with swirling ammonia cloud bands.",
                "Scan along the ecliptic for one of the brightest steady cream-white objects in the sky, outshining every star except Sirius.",
                "Standard 10×50 binoculars clearly show Jupiter's four Galilean moons (Io, Europa, Ganymede, Callisto) lined up like tiny pinpricks."
            ),
            PlanetElem(
                "saturn", "Saturn", "Ringed Giant • Pale golden world",
                9.55491, 0.05551, 0.03345965, 50.0774, 93.0572, "Mag +0.6",
                "A golden gas giant encircled by an expansive system of glistening water-ice rings spanning over 280,000 kilometers.",
                "Look along the ecliptic arc for a calm, creamy-gold star-like point with steady light.",
                "Even a modest 60mm backyard telescope at 40× magnification brings Saturn's iconic rings and largest moon Titan into sharp view."
            )
        )

        return defs.map { p ->
            val l = normalizeDeg(p.l0 + p.n * (jd - 2451545.0))
            val m = normalizeDeg(l - p.wBar) * DEG2RAD
            val v = m + 2.0 * p.e * sin(m) + 1.25 * p.e * p.e * sin(2.0 * m)
            val r = p.a * (1.0 - p.e * p.e) / (1.0 + p.e * cos(v))
            val lon = (p.wBar * DEG2RAD) + v
            val xp = r * cos(lon)
            val yp = r * sin(lon)

            val geoX = xp - xe
            val geoY = yp - ye
            val geoLon = atan2(geoY, geoX)
            val ra = normalizeDeg(atan2(sin(geoLon) * cos(eps), cos(geoLon)) * RAD2DEG)
            val dec = asin((sin(eps) * sin(geoLon)).coerceIn(-1.0, 1.0)) * RAD2DEG

            val h = equatorialToHorizontal(ra, dec, jd, location.latitude, location.longitude)
            val compass = azimuthToCompass(h.azimuthDeg)
            val visible = h.altitudeDeg > 6.0
            val visLabel = when {
                h.altitudeDeg >= 35.0 -> "High & Clear (${h.altitudeDeg.roundToInt()}° alt)"
                h.altitudeDeg >= 15.0 -> "Observable (${h.altitudeDeg.roundToInt()}° alt)"
                h.altitudeDeg > 6.0 -> "Low Horizon (${h.altitudeDeg.roundToInt()}° alt)"
                else -> "Below Horizon Now"
            }

            SkyObject(
                id = "planet_${p.id}",
                name = p.name,
                type = SkyObjectType.PLANET,
                subtitle = p.subtitle,
                altitudeDeg = h.altitudeDeg,
                azimuthDeg = h.azimuthDeg,
                compassDirection = compass,
                isCurrentlyVisible = visible,
                visibilityLabel = visLabel,
                whatItIs = p.what,
                whenToObserve = if (visible) "Above horizon now; best observed once twilight fades." else "Currently below your local horizon.",
                whereToLook = if (h.altitudeDeg > 0.0) {
                    "Look $compass at ${h.altitudeDeg.roundToInt()}° above the horizon."
                } else {
                    "Currently below the horizon (${h.altitudeDeg.roundToInt()}°); check again in a few hours."
                },
                howToFind = p.how,
                beginnerTips = p.tips,
                magnitudeLabel = p.mag
            )
        }
    }

    private fun activeMeteorShowerForDate(date: LocalDate): SkyObject? {
        val m = date.monthValue
        val d = date.dayOfMonth
        return when {
            m == 1 && d in 1..6 -> SkyObject(
                "active_quadrantids", "Quadrantids Meteor Shower", SkyObjectType.METEOR_SHOWER,
                "Active now • Sharp January peak", null, null, "NE", true, "Active Tonight",
                "Fast meteors originating from asteroid 2003 EH1 with a brief, intense peak.",
                "Best between 1:00 AM and dawn when the radiant climbs high.",
                "Face generally northeast and scan wide patches of overhead dark sky.",
                "Look away from bright moonlight and let eyes adapt for 20 minutes.",
                "Bring warm layers and watch without optical instruments.", "Up to 80/hr"
            )
            m == 8 && d in 5..18 -> SkyObject(
                "active_perseids", "Perseids Meteor Shower", SkyObjectType.METEOR_SHOWER,
                "Active now • Classic summer fireballs", null, null, "NE", true, "Active Tonight",
                "Debris from Comet 109P/Swift-Tuttle producing swift, bright, persistent meteor trails.",
                "Peak rates arrive between midnight and local dawn.",
                "Look northeast toward Perseus or straight overhead.",
                "Find the darkest horizon possible and recline with a wide sky view.",
                "Unaided eyes outperform telescopes for catching swift streaks.", "Up to 100/hr"
            )
            m == 11 && d in 10..26 -> SkyObject(
                "active_leonids", "Leonids & Northern Taurids", SkyObjectType.METEOR_SHOWER,
                "Active late-autumn meteor window", null, null, "E", true, "Active Tonight",
                "Swift Leonid meteors from Comet Tempel-Tuttle paired with slow, bright Taurid fireballs.",
                "Best observed after local midnight when Leo rises in the east.",
                "Look east-southeast and high overhead away from city glow.",
                "Watch patiently for 30+ minutes; meteors often arrive in brief flurries.",
                "Dress warmly and keep phone brightness dimmed to preserve night vision.", "10–15/hr"
            )
            m == 12 && d in 7..17 -> SkyObject(
                "active_geminids", "Geminids Meteor Shower", SkyObjectType.METEOR_SHOWER,
                "Active now • Year's richest meteor display", null, null, "E", true, "Active Tonight",
                "Dense debris stream from rocky asteroid 3200 Phaethon creating bright, multicolored meteors.",
                "Good even by 9:00 PM local time, peaking around 2:00 AM.",
                "Radiates near Castor & Pollux in Gemini; meteors streak across the entire sky.",
                "Simply lie back on a blanket looking straight up.",
                "No binoculars required—wide unobstructed sky is key.", "Up to 120/hr"
            )
            else -> null
        }
    }

    fun formatCountdown(targetMillis: Long, nowMillis: Long): String {
        val diff = targetMillis - nowMillis
        if (diff <= 0L) return "Happening now"
        val totalMinutes = diff / 60_000L
        val days = totalMinutes / (60 * 24)
        val hours = (totalMinutes % (60 * 24)) / 60
        val minutes = totalMinutes % 60
        return when {
            days > 0 -> "${days}d ${hours}h ${minutes}m"
            hours > 0 -> "${hours}h ${minutes}m"
            else -> "${minutes}m"
        }
    }
}
