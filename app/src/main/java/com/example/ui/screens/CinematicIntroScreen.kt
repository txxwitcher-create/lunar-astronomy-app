package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.IntroMoonStyle
import com.example.localization.LocalLunarStrings
import com.example.ui.components.RealisticMoonDisc
import com.example.widget.MoonTintMode
import kotlin.math.PI
import kotlin.math.sin

/**
 * Cinematic 10-second opening animation showcasing 10 artistic Moon intro variants.
 * 5 choreographed stages:
 *   0–2s: Dark space & subtle starfield atmosphere
 *   2–4s: Moon emerges gracefully from shadow
 *   4–6s: Slow celestial rotation, crater lighting & orbital presence
 *   6–8s: Dynamic visual lighting / eclipse shadow / horizon transition
 *   8–10s: "LUNAR" wordmark & localized tagline reveal -> smooth exit to Home
 *
 * Includes an immediate Skip button and an explicit "Artistic Intro Variant" indicator
 * so users never confuse the cinematic intro with the live astronomical Moon phase.
 */
@Composable
fun CinematicIntroScreen(
    style: IntroMoonStyle,
    reducedMotion: Boolean,
    onFinished: () -> Unit
) {
    val strings = LocalLunarStrings.current
    val resolvedStyle = remember(style) {
        if (style == IntroMoonStyle.AUTO_ROTATE) IntroMoonStyle.FULL_MOON else style
    }

    val timeline = remember { Animatable(0f) }
    var dismissed by remember { mutableStateOf(false) }

    fun finishOnce() {
        if (!dismissed) {
            dismissed = true
            onFinished()
        }
    }

    LaunchedEffect(resolvedStyle, reducedMotion) {
        val totalDurationMs = if (reducedMotion) 3200 else 9600
        timeline.snapTo(0f)
        timeline.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = totalDurationMs, easing = LinearEasing)
        )
        finishOnce()
    }

    val t = timeline.value

    // Stage progress helpers (0f..1f each)
    val starAlpha = (t / 0.20f).coerceIn(0f, 1f)
    val emergeProgress = ((t - 0.16f) / 0.26f).coerceIn(0f, 1f)
    val lightingStage = ((t - 0.40f) / 0.40f).coerceIn(0f, 1f)
    val brandReveal = ((t - 0.76f) / 0.18f).coerceIn(0f, 1f)
    val exitFade = if (t > 0.94f) (1f - (t - 0.94f) / 0.06f).coerceIn(0f, 1f) else 1f

    val moonTint = when (resolvedStyle) {
        IntroMoonStyle.BLOOD_MOON -> MoonTintMode.BLOOD_MOON
        IntroMoonStyle.BLUE_MOON -> MoonTintMode.BLUE_MOON
        IntroMoonStyle.GOLDEN_MOON -> MoonTintMode.GOLDEN_MOON
        else -> MoonTintMode.NORMAL
    }

    val bgGradient = when (resolvedStyle) {
        IntroMoonStyle.BLOOD_MOON -> listOf(Color(0xFF060204), Color(0xFF19070B), Color(0xFF020204))
        IntroMoonStyle.BLUE_MOON -> listOf(Color(0xFF020612), Color(0xFF0B1C3D), Color(0xFF010308))
        IntroMoonStyle.GOLDEN_MOON -> listOf(Color(0xFF0C0703), Color(0xFF211408), Color(0xFF040302))
        IntroMoonStyle.LUNAR_ECLIPSE -> listOf(Color(0xFF040308), Color(0xFF140B16), Color(0xFF010205))
        IntroMoonStyle.MOONRISE -> listOf(Color(0xFF030712), Color(0xFF0D172E), Color(0xFF020408))
        else -> listOf(Color(0xFF000000), Color(0xFF070C18), Color(0xFF000000))
    }

    val baseScale = when (resolvedStyle) {
        IntroMoonStyle.CRATER_MOON -> 1.48f
        IntroMoonStyle.LUNAR_SURFACE -> 1.65f
        else -> 1.0f
    }
    val animatedScale = if (reducedMotion) {
        baseScale
    } else {
        baseScale * (0.82f + 0.24f * emergeProgress + 0.07f * lightingStage)
    }
    val animatedRotation = if (reducedMotion) 0f else when (resolvedStyle) {
        IntroMoonStyle.LUNAR_SURFACE -> -10f + lightingStage * 18f
        IntroMoonStyle.CRATER_MOON -> -6f + lightingStage * 10f
        else -> -4f + lightingStage * 8f
    }
    val animatedTranslationY = if (reducedMotion) {
        0f
    } else if (resolvedStyle == IntroMoonStyle.MOONRISE) {
        (1f - emergeProgress) * 260f - lightingStage * 42f
    } else {
        (1f - emergeProgress) * 48f - brandReveal * 28f
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(bgGradient))
            .alpha(exitFade)
            .testTag("cinematic_intro_screen")
    ) {
        // Stage 1: Subtle deep-space starfield & atmospheric aura
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Restrained radial atmosphere behind the Moon
            val auraColor = when (resolvedStyle) {
                IntroMoonStyle.BLOOD_MOON -> Color(0x28C84A2A)
                IntroMoonStyle.BLUE_MOON -> Color(0x265E9BFF)
                IntroMoonStyle.GOLDEN_MOON -> Color(0x2AE8AD54)
                else -> Color(0x1E8AAEFF)
            }
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(auraColor.copy(alpha = auraColor.alpha * (0.35f + 0.65f * emergeProgress)), Color.Transparent),
                    center = Offset(w * 0.5f, h * 0.44f),
                    radius = w * 0.72f
                ),
                center = Offset(w * 0.5f, h * 0.44f),
                radius = w * 0.72f
            )

            // Crisp realistic pinpoint stars
            for (i in 0 until 72) {
                val sx = ((i * 7919 + 113) % 1000) / 1000f * w
                val sy = ((i * 104729 + 59) % 1000) / 1000f * h * 0.85f
                val twinkle = if (reducedMotion) 1f else (0.65f + 0.35f * sin(t * 2f * PI.toFloat() * 1.6f + i).toFloat())
                val a = (0.18f + (i % 5) * 0.12f) * starAlpha * twinkle
                val r = if (i % 13 == 0) 2.2f else 1.2f
                drawCircle(
                    color = Color(0xFFE6EEFF).copy(alpha = a.coerceIn(0f, 1f)),
                    radius = r,
                    center = Offset(sx, sy)
                )
            }

            // Moonrise dark horizon mountain silhouette
            if (resolvedStyle == IntroMoonStyle.MOONRISE) {
                val ridge = Path().apply {
                    moveTo(0f, h * 0.70f)
                    lineTo(w * 0.22f, h * 0.60f)
                    lineTo(w * 0.48f, h * 0.67f)
                    lineTo(w * 0.73f, h * 0.57f)
                    lineTo(w, h * 0.64f)
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(ridge, Color(0xFF04070E))
            }
        }

        // Stage 2, 3, 4: Emerging photorealistic Moon + variant-specific lighting transition
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-36).dp)
                .size(280.dp)
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    rotationZ = animatedRotation
                    translationY = animatedTranslationY
                    alpha = emergeProgress
                },
            contentAlignment = Alignment.Center
        ) {
            RealisticMoonDisc(
                phaseFraction = resolvedStyle.phaseFraction,
                tintMode = moonTint,
                sizeDp = 280.dp,
                includeOuterHalo = true,
                contentDescription = resolvedStyle.label,
                modifier = Modifier.fillMaxSize()
            )

            // Dynamic Stage 4 lighting/eclipse transition overlay
            if (resolvedStyle == IntroMoonStyle.LUNAR_ECLIPSE || resolvedStyle == IntroMoonStyle.SHADOW_MOON) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension * 0.41f
                    val sweepOffset = if (resolvedStyle == IntroMoonStyle.LUNAR_ECLIPSE) {
                        -radius * 1.6f + lightingStage * radius * 2.1f
                    } else {
                        (1f - emergeProgress) * radius * 1.1f - lightingStage * radius * 1.35f
                    }
                    val umbraColor = if (resolvedStyle == IntroMoonStyle.LUNAR_ECLIPSE) {
                        Color(0xC41A0808)
                    } else {
                        Color(0xD804060B)
                    }
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(umbraColor, umbraColor.copy(alpha = 0.75f), Color.Transparent),
                            center = Offset(size.width / 2f + sweepOffset, size.height / 2f),
                            radius = radius * 1.05f
                        ),
                        center = Offset(size.width / 2f + sweepOffset, size.height / 2f),
                        radius = radius * 1.05f
                    )
                }
            }
        }

        // Top bar: Artistic variant badge + Immediate Skip button (safe below display cutout)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .displayCutoutPadding()
                .padding(horizontal = 20.dp, vertical = 14.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = strings.introArtisticBadge.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF94A3B8),
                    letterSpacing = 1.4.sp
                )
                Text(
                    text = resolvedStyle.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFFE2E8F0)
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0x660B101C))
                    .border(0.75.dp, Color(0x44DCE3F0), RoundedCornerShape(50))
                    .clickable { finishOnce() }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .testTag("intro_skip_button")
            ) {
                Text(
                    text = strings.skip,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFFF4F7FB)
                )
            }
        }

        // Stage 5: Editorial LUNAR brand & localized tagline reveal
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 48.dp)
                .alpha(brandReveal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "LUNAR",
                style = MaterialTheme.typography.displayMedium.copy(
                    letterSpacing = 9.sp,
                    fontWeight = FontWeight.Light
                ),
                color = Color(0xFFF6F8FC),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = strings.tagline,
                style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 2.6.sp),
                color = Color(0xFFB4C3DE),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(22.dp))
            // Minimal progress hairline
            Box(
                modifier = Modifier
                    .width(96.dp)
                    .height(2.dp)
                    .clip(RoundedCornerShape(1.dp))
                    .background(Color(0x33DCE3F0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(t.coerceIn(0f, 1f))
                        .background(Color(0xFF8AAEFF))
                )
            }
        }
    }
}
