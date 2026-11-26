package com.example

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.example.localization.LunarLocaleManager
import com.example.notifications.LunarNotificationManager

class LunarApplication : Application(), ImageLoaderFactory {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LunarLocaleManager.wrapContext(base))
    }

    override fun onCreate() {
        super.onCreate()
        LunarNotificationManager.createNotificationChannel(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.22)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("lunar_image_cache"))
                    .maxSizeBytes(64L * 1024L * 1024L)
                    .build()
            }
            .crossfade(true)
            .respectCacheHeaders(false)
            .build()
    }
}
