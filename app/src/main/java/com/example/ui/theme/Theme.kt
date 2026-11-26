package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.astronomy.LunarFontStyle
import com.example.astronomy.LunarThemeMode
import com.example.localization.LocalLunarStrings
import com.example.localization.LunarLanguage
import com.example.localization.LunarLocaleManager
import com.example.localization.LunarStringCatalog

private val ObsidianColorScheme = darkColorScheme(
    primary = CelestialBlue,
    onPrimary = ObsidianBlack,
    primaryContainer = CharcoalElevated,
    onPrimaryContainer = StarlightWhite,
    secondary = MutedViolet,
    onSecondary = ObsidianBlack,
    secondaryContainer = ColorTokens.VioletSurface,
    onSecondaryContainer = SoftSilver,
    tertiary = EclipseCopper,
    onTertiary = PureBlack,
    background = ObsidianBlack,
    onBackground = StarlightWhite,
    surface = DeepSpaceSurface,
    onSurface = StarlightWhite,
    surfaceVariant = CharcoalElevated,
    onSurfaceVariant = MutedSlate,
    outline = SubtleBorder,
    outlineVariant = ColorTokens.HairlineObsidian
)

private val MidnightColorScheme = darkColorScheme(
    primary = CelestialBlue,
    onPrimary = MidnightBg,
    primaryContainer = MidnightElevated,
    onPrimaryContainer = StarlightWhite,
    secondary = MutedViolet,
    onSecondary = MidnightBg,
    secondaryContainer = ColorTokens.MidnightVioletSurface,
    onSecondaryContainer = SoftSilver,
    tertiary = EclipseCopper,
    onTertiary = PureBlack,
    background = MidnightBg,
    onBackground = StarlightWhite,
    surface = MidnightSurface,
    onSurface = StarlightWhite,
    surfaceVariant = MidnightElevated,
    onSurfaceVariant = SubtleSilver,
    outline = MidnightBorder,
    outlineVariant = ColorTokens.HairlineMidnight
)

private val EclipseColorScheme = darkColorScheme(
    primary = MutedViolet,
    onPrimary = EclipseBg,
    primaryContainer = EclipseElevated,
    onPrimaryContainer = StarlightWhite,
    secondary = CelestialBlue,
    onSecondary = EclipseBg,
    secondaryContainer = ColorTokens.EclipseSecondarySurface,
    onSecondaryContainer = SoftSilver,
    tertiary = EclipseCopper,
    onTertiary = PureBlack,
    background = EclipseBg,
    onBackground = StarlightWhite,
    surface = EclipseSurface,
    onSurface = StarlightWhite,
    surfaceVariant = EclipseElevated,
    onSurfaceVariant = MutedSlate,
    outline = EclipseBorder,
    outlineVariant = ColorTokens.HairlineEclipse
)

private val DaylightColorScheme = lightColorScheme(
    primary = DaylightAccent,
    onPrimary = DaylightCard,
    primaryContainer = ColorTokens.DaylightContainer,
    onPrimaryContainer = DaylightInk,
    secondary = ColorTokens.DaylightViolet,
    onSecondary = DaylightCard,
    tertiary = EclipseCopper,
    onTertiary = DaylightCard,
    background = DaylightSurface,
    onBackground = DaylightInk,
    surface = DaylightCard,
    onSurface = DaylightInk,
    surfaceVariant = ColorTokens.DaylightVariant,
    onSurfaceVariant = DaylightMuted,
    outline = ColorTokens.DaylightOutline,
    outlineVariant = ColorTokens.DaylightHairline
)

private object ColorTokens {
    val VioletSurface = androidx.compose.ui.graphics.Color(0xFF1A1830)
    val MidnightVioletSurface = androidx.compose.ui.graphics.Color(0xFF1C2347)
    val EclipseSecondarySurface = androidx.compose.ui.graphics.Color(0xFF212238)
    val HairlineObsidian = androidx.compose.ui.graphics.Color(0xFF171E2E)
    val HairlineMidnight = androidx.compose.ui.graphics.Color(0xFF1E2D4F)
    val HairlineEclipse = androidx.compose.ui.graphics.Color(0xFF242436)
    val DaylightContainer = androidx.compose.ui.graphics.Color(0xFFDCE5FA)
    val DaylightViolet = androidx.compose.ui.graphics.Color(0xFF5C4DB1)
    val DaylightVariant = androidx.compose.ui.graphics.Color(0xFFE7ECF6)
    val DaylightOutline = androidx.compose.ui.graphics.Color(0xFFC5CFE2)
    val DaylightHairline = androidx.compose.ui.graphics.Color(0xFFDCE2EF)
}

val LunarShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun LunarTheme(
    themeMode: LunarThemeMode = LunarThemeMode.OBSIDIAN,
    fontStyle: LunarFontStyle = LunarFontStyle.ROBOTO,
    activeLanguage: LunarLanguage = LunarLocaleManager.findLanguage(LunarLocaleManager.DEFAULT_LANGUAGE_CODE),
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val colorScheme = when (themeMode) {
        LunarThemeMode.OBSIDIAN -> ObsidianColorScheme
        LunarThemeMode.MIDNIGHT -> MidnightColorScheme
        LunarThemeMode.ECLIPSE -> EclipseColorScheme
        LunarThemeMode.SYSTEM -> if (systemDark) ObsidianColorScheme else DaylightColorScheme
    }

    val typography = remember(fontStyle, activeLanguage.usesNonLatinScript) {
        lunarTypographyFor(fontStyle, activeLanguage.usesNonLatinScript)
    }
    val strings = remember(activeLanguage.code) {
        LunarStringCatalog.forCode(activeLanguage.code)
    }
    val layoutDirection = if (activeLanguage.isRtl) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalLunarStrings provides strings,
        LocalLayoutDirection provides layoutDirection
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = LunarShapes,
            content = content
        )
    }
}
