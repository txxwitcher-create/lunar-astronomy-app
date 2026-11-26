package com.example.notifications

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.MainActivity
import com.example.R
import com.example.astronomy.AstronomyEngine
import com.example.astronomy.NotificationPreferences
import com.example.astronomy.UserLocation
import kotlin.math.roundToInt

object LunarNotificationManager {

    const val CHANNEL_ID = "lunar_astronomy_quiet"
    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_BODY = "extra_body"
    const val EXTRA_NOTIF_ID = "extra_notif_id"

    private const val REQ_FULL_MOON = 4001
    private const val REQ_NEW_MOON = 4002
    private const val REQ_ECLIPSE = 4003
    private const val REQ_MOONRISE = 4004
    private const val REQ_DAILY = 4005

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.notification_channel_desc)
                enableLights(false)
                enableVibration(false)
            }
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    /**
     * Schedules battery-conscious inexact Android AlarmManager reminders for enabled categories
     * and cancels disabled categories cleanly.
     */
    fun syncScheduledAlerts(
        context: Context,
        prefs: NotificationPreferences,
        location: UserLocation
    ) {
        createNotificationChannel(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val now = System.currentTimeMillis()

        // Full Moon reminder (2 hours before exact opposition)
        if (prefs.fullMoonAlerts) {
            val fullMillis = AstronomyEngine.findNextPhaseInstant(now, 180.0)
            val trigger = (fullMillis - 2L * 3600_000L).coerceAtLeast(now + 60_000L)
            scheduleOneShot(
                context, alarmManager, REQ_FULL_MOON, trigger,
                "Full Moon Rising Tonight",
                "100% illumination peaks shortly. Look east near sunset for the best horizon view."
            )
        } else {
            cancelAlarm(context, alarmManager, REQ_FULL_MOON)
        }

        // New Moon dark sky reminder
        if (prefs.newMoonAlerts) {
            val newMillis = AstronomyEngine.findNextPhaseInstant(now, 0.0)
            val trigger = (newMillis - 2L * 3600_000L).coerceAtLeast(now + 60_000L)
            scheduleOneShot(
                context, alarmManager, REQ_NEW_MOON, trigger,
                "New Moon — Dark Sky Window",
                "Moonlight reaches 0%. Tonight offers the darkest skies of the cycle for stargazing."
            )
        } else {
            cancelAlarm(context, alarmManager, REQ_NEW_MOON)
        }

        // Next Lunar Eclipse alert
        if (prefs.lunarEclipseAlerts) {
            val nextEclipse = AstronomyEngine.getKnownLunarEclipses().firstOrNull { it.timestampMillis > now }
            if (nextEclipse != null) {
                val trigger = (nextEclipse.timestampMillis - 3L * 3600_000L).coerceAtLeast(now + 60_000L)
                scheduleOneShot(
                    context, alarmManager, REQ_ECLIPSE, trigger,
                    nextEclipse.title,
                    "Eclipse begins in a few hours. Open LUNAR for exact local stage times."
                )
            }
        } else {
            cancelAlarm(context, alarmManager, REQ_ECLIPSE)
        }

        // Moonrise alert
        if (prefs.moonriseAlerts) {
            val snap = AstronomyEngine.getLunarSnapshot(now, location)
            val rise = snap.moonriseMillis
            if (rise != null && rise > now + 20 * 60_000L) {
                scheduleOneShot(
                    context, alarmManager, REQ_MOONRISE, rise - 15 * 60_000L,
                    "Moonrise in 15 Minutes",
                    "${snap.phaseType.displayName} (${snap.illuminationPercent.roundToInt()}%) climbs above your eastern horizon soon."
                )
            }
        } else {
            cancelAlarm(context, alarmManager, REQ_MOONRISE)
        }

        // Quiet daily lunar update (next evening in 24h via battery-friendly inexact alarm)
        if (prefs.dailyMoonUpdate) {
            scheduleOneShot(
                context, alarmManager, REQ_DAILY, now + 24L * 3600_000L,
                "Your Moon Tonight",
                "Check tonight's lunar illumination, twilight window, and visible constellations in LUNAR."
            )
        } else {
            cancelAlarm(context, alarmManager, REQ_DAILY)
        }
    }

    private fun scheduleOneShot(
        context: Context,
        alarmManager: AlarmManager,
        requestCode: Int,
        triggerAtMillis: Long,
        title: String,
        body: String
    ) {
        val intent = Intent(context, LunarNotificationReceiver::class.java).apply {
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_BODY, body)
            putExtra(EXTRA_NOTIF_ID, requestCode)
        }
        val pi = PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi)
    }

    private fun cancelAlarm(context: Context, alarmManager: AlarmManager, requestCode: Int) {
        val intent = Intent(context, LunarNotificationReceiver::class.java)
        val pi = PendingIntent.getBroadcast(
            context, requestCode, intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pi != null) {
            alarmManager.cancel(pi)
        }
    }

    fun sendNotificationNow(context: Context, id: Int, title: String, body: String): Boolean {
        if (!hasNotificationPermission(context)) return false
        createNotificationChannel(context)
        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val contentPi = PendingIntent.getActivity(
            context, id, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_lunar_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentPi)
            .setAutoCancel(true)
            .build()

        return try {
            NotificationManagerCompat.from(context).notify(id, notification)
            true
        } catch (_: SecurityException) {
            false
        }
    }
}

class LunarNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra(LunarNotificationManager.EXTRA_TITLE) ?: "LUNAR Astronomy Alert"
        val body = intent.getStringExtra(LunarNotificationManager.EXTRA_BODY) ?: "Look up at tonight's Moon."
        val id = intent.getIntExtra(LunarNotificationManager.EXTRA_NOTIF_ID, 4099)
        LunarNotificationManager.sendNotificationNow(context, id, title, body)
    }
}
