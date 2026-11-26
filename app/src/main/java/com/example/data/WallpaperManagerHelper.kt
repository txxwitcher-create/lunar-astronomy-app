package com.example.data

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.astronomy.WallpaperItem
import com.example.astronomy.WallpaperQuality
import com.example.widget.LunarBitmapRenderer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object WallpaperManagerHelper {

    /**
     * Fetches the wallpaper bitmap via Coil if online and remote URL is provided,
     * falling back seamlessly to our high-resolution procedural astronomy renderer.
     */
    private suspend fun resolveWallpaperBitmap(
        context: Context,
        item: WallpaperItem,
        quality: WallpaperQuality
    ): Bitmap = withContext(Dispatchers.IO) {
        if (item.imageUrl.isNotBlank()) {
            try {
                val loader = ImageLoader(context)
                val req = ImageRequest.Builder(context)
                    .data(item.imageUrl)
                    .allowHardware(false)
                    .build()
                val res = loader.execute(req)
                if (res is SuccessResult) {
                    val bmp = (res.drawable as? BitmapDrawable)?.bitmap
                    if (bmp != null) return@withContext bmp
                }
            } catch (_: Exception) {
                // Fall back to procedural high-res bitmap
            }
        }
        LunarBitmapRenderer.renderWallpaperBitmap(item, quality)
    }

    /**
     * Saves wallpaper cleanly to Pictures/LUNAR via MediaStore without requiring broad storage permissions.
     */
    suspend fun saveToGallery(
        context: Context,
        item: WallpaperItem,
        quality: WallpaperQuality
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val bitmap = resolveWallpaperBitmap(context, item, quality)
            val filename = "LUNAR_${item.id}_${System.currentTimeMillis()}.jpg"
            val compressQuality = when (quality) {
                WallpaperQuality.ORIGINAL -> 96
                WallpaperQuality.BALANCED -> 88
                WallpaperQuality.DATA_SAVER -> 76
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/LUNAR")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                    ?: error("Could not create MediaStore entry")
                resolver.openOutputStream(uri)?.use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, out)
                } ?: error("Could not open output stream")
                values.clear()
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(uri, values, null, null)
                "Saved to Pictures/LUNAR"
            } else {
                val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "LUNAR")
                dir.mkdirs()
                val file = File(dir, filename)
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, out)
                }
                "Saved to ${file.name}"
            }
        }
    }

    /**
     * Sets wallpaper directly on Android Home Screen, Lock Screen, or Both using WallpaperManager.
     */
    suspend fun setDeviceWallpaper(
        context: Context,
        item: WallpaperItem,
        quality: WallpaperQuality,
        flags: Int = WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
    ): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val bitmap = resolveWallpaperBitmap(context, item, quality)
            val wm = WallpaperManager.getInstance(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wm.setBitmap(bitmap, null, true, flags)
            } else {
                wm.setBitmap(bitmap)
            }
            "Wallpaper applied"
        }
    }

    /**
     * Shares wallpaper via Android ShareSheet using FileProvider.
     */
    suspend fun shareWallpaper(
        context: Context,
        item: WallpaperItem,
        quality: WallpaperQuality
    ): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val bitmap = resolveWallpaperBitmap(context, item, quality)
            val shareDir = File(context.cacheDir, "wallpapers").apply { mkdirs() }
            val file = File(shareDir, "lunar_share_${item.id}.jpg")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 92, out)
            }
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, "${item.title} — from LUNAR")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val chooser = Intent.createChooser(intent, "Share ${item.title}").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
        }
    }
}
