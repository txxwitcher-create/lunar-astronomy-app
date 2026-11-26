package com.example.localization

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.example.MainActivity
import com.example.widget.refreshAllLunarWidgets
import java.util.Locale

data class LunarLanguage(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val isRtl: Boolean = false,
    val usesNonLatinScript: Boolean = false
) {
    fun toLocale(): Locale {
        return if (code.contains("-")) {
            val parts = code.split("-")
            Locale.Builder().setLanguage(parts[0]).setRegion(parts[1]).build()
        } else {
            Locale.Builder().setLanguage(code).build()
        }
    }
}

object LunarLocaleManager {

    private const val PREFS_NAME = "lunar_locale_prefs"
    private const val KEY_LANGUAGE_CODE = "selected_language_code"
    const val DEFAULT_LANGUAGE_CODE = "en"

    val supportedLanguages: List<LunarLanguage> = listOf(
        LunarLanguage("en", "English", "English"),
        LunarLanguage("zh-CN", "中文 (简体)", "Simplified Chinese", usesNonLatinScript = true),
        LunarLanguage("es", "Español", "Spanish"),
        LunarLanguage("hi", "हिन्दी", "Hindi", usesNonLatinScript = true),
        LunarLanguage("ar", "العربية", "Arabic", isRtl = true, usesNonLatinScript = true),
        LunarLanguage("bn", "বাংলা", "Bengali", usesNonLatinScript = true),
        LunarLanguage("pt", "Português", "Portuguese"),
        LunarLanguage("ru", "Русский", "Russian", usesNonLatinScript = true),
        LunarLanguage("ja", "日本語", "Japanese", usesNonLatinScript = true),
        LunarLanguage("pa", "ਪੰਜਾਬੀ", "Punjabi", usesNonLatinScript = true),
        LunarLanguage("de", "Deutsch", "German"),
        LunarLanguage("jv", "Basa Jawa", "Javanese"),
        LunarLanguage("ko", "한국어", "Korean", usesNonLatinScript = true),
        LunarLanguage("fr", "Français", "French"),
        LunarLanguage("te", "తెలుగు", "Telugu", usesNonLatinScript = true),
        LunarLanguage("mr", "मराठी", "Marathi", usesNonLatinScript = true),
        LunarLanguage("tr", "Türkçe", "Turkish"),
        LunarLanguage("ta", "தமிழ்", "Tamil", usesNonLatinScript = true),
        LunarLanguage("vi", "Tiếng Việt", "Vietnamese"),
        LunarLanguage("ur", "اردو", "Urdu", isRtl = true, usesNonLatinScript = true),
        LunarLanguage("id", "Bahasa Indonesia", "Indonesian"),
        LunarLanguage("it", "Italiano", "Italian"),
        LunarLanguage("th", "ไทย", "Thai", usesNonLatinScript = true),
        LunarLanguage("gu", "ગુજરાતી", "Gujarati", usesNonLatinScript = true),
        LunarLanguage("fa", "فارسی", "Persian", isRtl = true, usesNonLatinScript = true),
        LunarLanguage("pl", "Polski", "Polish"),
        LunarLanguage("uk", "Українська", "Ukrainian", usesNonLatinScript = true),
        LunarLanguage("ml", "മലയാളം", "Malayalam", usesNonLatinScript = true),
        LunarLanguage("kn", "ಕನ್ನಡ", "Kannada", usesNonLatinScript = true),
        LunarLanguage("my", "မြန်မာဘာသာ", "Burmese", usesNonLatinScript = true),
        LunarLanguage("sw", "Kiswahili", "Swahili"),
        LunarLanguage("yo", "Yorùbá", "Yoruba"),
        LunarLanguage("ha", "Hausa", "Hausa"),
        LunarLanguage("am", "አማርኛ", "Amharic", usesNonLatinScript = true),
        LunarLanguage("ne", "नेपाली", "Nepali", usesNonLatinScript = true),
        LunarLanguage("si", "සිංහල", "Sinhala", usesNonLatinScript = true),
        LunarLanguage("km", "ភាសាខ្មែរ", "Khmer", usesNonLatinScript = true),
        LunarLanguage("lo", "ລາວ", "Lao", usesNonLatinScript = true),
        LunarLanguage("fil", "Filipino", "Filipino"),
        LunarLanguage("nl", "Nederlands", "Dutch"),
        LunarLanguage("el", "Ελληνικά", "Greek", usesNonLatinScript = true),
        LunarLanguage("he", "עברית", "Hebrew", isRtl = true, usesNonLatinScript = true),
        LunarLanguage("ro", "Română", "Romanian"),
        LunarLanguage("cs", "Čeština", "Czech"),
        LunarLanguage("hu", "Magyar", "Hungarian"),
        LunarLanguage("sv", "Svenska", "Swedish"),
        LunarLanguage("da", "Dansk", "Danish"),
        LunarLanguage("fi", "Suomi", "Finnish"),
        LunarLanguage("nb", "Norsk", "Norwegian"),
        LunarLanguage("sr", "Српски", "Serbian", usesNonLatinScript = true)
    )

    private val languageMap: Map<String, LunarLanguage> = supportedLanguages.associateBy { it.code }

    fun findLanguage(code: String?): LunarLanguage {
        return languageMap[code] ?: languageMap[DEFAULT_LANGUAGE_CODE]!!
    }

    /**
     * Reads saved language synchronously. Always defaults to English ("en") on first install,
     * never auto-switching to device locale unless the user explicitly selected a language.
     */
    fun getSavedLanguageCode(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getString(KEY_LANGUAGE_CODE, DEFAULT_LANGUAGE_CODE)
        return if (saved != null && languageMap.containsKey(saved)) saved else DEFAULT_LANGUAGE_CODE
    }

    fun getSavedLanguage(context: Context): LunarLanguage {
        return findLanguage(getSavedLanguageCode(context))
    }

    /**
     * Synchronously commits the chosen language to persistent storage so it is guaranteed
     * to be flushed to disk before restarting the app.
     */
    fun saveLanguageCodeSynchronously(context: Context, code: String): Boolean {
        val validCode = findLanguage(code).code
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.edit().putString(KEY_LANGUAGE_CODE, validCode).commit()
    }

    /**
     * Applies saved locale to Android Context before any UI is inflated or rendered,
     * preventing any flash of the previous language on startup.
     */
    fun wrapContext(base: Context): Context {
        val lang = getSavedLanguage(base)
        val locale = lang.toLocale()
        Locale.setDefault(locale)

        val config = Configuration(base.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocales(LocaleList(locale))
        }
        return base.createConfigurationContext(config)
    }

    /**
     * Safely commits the new language, updates widgets, closes the current task,
     * and cleanly relaunches LUNAR from its launcher entry point without restart loops.
     */
    fun commitLanguageAndRestart(activity: Activity, newLanguageCode: String) {
        val current = getSavedLanguageCode(activity)
        val target = findLanguage(newLanguageCode)
        if (current == target.code) return

        // 1. Synchronous disk commit BEFORE relaunch
        saveLanguageCodeSynchronously(activity.applicationContext, target.code)

        // 2. Apply JVM default locale immediately
        Locale.setDefault(target.toLocale())

        // 3. Refresh Home Screen Widgets for the new language
        runCatching { refreshAllLunarWidgets(activity.applicationContext) }

        // 4. Clean single-instance relaunch from launcher entry point
        val restartIntent = Intent(activity.applicationContext, MainActivity::class.java).apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
            putExtra("lunar_restarted_for_locale", true)
        }
        activity.startActivity(restartIntent)
        activity.overridePendingTransition(0, 0)
        activity.finishAffinity()
    }
}
