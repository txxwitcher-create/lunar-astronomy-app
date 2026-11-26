package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.astronomy.LunarFontStyle

val RobotoFontFamily = FontFamily(
    Font(R.font.roboto, FontWeight.Normal),
    Font(R.font.roboto, FontWeight.Medium),
    Font(R.font.roboto, FontWeight.SemiBold),
    Font(R.font.roboto, FontWeight.Bold)
)

val SpaceGroteskFontFamily = FontFamily(
    Font(R.font.space_grotesk, FontWeight.Normal),
    Font(R.font.space_grotesk, FontWeight.Medium),
    Font(R.font.space_grotesk, FontWeight.SemiBold),
    Font(R.font.space_grotesk, FontWeight.Bold)
)

val IbmPlexMonoFontFamily = FontFamily(
    Font(R.font.ibm_plex_mono, FontWeight.Normal),
    Font(R.font.ibm_plex_mono, FontWeight.Medium),
    Font(R.font.ibm_plex_mono, FontWeight.SemiBold),
    Font(R.font.ibm_plex_mono, FontWeight.Bold)
)

// Backwards-compatible aliases used across existing screens
val EditorialSerif = SpaceGroteskFontFamily
val LunarSans = RobotoFontFamily
val TelemetryMono = IbmPlexMonoFontFamily

/**
 * Builds Material 3 Typography honoring the user's selected font (Roboto, Space Grotesk, or IBM Plex Mono)
 * while automatically preserving system script fallback for non-Latin languages (Hindi, Arabic, CJK, etc.)
 * so no glyphs are ever missing or clipped.
 */
fun lunarTypographyFor(
    fontStyle: LunarFontStyle,
    usesNonLatinScript: Boolean = false
): Typography {
    val headingFamily = when {
        usesNonLatinScript -> FontFamily.SansSerif
        fontStyle == LunarFontStyle.IBM_PLEX_MONO -> IbmPlexMonoFontFamily
        else -> SpaceGroteskFontFamily
    }
    val bodyFamily = when {
        usesNonLatinScript -> FontFamily.SansSerif
        fontStyle == LunarFontStyle.SPACE_GROTESK -> SpaceGroteskFontFamily
        fontStyle == LunarFontStyle.IBM_PLEX_MONO -> IbmPlexMonoFontFamily
        else -> RobotoFontFamily
    }
    val monoFamily = if (usesNonLatinScript) FontFamily.SansSerif else IbmPlexMonoFontFamily

    return Typography(
        displayLarge = TextStyle(
            fontFamily = headingFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 40.sp,
            lineHeight = 46.sp,
            letterSpacing = (-0.5).sp
        ),
        displayMedium = TextStyle(
            fontFamily = headingFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            lineHeight = 38.sp,
            letterSpacing = (-0.3).sp
        ),
        displaySmall = TextStyle(
            fontFamily = headingFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 26.sp,
            lineHeight = 32.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = headingFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 30.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = headingFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = (-0.2).sp
        ),
        headlineSmall = TextStyle(
            fontFamily = headingFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp
        ),
        titleLarge = TextStyle(
            fontFamily = headingFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            lineHeight = 23.sp
        ),
        titleMedium = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 21.sp
        ),
        titleSmall = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.5.sp,
            lineHeight = 19.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 23.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 13.5.sp,
            lineHeight = 20.sp
        ),
        bodySmall = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
        labelLarge = TextStyle(
            fontFamily = bodyFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            lineHeight = 17.sp,
            letterSpacing = 0.3.sp
        ),
        labelMedium = TextStyle(
            fontFamily = monoFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.5.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = monoFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.6.sp
        )
    )
}

val LunarTypography = lunarTypographyFor(LunarFontStyle.ROBOTO)
