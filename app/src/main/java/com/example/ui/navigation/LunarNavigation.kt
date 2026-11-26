package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.astronomy.LunarEventType
import com.example.astronomy.LunarPhaseType
import com.example.localization.LocalLunarStrings
import com.example.ui.screens.*
import com.example.ui.viewmodel.LunarViewModel
import java.time.LocalDate

object LunarRoutes {
    const val HOME = "home"
    const val EXPLORE = "explore"
    const val CALENDAR = "calendar"
    const val TONIGHT = "tonight"

    const val PHASES_OVERVIEW = "phases_overview"
    const val PHASE_DETAIL = "phase_detail/{phaseName}"
    const val FEATURE_DETAIL = "feature_detail/{featureId}"
    const val CALENDAR_DAY = "calendar_day/{isoDate}"
    const val EVENTS_LIST = "events_list"
    const val EVENT_DETAIL = "event_detail/{eventId}"
    const val LEARN_LIBRARY = "learn_library"
    const val ARTICLE_DETAIL = "article_detail/{articleId}"
    const val PHOTOGRAPHY_HUB = "photography_hub"
    const val PHOTO_GUIDE_DETAIL = "photo_guide_detail/{guideId}"
    const val WALLPAPERS_GALLERY = "wallpapers_gallery"
    const val WALLPAPER_FULLSCREEN = "wallpaper_fullscreen/{wallpaperId}"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val LANGUAGE_SELECTOR = "language_selector"
    const val REPLAY_INTRO = "replay_intro"
    const val NOTIFICATIONS_SETTINGS = "notifications_settings"
    const val WIDGET_SETTINGS = "widget_settings"
    const val ABOUT_LUNAR = "about_lunar"

    // Expanded Astronomy Encyclopedia routes
    const val PLANETS = "planets"
    const val PLANET_DETAIL = "planet_detail/{planetId}"
    const val CONSTELLATIONS = "constellations"
    const val CONSTELLATION_DETAIL = "constellation_detail/{constellationId}"
    const val DEEP_SPACE = "deep_space"
    const val DEEP_SPACE_DETAIL = "deep_space_detail/{objectId}"
    const val SPACE_MISSIONS = "space_missions"
    const val SPACE_MISSION_DETAIL = "space_mission_detail/{missionId}"

    fun phaseDetail(phase: LunarPhaseType) = "phase_detail/${phase.name}"
    fun featureDetail(id: String) = "feature_detail/$id"
    fun calendarDay(date: LocalDate) = "calendar_day/$date"
    fun eventDetail(id: String) = "event_detail/$id"
    fun articleDetail(id: String) = "article_detail/$id"
    fun photoGuideDetail(id: String) = "photo_guide_detail/$id"
    fun wallpaperFullscreen(id: String) = "wallpaper_fullscreen/$id"
    fun planetDetail(id: String) = "planet_detail/$id"
    fun constellationDetail(id: String) = "constellation_detail/$id"
    fun deepSpaceDetail(id: String) = "deep_space_detail/$id"
    fun spaceMissionDetail(id: String) = "space_mission_detail/$id"
}

private data class PrimaryNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun LunarAppRoot(
    viewModel: LunarViewModel,
    activeLanguageCode: String,
    restartedForLocale: Boolean = false,
    onConfirmLanguageChange: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val strings = LocalLunarStrings.current

    // Hold a pure OLED black frame for the <30ms DataStore read so the intro never flashes
    // on returning launches or after a language restart.
    if (!uiState.isSettingsLoaded) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
        return
    }

    // Play cinematic 10-second opening animation automatically on first launch only (never loops)
    val shouldPlayLaunchIntro = !restartedForLocale &&
        uiState.settings.introEnabled &&
        !uiState.settings.hasSeenIntro

    if (shouldPlayLaunchIntro) {
        val introStyle = remember(uiState.settings.introStyle, uiState.settings.introVariantIndex) {
            viewModel.resolveIntroStyle()
        }
        CinematicIntroScreen(
            style = introStyle,
            reducedMotion = uiState.settings.reducedMotion,
            onFinished = { viewModel.markIntroCompleted() }
        )
        return
    }

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val primaryDestinations = remember(strings) {
        listOf(
            PrimaryNavItem(LunarRoutes.HOME, strings.navHome, Icons.Filled.Brightness2, Icons.Outlined.Brightness2, "nav_tab_home"),
            PrimaryNavItem(LunarRoutes.EXPLORE, strings.navExplore, Icons.Filled.Public, Icons.Outlined.Public, "nav_tab_explore"),
            PrimaryNavItem(LunarRoutes.CALENDAR, strings.navCalendar, Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth, "nav_tab_calendar"),
            PrimaryNavItem(LunarRoutes.TONIGHT, strings.navTonight, Icons.Filled.NightsStay, Icons.Outlined.NightsStay, "nav_tab_tonight")
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showPrimaryNav = currentRoute in primaryDestinations.map { it.route }

    LaunchedEffect(uiState.statusMessage) {
        uiState.statusMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
            viewModel.consumeMessage()
        }
    }

    fun navigateTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (showPrimaryNav && !useNavigationRail) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
                        tonalElevation = 0.dp,
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        primaryDestinations.forEach { dest ->
                            val selected = currentRoute == dest.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateTopLevel(dest.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                        contentDescription = dest.label
                                    )
                                },
                                label = {
                                    Text(
                                        text = dest.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        maxLines = 1
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.testTag(dest.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (showPrimaryNav && useNavigationRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .displayCutoutPadding()
                            .navigationBarsPadding()
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        primaryDestinations.forEach { dest ->
                            val selected = currentRoute == dest.route
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTopLevel(dest.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                        contentDescription = dest.label
                                    )
                                },
                                label = {
                                    Text(
                                        text = dest.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        maxLines = 1
                                    )
                                },
                                modifier = Modifier.testTag(dest.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = LunarRoutes.HOME,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(LunarRoutes.HOME) {
                        HomeScreen(
                            uiState = uiState,
                            onOpenCalendar = { navigateTopLevel(LunarRoutes.CALENDAR) },
                            onOpenSettings = { navController.navigate(LunarRoutes.SETTINGS) },
                            onOpenPhaseDetail = { navController.navigate(LunarRoutes.phaseDetail(it)) },
                            onOpenAllPhases = { navController.navigate(LunarRoutes.PHASES_OVERVIEW) },
                            onOpenExplore = { navigateTopLevel(LunarRoutes.EXPLORE) },
                            onOpenFeatureDetail = { navController.navigate(LunarRoutes.featureDetail(it)) },
                            onOpenTonight = { navigateTopLevel(LunarRoutes.TONIGHT) },
                            onOpenEvents = { navController.navigate(LunarRoutes.EVENTS_LIST) },
                            onOpenEventDetail = { navController.navigate(LunarRoutes.eventDetail(it)) },
                            onOpenLearn = { navController.navigate(LunarRoutes.LEARN_LIBRARY) },
                            onOpenArticleDetail = { navController.navigate(LunarRoutes.articleDetail(it)) },
                            onOpenPhotography = { navController.navigate(LunarRoutes.PHOTOGRAPHY_HUB) },
                            onOpenWallpapers = { navController.navigate(LunarRoutes.WALLPAPERS_GALLERY) },
                            onOpenFavorites = { navController.navigate(LunarRoutes.FAVORITES) }
                        )
                    }

                    composable(LunarRoutes.EXPLORE) {
                        ExploreScreen(
                            uiState = uiState,
                            onOpenFeatureDetail = { navController.navigate(LunarRoutes.featureDetail(it)) },
                            onOpenAllPhases = { navController.navigate(LunarRoutes.PHASES_OVERVIEW) },
                            onOpenLearn = { navController.navigate(LunarRoutes.LEARN_LIBRARY) },
                            onOpenPhotography = { navController.navigate(LunarRoutes.PHOTOGRAPHY_HUB) },
                            onOpenWallpapers = { navController.navigate(LunarRoutes.WALLPAPERS_GALLERY) },
                            onOpenPlanets = { navController.navigate(LunarRoutes.PLANETS) },
                            onOpenConstellations = { navController.navigate(LunarRoutes.CONSTELLATIONS) },
                            onOpenDeepSpace = { navController.navigate(LunarRoutes.DEEP_SPACE) },
                            onOpenSpaceMissions = { navController.navigate(LunarRoutes.SPACE_MISSIONS) },
                            onOpenEvents = { navController.navigate(LunarRoutes.EVENTS_LIST) }
                        )
                    }

                    composable(LunarRoutes.CALENDAR) {
                        CalendarScreen(
                            uiState = uiState,
                            onPreviousMonth = { viewModel.previousMonth() },
                            onNextMonth = { viewModel.nextMonth() },
                            onJumpToday = { viewModel.jumpToTodayMonth() },
                            onSelectDate = { navController.navigate(LunarRoutes.calendarDay(it)) },
                            onOpenEvents = { navController.navigate(LunarRoutes.EVENTS_LIST) }
                        )
                    }

                    composable(LunarRoutes.TONIGHT) {
                        TonightScreen(
                            uiState = uiState,
                            hasLocationPermission = viewModel.hasLocationPermission(),
                            onRequestGpsLocation = { viewModel.detectGpsLocation() },
                            onSelectManualLocation = { viewModel.selectManualLocation(it) },
                            onSearchCities = { viewModel.searchCities(it) },
                            onRefreshWeather = { viewModel.refreshWeather() }
                        )
                    }

                    composable(LunarRoutes.PHASES_OVERVIEW) {
                        PhasesOverviewScreen(
                            currentPhase = uiState.currentSnapshot.phaseType,
                            onBack = { navController.popBackStack() },
                            onSelectPhase = { navController.navigate(LunarRoutes.phaseDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.PHASE_DETAIL,
                        arguments = listOf(navArgument("phaseName") { type = NavType.StringType })
                    ) { entry ->
                        val name = entry.arguments?.getString("phaseName") ?: LunarPhaseType.FULL_MOON.name
                        val phase = runCatching { LunarPhaseType.valueOf(name) }.getOrDefault(LunarPhaseType.FULL_MOON)
                        PhaseDetailScreen(
                            phase = phase,
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onNavigatePhase = { next ->
                                navController.navigate(LunarRoutes.phaseDetail(next)) {
                                    popUpTo(LunarRoutes.PHASE_DETAIL) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(
                        route = LunarRoutes.FEATURE_DETAIL,
                        arguments = listOf(navArgument("featureId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("featureId") ?: "tycho"
                        FeatureDetailScreen(
                            featureId = id,
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onToggleFavorite = { viewModel.toggleFavoriteFeature(it) },
                            onOpenRelatedFeature = { navController.navigate(LunarRoutes.featureDetail(it)) }
                        )
                    }

                    // Expanded Astronomy Encyclopedia destinations
                    composable(LunarRoutes.PLANETS) {
                        PlanetsScreen(
                            settings = uiState.settings,
                            onBack = { navController.popBackStack() },
                            onOpenPlanet = { navController.navigate(LunarRoutes.planetDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.PLANET_DETAIL,
                        arguments = listOf(navArgument("planetId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("planetId") ?: "earth"
                        PlanetDetailScreen(
                            planetId = id,
                            settings = uiState.settings,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(LunarRoutes.CONSTELLATIONS) {
                        ConstellationsScreen(
                            onBack = { navController.popBackStack() },
                            onOpenConstellation = { navController.navigate(LunarRoutes.constellationDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.CONSTELLATION_DETAIL,
                        arguments = listOf(navArgument("constellationId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("constellationId") ?: "orion"
                        ConstellationDetailScreen(
                            constellationId = id,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(LunarRoutes.DEEP_SPACE) {
                        DeepSpaceScreen(
                            onBack = { navController.popBackStack() },
                            onOpenDeepSpace = { navController.navigate(LunarRoutes.deepSpaceDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.DEEP_SPACE_DETAIL,
                        arguments = listOf(navArgument("objectId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("objectId") ?: "m31"
                        DeepSpaceDetailScreen(
                            objectId = id,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(LunarRoutes.SPACE_MISSIONS) {
                        SpaceMissionsScreen(
                            onBack = { navController.popBackStack() },
                            onOpenMission = { navController.navigate(LunarRoutes.spaceMissionDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.SPACE_MISSION_DETAIL,
                        arguments = listOf(navArgument("missionId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("missionId") ?: "apollo11"
                        SpaceMissionDetailScreen(
                            missionId = id,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = LunarRoutes.CALENDAR_DAY,
                        arguments = listOf(navArgument("isoDate") { type = NavType.StringType })
                    ) { entry ->
                        val dateStr = entry.arguments?.getString("isoDate")
                        val date = runCatching { LocalDate.parse(dateStr) }.getOrDefault(LocalDate.now())
                        val snap = remember(date, uiState.settings.location) { viewModel.getSnapshotForDate(date) }
                        CalendarDayDetailScreen(
                            date = date,
                            uiState = uiState,
                            snapshot = snap,
                            onBack = { navController.popBackStack() },
                            onOpenPhaseDetail = { navController.navigate(LunarRoutes.phaseDetail(it)) },
                            onOpenEventDetail = { navController.navigate(LunarRoutes.eventDetail(it)) }
                        )
                    }

                    composable(LunarRoutes.EVENTS_LIST) {
                        EventsListScreen(
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onSelectEvent = { navController.navigate(LunarRoutes.eventDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.EVENT_DETAIL,
                        arguments = listOf(navArgument("eventId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("eventId") ?: ""
                        EventDetailScreen(
                            eventId = id,
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onToggleFavorite = { viewModel.toggleFavoriteEvent(it) },
                            onEnableEventNotification = { ev ->
                                viewModel.updateNotificationPrefs { p ->
                                    when (ev.type) {
                                        LunarEventType.FULL_MOON, LunarEventType.SUPERMOON -> p.copy(fullMoonAlerts = !p.fullMoonAlerts)
                                        LunarEventType.NEW_MOON -> p.copy(newMoonAlerts = !p.newMoonAlerts)
                                        LunarEventType.TOTAL_LUNAR_ECLIPSE,
                                        LunarEventType.PARTIAL_LUNAR_ECLIPSE,
                                        LunarEventType.PENUMBRAL_LUNAR_ECLIPSE -> p.copy(lunarEclipseAlerts = !p.lunarEclipseAlerts)
                                        else -> p.copy(majorEventsAlerts = !p.majorEventsAlerts)
                                    }
                                }
                                viewModel.postMessage("Notification preference updated for ${ev.type.label}")
                            }
                        )
                    }

                    composable(LunarRoutes.LEARN_LIBRARY) {
                        LearnLibraryScreen(
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onOpenArticle = { navController.navigate(LunarRoutes.articleDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.ARTICLE_DETAIL,
                        arguments = listOf(navArgument("articleId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("articleId") ?: ""
                        ArticleDetailScreen(
                            articleId = id,
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onToggleFavorite = { viewModel.toggleFavoriteArticle(it) },
                            onOpenRelatedArticle = { navController.navigate(LunarRoutes.articleDetail(it)) }
                        )
                    }

                    composable(LunarRoutes.PHOTOGRAPHY_HUB) {
                        PhotographyHubScreen(
                            onBack = { navController.popBackStack() },
                            onOpenGuide = { navController.navigate(LunarRoutes.photoGuideDetail(it)) }
                        )
                    }

                    composable(
                        route = LunarRoutes.PHOTO_GUIDE_DETAIL,
                        arguments = listOf(navArgument("guideId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("guideId") ?: ""
                        PhotoGuideDetailScreen(
                            guideId = id,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(LunarRoutes.WALLPAPERS_GALLERY) {
                        WallpapersGalleryScreen(
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onOpenWallpaper = { navController.navigate(LunarRoutes.wallpaperFullscreen(it)) },
                            onToggleFavorite = { viewModel.toggleFavoriteWallpaper(it) }
                        )
                    }

                    composable(
                        route = LunarRoutes.WALLPAPER_FULLSCREEN,
                        arguments = listOf(navArgument("wallpaperId") { type = NavType.StringType })
                    ) { entry ->
                        val id = entry.arguments?.getString("wallpaperId") ?: ""
                        WallpaperFullscreenScreen(
                            initialWallpaperId = id,
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onToggleFavorite = { viewModel.toggleFavoriteWallpaper(it) },
                            onSaveWallpaper = { viewModel.saveWallpaper(it) },
                            onShareWallpaper = { viewModel.shareWallpaper(it) },
                            onApplyWallpaper = { item, flags -> viewModel.applyDeviceWallpaper(item, flags) }
                        )
                    }

                    composable(LunarRoutes.FAVORITES) {
                        FavoritesScreen(
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onOpenWallpaper = { navController.navigate(LunarRoutes.wallpaperFullscreen(it)) },
                            onOpenArticle = { navController.navigate(LunarRoutes.articleDetail(it)) },
                            onOpenFeature = { navController.navigate(LunarRoutes.featureDetail(it)) },
                            onOpenEvent = { navController.navigate(LunarRoutes.eventDetail(it)) }
                        )
                    }

                    composable(LunarRoutes.SETTINGS) {
                        LaunchedEffect(Unit) { viewModel.refreshCacheSize() }
                        SettingsScreen(
                            uiState = uiState,
                            activeLanguageCode = activeLanguageCode,
                            hasLocationPermission = viewModel.hasLocationPermission(),
                            onBack = { navController.popBackStack() },
                            onOpenLanguageSelector = { navController.navigate(LunarRoutes.LANGUAGE_SELECTOR) },
                            onSelectFontStyle = { viewModel.setFontStyle(it) },
                            onToggleIntroEnabled = { viewModel.setIntroEnabled(it) },
                            onReplayIntro = { navController.navigate(LunarRoutes.REPLAY_INTRO) },
                            onSelectIntroStyle = { viewModel.setIntroStyle(it) },
                            onToggleReducedMotion = { viewModel.setReducedMotion(it) },
                            onRequestGpsLocation = { viewModel.detectGpsLocation() },
                            onSelectManualLocation = { viewModel.selectManualLocation(it) },
                            onSearchCities = { viewModel.searchCities(it) },
                            onSelectTheme = { viewModel.setThemeMode(it) },
                            onSelectUnit = { viewModel.setDistanceUnit(it) },
                            onToggle24Hour = { viewModel.setUse24HourTime(it) },
                            onUpdateCalendarPrefs = { i, e, m -> viewModel.setCalendarPrefs(i, e, m) },
                            onUpdateObservationPrefs = { c, r -> viewModel.setObservationPrefs(c, r) },
                            onUpdateWallpaperPrefs = { q, w -> viewModel.setWallpaperPrefs(q, w) },
                            onOpenNotifications = { navController.navigate(LunarRoutes.NOTIFICATIONS_SETTINGS) },
                            onOpenWidgets = { navController.navigate(LunarRoutes.WIDGET_SETTINGS) },
                            onOpenFavorites = { navController.navigate(LunarRoutes.FAVORITES) },
                            onOpenWallpapers = { navController.navigate(LunarRoutes.WALLPAPERS_GALLERY) },
                            onClearCache = { viewModel.clearCachePreservingFavorites() },
                            onOpenAbout = { navController.navigate(LunarRoutes.ABOUT_LUNAR) },
                            onOpenInstagram = { viewModel.openCreatorInstagram(context) },
                            onSendFeedback = { subject -> viewModel.sendFeedbackEmail(context, subject) }
                        )
                    }

                    composable(LunarRoutes.LANGUAGE_SELECTOR) {
                        LanguageSelectorScreen(
                            activeLanguageCode = activeLanguageCode,
                            onBack = { navController.popBackStack() },
                            onConfirmLanguageChange = { newCode ->
                                onConfirmLanguageChange(newCode)
                            }
                        )
                    }

                    composable(LunarRoutes.REPLAY_INTRO) {
                        val replayStyle = remember { viewModel.resolveIntroStyle() }
                        CinematicIntroScreen(
                            style = replayStyle,
                            reducedMotion = uiState.settings.reducedMotion,
                            onFinished = {
                                viewModel.markIntroCompleted()
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(LunarRoutes.NOTIFICATIONS_SETTINGS) {
                        NotificationsSettingsScreen(
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onUpdatePrefs = { viewModel.updateNotificationPrefs(it) },
                            onSendPreview = { viewModel.sendPreviewNotification() }
                        )
                    }

                    composable(LunarRoutes.WIDGET_SETTINGS) {
                        WidgetSettingsScreen(
                            uiState = uiState,
                            onBack = { navController.popBackStack() },
                            onSelectWidgetTheme = { viewModel.setWidgetTheme(it) }
                        )
                    }

                    composable(LunarRoutes.ABOUT_LUNAR) {
                        AboutLunarScreen(
                            onBack = { navController.popBackStack() },
                            onOpenInstagram = { viewModel.openCreatorInstagram(context) }
                        )
                    }
                }
            }
        }
    }
}
