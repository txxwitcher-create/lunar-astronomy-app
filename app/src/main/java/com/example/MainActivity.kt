package com.example

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.astronomy.LunarThemeMode
import com.example.localization.LunarLocaleManager
import com.example.ui.navigation.LunarAppRoot
import com.example.ui.theme.LunarTheme
import com.example.ui.viewmodel.LunarViewModel

class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LunarLocaleManager.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        applyImmersiveFullscreenMode()

        val activeLanguage = LunarLocaleManager.getSavedLanguage(this)
        val restartedForLocale = intent?.getBooleanExtra("lunar_restarted_for_locale", false) == true

        setContent {
            val lunarViewModel: LunarViewModel = viewModel()
            val uiState by lunarViewModel.uiState.collectAsStateWithLifecycle()
            val themeMode = uiState.settings.themeMode
            val fontStyle = uiState.settings.fontStyle
            val systemDark = isSystemInDarkTheme()
            val useDarkBars = themeMode != LunarThemeMode.SYSTEM || systemDark
            val savedLanguage = remember { activeLanguage }

            LaunchedEffect(useDarkBars) {
                enableEdgeToEdge(
                    statusBarStyle = if (useDarkBars) SystemBarStyle.dark(Color.TRANSPARENT)
                    else SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
                    navigationBarStyle = if (useDarkBars) SystemBarStyle.dark(Color.TRANSPARENT)
                    else SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                )
                applyImmersiveFullscreenMode()
            }

            LunarTheme(
                themeMode = themeMode,
                fontStyle = fontStyle,
                activeLanguage = savedLanguage
            ) {
                LunarAppRoot(
                    viewModel = lunarViewModel,
                    activeLanguageCode = savedLanguage.code,
                    restartedForLocale = restartedForLocale,
                    onConfirmLanguageChange = { newCode ->
                        LunarLocaleManager.commitLanguageAndRestart(this@MainActivity, newCode)
                    }
                )
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            applyImmersiveFullscreenMode()
        }
    }

    /**
     * Hides the Android status bar by default for a clean, edge-to-edge astronomy viewing
     * experience while keeping system navigation usable and allowing transient swipe reveal.
     */
    private fun applyImmersiveFullscreenMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes = window.attributes.apply {
                layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.statusBars())
    }
}
