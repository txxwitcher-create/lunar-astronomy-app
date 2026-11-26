package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.MainActivity
import com.example.R
import com.example.astronomy.AstronomyEngine
import com.example.astronomy.WidgetTheme
import com.example.astronomy.formatInstantTime
import com.example.data.SettingsRepository
import com.example.localization.LunarLocaleManager
import com.example.localization.LunarStringCatalog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

private enum class LunarWidgetSize { SMALL, MEDIUM, LARGE }

class LunarSmallWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateWidgetInstances(context, appWidgetManager, appWidgetIds, LunarWidgetSize.SMALL)
    }
}

class LunarMediumWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateWidgetInstances(context, appWidgetManager, appWidgetIds, LunarWidgetSize.MEDIUM)
    }
}

class LunarLargeWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        updateWidgetInstances(context, appWidgetManager, appWidgetIds, LunarWidgetSize.LARGE)
    }
}

fun refreshAllLunarWidgets(context: Context) {
    val manager = AppWidgetManager.getInstance(context)
    listOf(
        LunarSmallWidgetProvider::class.java to LunarWidgetSize.SMALL,
        LunarMediumWidgetProvider::class.java to LunarWidgetSize.MEDIUM,
        LunarLargeWidgetProvider::class.java to LunarWidgetSize.LARGE
    ).forEach { (cls, size) ->
        val ids = manager.getAppWidgetIds(ComponentName(context, cls))
        if (ids.isNotEmpty()) {
            updateWidgetInstances(context, manager, ids, size)
        }
    }
}

private fun updateWidgetInstances(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray,
    size: LunarWidgetSize
) {
    CoroutineScope(Dispatchers.Default).launch {
        val settings = SettingsRepository(context).getCurrentSettings()
        val langCode = LunarLocaleManager.getSavedLanguageCode(context)
        val strings = LunarStringCatalog.forCode(langCode)

        val now = System.currentTimeMillis()
        val snapshot = AstronomyEngine.getLunarSnapshot(now, settings.location)
        val nextFullMillis = AstronomyEngine.findNextPhaseInstant(now, 180.0)
        val nextEvent = AstronomyEngine.getUpcomingEvents(now).firstOrNull()
        val moonBitmap = LunarBitmapRenderer.renderMoonBitmap(
            sizePx = if (size == LunarWidgetSize.LARGE) 240 else 192,
            phaseFraction = snapshot.phaseFraction,
            includeOuterHalo = settings.widgetTheme != WidgetTheme.MINIMAL
        )

        val bgRes = when (settings.widgetTheme) {
            WidgetTheme.BLACK -> R.drawable.widget_bg_black
            WidgetTheme.MIDNIGHT_BLUE -> R.drawable.widget_bg_midnight
            WidgetTheme.TRANSPARENT -> R.drawable.widget_bg_transparent
            WidgetTheme.MINIMAL -> R.drawable.widget_bg_minimal
            WidgetTheme.IMAGE_FOCUSED -> R.drawable.widget_bg_black
        }

        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            size.ordinal,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val phaseLabel = strings.phaseName(snapshot.phaseType)
        val waxingLabel = if (snapshot.isWaxing) strings.waxing else strings.waning

        appWidgetIds.forEach { widgetId ->
            val layoutRes = when (size) {
                LunarWidgetSize.SMALL -> R.layout.widget_lunar_small
                LunarWidgetSize.MEDIUM -> R.layout.widget_lunar_medium
                LunarWidgetSize.LARGE -> R.layout.widget_lunar_large
            }
            val views = RemoteViews(context.packageName, layoutRes)
            views.setInt(R.id.widget_root, "setBackgroundResource", bgRes)
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)
            views.setImageViewBitmap(R.id.widget_moon_image, moonBitmap)
            views.setTextViewText(R.id.widget_phase_name, phaseLabel)
            views.setTextViewText(
                R.id.widget_illumination,
                "${snapshot.illuminationPercent.roundToInt()}% ${strings.illuminated}"
            )

            when (size) {
                LunarWidgetSize.SMALL -> Unit
                LunarWidgetSize.MEDIUM -> {
                    val countdown = AstronomyEngine.formatCountdown(nextFullMillis, now)
                    views.setTextViewText(R.id.widget_countdown, "${strings.phaseFullMoon} • $countdown")
                }
                LunarWidgetSize.LARGE -> {
                    views.setTextViewText(
                        R.id.widget_lunar_age,
                        String.format(
                            Locale.US,
                            "%s %.1f %s • %s",
                            strings.lunarAge,
                            snapshot.lunarAgeDays,
                            strings.daysUnit,
                            waxingLabel
                        )
                    )
                    views.setTextViewText(
                        R.id.widget_moonrise,
                        "${strings.moonrise}  ${formatInstantTime(snapshot.moonriseMillis, settings.use24HourTime)}"
                    )
                    views.setTextViewText(
                        R.id.widget_moonset,
                        "${strings.moonset}  ${formatInstantTime(snapshot.moonsetMillis, settings.use24HourTime)}"
                    )
                    val nextLabel = nextEvent?.let {
                        "${it.title} • ${AstronomyEngine.formatCountdown(it.timestampMillis, now)}"
                    } ?: "${strings.phaseFullMoon} • ${AstronomyEngine.formatCountdown(nextFullMillis, now)}"
                    views.setTextViewText(R.id.widget_next_event, nextLabel)
                }
            }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}
