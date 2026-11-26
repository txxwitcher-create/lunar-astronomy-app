package com.example.widget

import android.graphics.*
import com.example.astronomy.VisualFallbackType
import com.example.astronomy.WallpaperItem
import com.example.astronomy.WallpaperQuality
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

enum class MoonTintMode {
    NORMAL,
    BLOOD_MOON,
    BLUE_MOON,
    GOLDEN_MOON
}

/**
 * High-precision procedural Moon & astronomical artwork Bitmap renderer.
 * Uses real selenographic near-side maria & crater positions with spherical
 * Lommel-Seeliger terminator photometry, subtle earthshine, and artistic/eclipse tinting.
 * Used for Home Screen Widgets, Wallpaper exports, Intro animations, and offline fallback artwork.
 */
object LunarBitmapRenderer {

    private data class MareBlob(val x: Float, val y: Float, val rx: Float, val ry: Float, val rot: Float, val darkness: Float)
    private data class CraterSpot(val x: Float, val y: Float, val r: Float, val brightness: Float, val hasRays: Boolean = false)

    // Near-side selenographic normalized coordinates (-1..1 disc space)
    private val maria = listOf(
        MareBlob(-0.32f, -0.32f, 0.34f, 0.30f, -15f, 0.36f), // Mare Imbrium
        MareBlob(0.18f, -0.30f, 0.23f, 0.22f, 10f, 0.34f),   // Mare Serenitatis
        MareBlob(0.36f, -0.04f, 0.25f, 0.22f, 22f, 0.40f),   // Mare Tranquillitatis
        MareBlob(0.56f, 0.16f, 0.18f, 0.22f, -12f, 0.33f),   // Mare Fecunditatis
        MareBlob(0.34f, 0.26f, 0.14f, 0.14f, 5f, 0.30f),     // Mare Nectaris
        MareBlob(0.68f, -0.22f, 0.13f, 0.15f, 8f, 0.38f),    // Mare Crisium
        MareBlob(-0.56f, -0.02f, 0.28f, 0.44f, -8f, 0.34f),  // Oceanus Procellarum
        MareBlob(-0.24f, 0.28f, 0.22f, 0.18f, 14f, 0.30f),   // Mare Nubium
        MareBlob(-0.48f, 0.34f, 0.15f, 0.14f, -10f, 0.31f),  // Mare Humorum
        MareBlob(-0.08f, -0.62f, 0.36f, 0.11f, -4f, 0.28f)   // Mare Frigoris
    )

    private val craters = listOf(
        CraterSpot(-0.12f, 0.60f, 0.055f, 0.92f, hasRays = true),  // Tycho
        CraterSpot(-0.30f, -0.14f, 0.058f, 0.88f, hasRays = true), // Copernicus
        CraterSpot(-0.58f, -0.32f, 0.032f, 0.96f, hasRays = true), // Aristarchus
        CraterSpot(-0.54f, -0.08f, 0.036f, 0.82f, hasRays = true), // Kepler
        CraterSpot(-0.12f, -0.68f, 0.045f, 0.28f, hasRays = false),// Plato (dark floor)
        CraterSpot(-0.14f, 0.74f, 0.075f, 0.68f, hasRays = false), // Clavius
        CraterSpot(0.18f, 0.44f, 0.044f, 0.74f, hasRays = false),  // Theophilus
        CraterSpot(-0.04f, 0.12f, 0.052f, 0.65f, hasRays = false)  // Ptolemaeus
    )

    fun renderMoonBitmap(
        sizePx: Int,
        phaseFraction: Double,
        isEclipseBloodMoon: Boolean = false,
        includeOuterHalo: Boolean = true,
        tintMode: MoonTintMode = if (isEclipseBloodMoon) MoonTintMode.BLOOD_MOON else MoonTintMode.NORMAL
    ): Bitmap {
        val effectiveTint = if (isEclipseBloodMoon) MoonTintMode.BLOOD_MOON else tintMode
        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val cx = sizePx / 2f
        val cy = sizePx / 2f
        val radius = sizePx * (if (includeOuterHalo) 0.41f else 0.47f)

        // 1. Subtle atmospheric scatter halo (very restrained, luxury editorial feel)
        if (includeOuterHalo) {
            val haloColor = when (effectiveTint) {
                MoonTintMode.BLOOD_MOON -> Color.argb(72, 200, 74, 42)
                MoonTintMode.BLUE_MOON -> Color.argb(66, 98, 160, 255)
                MoonTintMode.GOLDEN_MOON -> Color.argb(72, 240, 178, 92)
                MoonTintMode.NORMAL -> Color.argb(46, 175, 198, 245)
            }
            val haloPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = RadialGradient(
                    cx, cy, radius * 1.24f,
                    intArrayOf(haloColor, Color.TRANSPARENT),
                    floatArrayOf(0.76f, 1.0f),
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawCircle(cx, cy, radius * 1.24f, haloPaint)
        }

        // Clip strictly to the Moon sphere
        val saveCount = canvas.save()
        val clipPath = Path().apply { addCircle(cx, cy, radius, Path.Direction.CW) }
        canvas.clipPath(clipPath)

        // 2. Base lunar highland regolith albedo gradient
        val basePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = RadialGradient(
                cx - radius * 0.12f, cy - radius * 0.12f, radius * 1.15f,
                intArrayOf(
                    Color.rgb(232, 234, 238),
                    Color.rgb(206, 210, 218),
                    Color.rgb(168, 173, 184)
                ),
                floatArrayOf(0f, 0.65f, 1f),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawCircle(cx, cy, radius, basePaint)

        // 3. Ancient volcanic lunar maria (layered soft organic basalt basins)
        maria.forEach { m ->
            canvas.save()
            val mx = cx + m.x * radius
            val my = cy + m.y * radius
            canvas.translate(mx, my)
            canvas.rotate(m.rot)
            val rx = m.rx * radius
            val ry = m.ry * radius
            val alpha = (m.darkness * 255).toInt().coerceIn(25, 130)
            val marePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = RadialGradient(
                    0f, 0f, max(rx, ry),
                    intArrayOf(
                        Color.argb(alpha, 56, 62, 76),
                        Color.argb((alpha * 0.7f).toInt(), 70, 76, 90),
                        Color.TRANSPARENT
                    ),
                    floatArrayOf(0f, 0.62f, 1f),
                    Shader.TileMode.CLAMP
                )
            }
            canvas.scale(1f, ry / rx)
            canvas.drawCircle(0f, 0f, rx, marePaint)
            canvas.restore()
        }

        // 4. Fine regolith micro-texture speckles (deterministic, zero jitter)
        val microPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        for (i in 0 until 95) {
            val angle = i * 2.3999632 // golden angle
            val dist = sqrt((i + 0.5) / 95.0).toFloat() * radius * 0.92f
            val px = cx + cos(angle).toFloat() * dist
            val py = cy + sin(angle).toFloat() * dist
            val r = radius * (0.012f + (i % 5) * 0.005f)
            val bright = i % 3 == 0
            microPaint.color = if (bright) Color.argb(38, 248, 250, 255) else Color.argb(42, 62, 68, 82)
            canvas.drawCircle(px, py, r, microPaint)
        }

        // 5. Major impact craters + bright ejecta rays (Tycho, Copernicus, Aristarchus)
        craters.forEach { c ->
            val x = cx + c.x * radius
            val y = cy + c.y * radius
            val cr = c.r * radius
            if (c.hasRays) {
                val rayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    strokeWidth = max(1f, radius * 0.008f)
                    color = Color.argb(58, 245, 248, 255)
                }
                for (k in 0 until 9) {
                    val ang = k * (2.0 * PI / 9.0) + c.x * 2.0
                    val len = radius * (0.28f + (k % 3) * 0.14f)
                    canvas.drawLine(
                        x, y,
                        x + cos(ang).toFloat() * len,
                        y + sin(ang).toFloat() * len,
                        rayPaint
                    )
                }
            }
            val rimPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = RadialGradient(
                    x, y, cr * 1.45f,
                    intArrayOf(
                        Color.argb(120, 75, 80, 92),
                        Color.argb((c.brightness * 150).toInt(), 240, 244, 252),
                        Color.TRANSPARENT
                    ),
                    floatArrayOf(0f, 0.62f, 1f),
                    Shader.TileMode.CLAMP
                )
            }
            canvas.drawCircle(x, y, cr * 1.45f, rimPaint)
        }

        // 6. Optional artistic or eclipse tint overlay
        when (effectiveTint) {
            MoonTintMode.BLOOD_MOON -> {
                val bloodPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    shader = RadialGradient(
                        cx - radius * 0.25f, cy - radius * 0.2f, radius * 1.35f,
                        intArrayOf(
                            Color.argb(205, 196, 68, 34),
                            Color.argb(225, 134, 34, 20),
                            Color.argb(242, 46, 12, 10)
                        ),
                        floatArrayOf(0f, 0.58f, 1f),
                        Shader.TileMode.CLAMP
                    )
                    xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
                }
                canvas.drawCircle(cx, cy, radius, bloodPaint)
            }
            MoonTintMode.BLUE_MOON -> {
                val bluePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    shader = RadialGradient(
                        cx - radius * 0.15f, cy - radius * 0.15f, radius * 1.25f,
                        intArrayOf(
                            Color.argb(175, 146, 198, 255),
                            Color.argb(195, 86, 138, 232),
                            Color.argb(220, 34, 62, 130)
                        ),
                        floatArrayOf(0f, 0.62f, 1f),
                        Shader.TileMode.CLAMP
                    )
                    xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
                }
                canvas.drawCircle(cx, cy, radius, bluePaint)
                applySphericalPhaseShadow(canvas, cx, cy, radius, phaseFraction)
            }
            MoonTintMode.GOLDEN_MOON -> {
                val goldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    shader = RadialGradient(
                        cx - radius * 0.18f, cy - radius * 0.15f, radius * 1.28f,
                        intArrayOf(
                            Color.argb(185, 255, 218, 146),
                            Color.argb(205, 228, 158, 72),
                            Color.argb(228, 138, 78, 28)
                        ),
                        floatArrayOf(0f, 0.60f, 1f),
                        Shader.TileMode.CLAMP
                    )
                    xfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
                }
                canvas.drawCircle(cx, cy, radius, goldPaint)
                applySphericalPhaseShadow(canvas, cx, cy, radius, phaseFraction)
            }
            MoonTintMode.NORMAL -> {
                // 7. Physically accurate 3D spherical terminator shadow with earthshine
                applySphericalPhaseShadow(canvas, cx, cy, radius, phaseFraction)
            }
        }

        // 8. Subtle spherical limb shading
        val limbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = RadialGradient(
                cx, cy, radius,
                intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT, Color.argb(95, 5, 8, 16)),
                floatArrayOf(0f, 0.84f, 1f),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawCircle(cx, cy, radius, limbPaint)

        canvas.restoreToCount(saveCount)
        return bitmap
    }

    private fun applySphericalPhaseShadow(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        radius: Float,
        phaseFraction: Double
    ) {
        val p = ((phaseFraction % 1.0) + 1.0) % 1.0
        // Sun direction vector in observer-centered lunar sphere coordinates:
        // p = 0.0 -> Sun behind Moon (0, 0, -1) -> all dark
        // p = 0.25 -> First Quarter -> Sun from right (+1, 0, 0)
        // p = 0.50 -> Full Moon -> Sun behind observer (0, 0, +1) -> all lit
        // p = 0.75 -> Last Quarter -> Sun from left (-1, 0, 0)
        val angle = p * 2.0 * PI
        val sunX = sin(angle)
        val sunZ = -cos(angle)

        val steps = 64
        val shadowBmp = Bitmap.createBitmap(steps, steps, Bitmap.Config.ARGB_8888)
        val pixels = IntArray(steps * steps)

        for (py in 0 until steps) {
            val ny = (py + 0.5f) / steps * 2f - 1f
            for (px in 0 until steps) {
                val nx = (px + 0.5f) / steps * 2f - 1f
                val r2 = nx * nx + ny * ny
                if (r2 <= 1.02f) {
                    val nz = sqrt(max(0f, 1f - r2))
                    val dot = nx * sunX + nz * sunZ
                    // Smooth lunar regolith terminator falloff + subtle earthshine (never pitch-flat)
                    val lit = ((dot + 0.08) / 0.20).coerceIn(0.0, 1.0)
                    val shadowAlpha = ((1.0 - lit) * 238.0).toInt().coerceIn(0, 238)
                    pixels[py * steps + px] = Color.argb(shadowAlpha, 7, 10, 19)
                } else {
                    pixels[py * steps + px] = Color.TRANSPARENT
                }
            }
        }
        shadowBmp.setPixels(pixels, 0, steps, 0, 0, steps, steps)
        val dst = RectF(cx - radius, cy - radius, cx + radius, cy + radius)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.drawBitmap(shadowBmp, null, dst, paint)
        shadowBmp.recycle()
    }

    /**
     * Generates a genuine high-resolution portrait wallpaper Bitmap suitable for saving to gallery,
     * sharing, or setting as device wallpaper (even offline!).
     */
    fun renderWallpaperBitmap(item: WallpaperItem, quality: WallpaperQuality): Bitmap {
        val (width, height) = when (quality) {
            WallpaperQuality.ORIGINAL -> 1440 to 2560
            WallpaperQuality.BALANCED -> 1080 to 1920
            WallpaperQuality.DATA_SAVER -> 720 to 1280
        }
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)

        val bgColors = when (item.fallbackVisualType) {
            VisualFallbackType.BLOOD_MOON -> intArrayOf(Color.rgb(12, 4, 7), Color.rgb(26, 10, 14), Color.rgb(6, 4, 8))
            VisualFallbackType.BLUE_MOON -> intArrayOf(Color.rgb(4, 9, 24), Color.rgb(10, 24, 54), Color.rgb(3, 6, 14))
            VisualFallbackType.GOLDEN_MOON -> intArrayOf(Color.rgb(16, 10, 5), Color.rgb(34, 20, 9), Color.rgb(7, 5, 4))
            VisualFallbackType.MOON_OCEAN -> intArrayOf(Color.rgb(5, 10, 24), Color.rgb(12, 22, 44), Color.rgb(3, 6, 14))
            VisualFallbackType.MOON_MOUNTAINS -> intArrayOf(Color.rgb(6, 11, 24), Color.rgb(16, 22, 40), Color.rgb(4, 6, 12))
            VisualFallbackType.GALAXY, VisualFallbackType.DEEP_SPACE -> intArrayOf(Color.rgb(5, 7, 16), Color.rgb(17, 16, 38), Color.rgb(4, 5, 11))
            VisualFallbackType.NIGHT_SKY -> intArrayOf(Color.rgb(4, 7, 18), Color.rgb(10, 18, 36), Color.rgb(3, 5, 10))
            else -> intArrayOf(Color.rgb(2, 3, 7), Color.rgb(6, 9, 18), Color.rgb(0, 0, 0))
        }

        val bgPaint = Paint().apply {
            shader = LinearGradient(0f, 0f, 0f, height.toFloat(), bgColors, floatArrayOf(0f, 0.55f, 1f), Shader.TileMode.CLAMP)
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Subtle realistic starfield (restrained, pinpoint, varied magnitudes)
        if (item.fallbackVisualType != VisualFallbackType.CRESCENT_MOON) {
            val starPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            val starCount = if (item.fallbackVisualType == VisualFallbackType.GALAXY || item.fallbackVisualType == VisualFallbackType.NIGHT_SKY) 240 else 90
            for (i in 0 until starCount) {
                val sx = ((i * 7919 + 137) % width).toFloat()
                val sy = ((i * 104729 + 521) % (height * 0.78f).toInt()).toFloat()
                val sr = if (i % 11 == 0) width * 0.0022f else width * 0.0011f
                val alpha = 70 + (i * 37) % 165
                starPaint.color = Color.argb(alpha, 225, 234, 255)
                canvas.drawCircle(sx, sy, sr, starPaint)
            }
        }

        // Foreground scene / Moon composition depending on artwork style
        when (item.fallbackVisualType) {
            VisualFallbackType.MOON_OCEAN -> {
                val moonSize = (width * 0.52f).toInt()
                val moonBmp = renderMoonBitmap(moonSize, item.phaseFractionOverride, false, true)
                canvas.drawBitmap(moonBmp, (width - moonSize) / 2f, height * 0.20f, null)
                moonBmp.recycle()

                val horizonY = height * 0.64f
                val oceanPaint = Paint().apply {
                    shader = LinearGradient(0f, horizonY, 0f, height.toFloat(), Color.rgb(7, 13, 28), Color.rgb(2, 4, 10), Shader.TileMode.CLAMP)
                }
                canvas.drawRect(0f, horizonY, width.toFloat(), height.toFloat(), oceanPaint)

                // Shimmering moonlight water reflection
                val shimmerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
                for (row in 0 until 44) {
                    val t = row / 44f
                    val y = horizonY + t * (height - horizonY) * 0.92f
                    val spread = width * (0.04f + t * 0.16f)
                    val alpha = ((1f - t) * 145).toInt().coerceIn(15, 145)
                    shimmerPaint.color = Color.argb(alpha, 220, 232, 255)
                    shimmerPaint.strokeWidth = max(2f, width * 0.003f)
                    val jitter = sin(row * 1.9).toFloat() * width * 0.015f
                    canvas.drawLine(width / 2f - spread + jitter, y, width / 2f + spread + jitter, y, shimmerPaint)
                }
            }
            VisualFallbackType.MOON_MOUNTAINS -> {
                val moonSize = (width * 0.56f).toInt()
                val moonBmp = renderMoonBitmap(moonSize, item.phaseFractionOverride, false, true)
                canvas.drawBitmap(moonBmp, (width - moonSize) / 2f, height * 0.18f, null)
                moonBmp.recycle()

                // Layered alpine ridge silhouettes
                val ridge1 = Path().apply {
                    moveTo(0f, height * 0.72f)
                    lineTo(width * 0.22f, height * 0.62f)
                    lineTo(width * 0.44f, height * 0.69f)
                    lineTo(width * 0.68f, height * 0.58f)
                    lineTo(width * 0.88f, height * 0.66f)
                    lineTo(width.toFloat(), height * 0.62f)
                    lineTo(width.toFloat(), height.toFloat())
                    lineTo(0f, height.toFloat())
                    close()
                }
                canvas.drawPath(ridge1, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(10, 14, 24) })

                val ridge2 = Path().apply {
                    moveTo(0f, height * 0.80f)
                    lineTo(width * 0.30f, height * 0.71f)
                    lineTo(width * 0.56f, height * 0.78f)
                    lineTo(width * 0.82f, height * 0.69f)
                    lineTo(width.toFloat(), height * 0.76f)
                    lineTo(width.toFloat(), height.toFloat())
                    lineTo(0f, height.toFloat())
                    close()
                }
                canvas.drawPath(ridge2, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.rgb(4, 6, 12) })
            }
            VisualFallbackType.GALAXY, VisualFallbackType.DEEP_SPACE -> {
                canvas.save()
                canvas.translate(width * 0.5f, height * 0.46f)
                canvas.rotate(-28f)
                val bandPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    shader = RadialGradient(
                        0f, 0f, width * 0.78f,
                        intArrayOf(
                            Color.argb(120, 175, 192, 242),
                            Color.argb(72, 122, 108, 186),
                            Color.TRANSPARENT
                        ),
                        floatArrayOf(0f, 0.42f, 1f),
                        Shader.TileMode.CLAMP
                    )
                }
                canvas.scale(1.6f, 0.38f)
                canvas.drawCircle(0f, 0f, width * 0.6f, bandPaint)
                canvas.restore()
            }
            else -> {
                val moonSize = (min(width, height) * 0.74f).toInt()
                val tint = when (item.fallbackVisualType) {
                    VisualFallbackType.BLOOD_MOON -> MoonTintMode.BLOOD_MOON
                    VisualFallbackType.BLUE_MOON -> MoonTintMode.BLUE_MOON
                    VisualFallbackType.GOLDEN_MOON -> MoonTintMode.GOLDEN_MOON
                    else -> MoonTintMode.NORMAL
                }
                val moonBmp = renderMoonBitmap(
                    sizePx = moonSize,
                    phaseFraction = item.phaseFractionOverride,
                    isEclipseBloodMoon = tint == MoonTintMode.BLOOD_MOON,
                    includeOuterHalo = true,
                    tintMode = tint
                )
                canvas.drawBitmap(moonBmp, (width - moonSize) / 2f, (height - moonSize) * 0.42f, null)
                moonBmp.recycle()
            }
        }
        return bmp
    }
}
