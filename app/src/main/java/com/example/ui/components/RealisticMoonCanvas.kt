package com.example.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.astronomy.VisualFallbackType
import com.example.widget.LunarBitmapRenderer
import com.example.widget.MoonTintMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Lightweight in-memory LRU cache for rendered Moon phase bitmaps so calendar grids and
 * scrolling lists render at silky 60-120fps without reallocating bitmaps.
 */
private object MoonBitmapCache {
    private val map = object : LinkedHashMap<String, Bitmap>(48, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Bitmap>?): Boolean = size > 48
    }

    @Synchronized
    fun get(key: String): Bitmap? = map[key]

    @Synchronized
    fun put(key: String, bmp: Bitmap) {
        map[key] = bmp
    }
}

/**
 * Scientifically accurate photorealistic Moon renderer.
 * Displays actual lunar maria, craters, ejecta rays, spherical terminator lighting,
 * earthshine, and optional Blood Moon copper umbra coloration.
 */
@Composable
fun RealisticMoonDisc(
    phaseFraction: Double,
    modifier: Modifier = Modifier,
    sizeDp: Dp = 220.dp,
    isBloodMoon: Boolean = false,
    tintMode: MoonTintMode = if (isBloodMoon) MoonTintMode.BLOOD_MOON else MoonTintMode.NORMAL,
    includeOuterHalo: Boolean = true,
    contentDescription: String = "Realistic Moon phase visualization"
) {
    val effectiveTint = if (isBloodMoon) MoonTintMode.BLOOD_MOON else tintMode
    val density = LocalDensity.current
    val targetPx = remember(sizeDp, density) {
        with(density) { sizeDp.roundToPx() }.coerceIn(64, 720)
    }
    // Quantize phase to 0.005 (~0.15 day) for crisp cache hits
    val quantizedPhase = remember(phaseFraction) {
        (Math.round(phaseFraction * 200.0) / 200.0).coerceIn(0.0, 1.0)
    }
    val cacheKey = remember(targetPx, quantizedPhase, effectiveTint, includeOuterHalo) {
        "${targetPx}_${quantizedPhase}_${effectiveTint.name}_$includeOuterHalo"
    }

    var bitmap by remember(cacheKey) { mutableStateOf(MoonBitmapCache.get(cacheKey)) }

    LaunchedEffect(cacheKey) {
        if (bitmap == null) {
            val generated = withContext(Dispatchers.Default) {
                LunarBitmapRenderer.renderMoonBitmap(
                    sizePx = targetPx,
                    phaseFraction = quantizedPhase,
                    isEclipseBloodMoon = effectiveTint == MoonTintMode.BLOOD_MOON,
                    includeOuterHalo = includeOuterHalo,
                    tintMode = effectiveTint
                )
            }
            MoonBitmapCache.put(cacheKey, generated)
            bitmap = generated
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        bitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

/**
 * Zero-layout-shift astronomy photography container.
 * Always displays rich bespoke astronomical artwork immediately underneath, and smoothly
 * crossfades into cached high-resolution NASA/astronomy photography when online.
 */
@Composable
fun AstronomyArtworkImage(
    imageUrl: String,
    fallbackType: VisualFallbackType,
    contentDescription: String,
    modifier: Modifier = Modifier,
    phaseFractionOverride: Double = 0.5,
    contentScale: ContentScale = ContentScale.Crop
) {
    Box(modifier = modifier.background(Color(0xFF050811))) {
        ProceduralAstronomyScene(
            type = fallbackType,
            phaseFraction = phaseFractionOverride,
            modifier = Modifier.fillMaxSize()
        )
        if (imageUrl.isNotBlank()) {
            val context = LocalContext.current
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(400)
                    .build(),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize(),
                loading = { /* Procedural artwork already visible with zero flicker */ },
                error = { /* Procedural artwork remains crisp offline */ }
            )
        }
    }
}

@Composable
fun ProceduralAstronomyScene(
    type: VisualFallbackType,
    phaseFraction: Double = 0.5,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            val gradientColors = when (type) {
                VisualFallbackType.BLOOD_MOON -> listOf(Color(0xFF0E0507), Color(0xFF210B10), Color(0xFF070408))
                VisualFallbackType.BLUE_MOON -> listOf(Color(0xFF040918), Color(0xFF0C1D40), Color(0xFF03060E))
                VisualFallbackType.GOLDEN_MOON -> listOf(Color(0xFF140C05), Color(0xFF2B190B), Color(0xFF080504))
                VisualFallbackType.MOON_OCEAN -> listOf(Color(0xFF050A18), Color(0xFF0D1B36), Color(0xFF03060E))
                VisualFallbackType.MOON_MOUNTAINS -> listOf(Color(0xFF070D1D), Color(0xFF141D35), Color(0xFF04060C))
                VisualFallbackType.GALAXY, VisualFallbackType.DEEP_SPACE -> listOf(Color(0xFF050712), Color(0xFF14132E), Color(0xFF04050B))
                VisualFallbackType.NIGHT_SKY -> listOf(Color(0xFF040714), Color(0xFF0D1730), Color(0xFF04060C))
                VisualFallbackType.LUNAR_SURFACE, VisualFallbackType.APOLLO_SURFACE -> listOf(Color(0xFF0B0D12), Color(0xFF1A1E28), Color(0xFF06070A))
                else -> listOf(Color(0xFF020408), Color(0xFF070B16), Color(0xFF020306))
            }

            drawRect(brush = Brush.verticalGradient(gradientColors))

            // Restrained realistic starfield
            if (type != VisualFallbackType.CRESCENT_MOON && type != VisualFallbackType.LUNAR_SURFACE) {
                val count = if (type == VisualFallbackType.GALAXY || type == VisualFallbackType.NIGHT_SKY) 85 else 36
                for (i in 0 until count) {
                    val x = ((i * 7919 + 43) % 1000) / 1000f * w
                    val y = ((i * 104729 + 19) % 1000) / 1000f * h * 0.78f
                    val r = if (i % 9 == 0) 1.8f else 1.0f
                    val alpha = 0.25f + ((i * 29) % 60) / 100f
                    drawCircle(color = Color(0xFFE3ECFF).copy(alpha = alpha), radius = r, center = Offset(x, y))
                }
            }

            when (type) {
                VisualFallbackType.MOON_OCEAN -> {
                    val horizon = h * 0.62f
                    drawRect(
                        brush = Brush.verticalGradient(listOf(Color(0xFF081226), Color(0xFF02050B))),
                        topLeft = Offset(0f, horizon)
                    )
                    for (row in 0 until 24) {
                        val t = row / 24f
                        val y = horizon + t * (h - horizon) * 0.9f
                        val half = w * (0.04f + t * 0.16f)
                        val jitter = sin(row * 1.7).toFloat() * w * 0.015f
                        drawLine(
                            color = Color(0xFFD8E5FF).copy(alpha = (1f - t) * 0.55f),
                            start = Offset(w / 2f - half + jitter, y),
                            end = Offset(w / 2f + half + jitter, y),
                            strokeWidth = 2.2f
                        )
                    }
                }
                VisualFallbackType.MOON_MOUNTAINS -> {
                    val ridge = Path().apply {
                        moveTo(0f, h * 0.74f)
                        lineTo(w * 0.24f, h * 0.61f)
                        lineTo(w * 0.46f, h * 0.70f)
                        lineTo(w * 0.71f, h * 0.57f)
                        lineTo(w * 0.90f, h * 0.66f)
                        lineTo(w, h * 0.62f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(ridge, Color(0xFF090E1B))
                }
                VisualFallbackType.GALAXY, VisualFallbackType.DEEP_SPACE -> {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0x66A8BFFF), Color(0x337664BA), Color.Transparent),
                            center = Offset(w * 0.52f, h * 0.46f),
                            radius = min(w, h) * 0.58f
                        ),
                        center = Offset(w * 0.52f, h * 0.46f),
                        radius = min(w, h) * 0.58f
                    )
                }
                VisualFallbackType.MINIMAL_ORBIT -> {
                    val c = Offset(w / 2f, h / 2f)
                    val r = min(w, h) * 0.34f
                    drawCircle(Color(0x338AAEFF), radius = r, center = c, style = Stroke(width = 1.5f))
                    drawCircle(Color(0x22DCE3F0), radius = r * 0.6f, center = c, style = Stroke(width = 1f))
                    drawCircle(Color(0xFF8AAEFF), radius = 8f, center = c)
                    drawCircle(
                        Color(0xFFF2F5FC),
                        radius = 5f,
                        center = Offset(c.x + cos(0.8f) * r, c.y - sin(0.8f) * r)
                    )
                }
                VisualFallbackType.LUNAR_SURFACE, VisualFallbackType.APOLLO_SURFACE -> {
                    // Subtle crater ridge arcs
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(Color(0x339AA6BD), Color(0x11454D5E), Color.Transparent),
                            center = Offset(w * 0.5f, h * 1.15f),
                            radius = w * 0.85f
                        ),
                        center = Offset(w * 0.5f, h * 1.15f),
                        radius = w * 0.85f
                    )
                    drawCircle(
                        color = Color(0x30DCE3F0),
                        radius = min(w, h) * 0.24f,
                        center = Offset(w * 0.46f, h * 0.52f),
                        style = Stroke(width = 2f)
                    )
                    drawCircle(
                        color = Color(0x2294A3B8),
                        radius = min(w, h) * 0.11f,
                        center = Offset(w * 0.72f, h * 0.38f),
                        style = Stroke(width = 1.5f)
                    )
                }
                else -> Unit
            }
        }

        // Overlay our realistic procedural Moon sphere for lunar scenes
        if (type in listOf(
                VisualFallbackType.FULL_MOON,
                VisualFallbackType.CRESCENT_MOON,
                VisualFallbackType.BLOOD_MOON,
                VisualFallbackType.BLUE_MOON,
                VisualFallbackType.GOLDEN_MOON,
                VisualFallbackType.MOON_OCEAN,
                VisualFallbackType.MOON_MOUNTAINS
            )
        ) {
            val moonAlign = when (type) {
                VisualFallbackType.MOON_OCEAN, VisualFallbackType.MOON_MOUNTAINS -> Alignment.TopCenter
                else -> Alignment.Center
            }
            val tint = when (type) {
                VisualFallbackType.BLOOD_MOON -> MoonTintMode.BLOOD_MOON
                VisualFallbackType.BLUE_MOON -> MoonTintMode.BLUE_MOON
                VisualFallbackType.GOLDEN_MOON -> MoonTintMode.GOLDEN_MOON
                else -> MoonTintMode.NORMAL
            }
            RealisticMoonDisc(
                phaseFraction = if (type == VisualFallbackType.CRESCENT_MOON) 0.14 else phaseFraction,
                tintMode = tint,
                sizeDp = if (type == VisualFallbackType.MOON_OCEAN || type == VisualFallbackType.MOON_MOUNTAINS) 140.dp else 185.dp,
                modifier = Modifier
                    .align(moonAlign)
                    .fillMaxSize(if (type == VisualFallbackType.MOON_OCEAN || type == VisualFallbackType.MOON_MOUNTAINS) 0.56f else 0.78f)
            )
        }
    }
}
