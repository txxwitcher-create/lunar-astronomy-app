package com.example.ui.screens

import android.app.WallpaperManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.astronomy.WallpaperCategory
import com.example.astronomy.WallpaperItem
import com.example.data.LunarContentCatalog
import com.example.localization.LocalLunarStrings
import com.example.ui.components.*
import com.example.ui.viewmodel.LunarUiState
import kotlinx.coroutines.launch

@Composable
fun WallpapersGalleryScreen(
    uiState: LunarUiState,
    onBack: () -> Unit,
    onOpenWallpaper: (String) -> Unit,
    onToggleFavorite: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    var selectedCategory by remember { mutableStateOf(WallpaperCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }

    val items = remember(selectedCategory, searchQuery) {
        val byCat = if (selectedCategory == WallpaperCategory.ALL) {
            LunarContentCatalog.wallpapers
        } else {
            LunarContentCatalog.wallpapers.filter { it.category == selectedCategory }
        }
        val q = searchQuery.trim().lowercase()
        if (q.isEmpty()) byCat
        else byCat.filter { wp ->
            wp.title.lowercase().contains(q) ||
                wp.collection.lowercase().contains(q) ||
                wp.subtitle.lowercase().contains(q) ||
                wp.viewingAngle.lowercase().contains(q) ||
                wp.lightingMood.lowercase().contains(q)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = strings.wallpapersTitle, onBack = onBack)

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(strings.searchWallpapers) },
            leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = strings.search) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.clear)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp)
                .testTag("wallpaper_search_input")
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(WallpaperCategory.entries) { cat ->
                val count = if (cat == WallpaperCategory.ALL) LunarContentCatalog.wallpapers.size
                else LunarContentCatalog.wallpapers.count { it.category == cat }
                if (count > 0) {
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(if (cat == WallpaperCategory.ALL) strings.all else cat.label) }
                    )
                }
            }
        }

        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = strings.noResults,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 156.dp),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 36.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(items, key = { _, wp -> wp.id }) { _, wp ->
                    val isFav = wp.id in uiState.settings.favoriteWallpaperIds
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.68f)
                            .clip(MaterialTheme.shapes.large)
                            .border(0.75.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), MaterialTheme.shapes.large)
                            .clickable { onOpenWallpaper(wp.id) }
                            .testTag("wallpaper_card_${wp.id}")
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = wp.imageUrl,
                            fallbackType = wp.fallbackVisualType,
                            phaseFractionOverride = wp.phaseFractionOverride,
                            contentDescription = wp.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(Color(0x2204060B), Color.Transparent, Color(0xE604060B))))
                        )
                        IconButton(
                            onClick = { onToggleFavorite(wp.id) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(6.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0x8804060B))
                        ) {
                            Icon(
                                imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = strings.favorites,
                                tint = if (isFav) Color(0xFFFF7A90) else Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        ) {
                            Text(wp.collection.uppercase(), style = MaterialTheme.typography.labelSmall, color = Color(0xFF9FB8F5), maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = wp.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = Color(0xFFF5F7FC),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text("${wp.viewingAngle} • ${wp.lightingMood}", style = MaterialTheme.typography.labelSmall, color = Color(0xFFB4C0D6), maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WallpaperFullscreenScreen(
    initialWallpaperId: String,
    uiState: LunarUiState,
    onBack: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onSaveWallpaper: (WallpaperItem) -> Unit,
    onShareWallpaper: (WallpaperItem) -> Unit,
    onApplyWallpaper: (WallpaperItem, Int) -> Unit
) {
    val strings = LocalLunarStrings.current
    val allWallpapers = LunarContentCatalog.wallpapers
    val startIndex = remember(initialWallpaperId) {
        allWallpapers.indexOfFirst { it.id == initialWallpaperId }.coerceAtLeast(0)
    }
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { allWallpapers.size })
    val scope = rememberCoroutineScope()
    val currentItem = allWallpapers[pagerState.currentPage]
    val isFav = currentItem.id in uiState.settings.favoriteWallpaperIds
    var showApplySheet by remember { mutableStateOf(false) }

    // Pinch-to-zoom state (resets cleanly whenever user swipes to another wallpaper)
    var zoomScale by remember(pagerState.currentPage) { mutableFloatStateOf(1f) }
    var panOffset by remember(pagerState.currentPage) { mutableStateOf(Offset.Zero) }

    val relatedWallpapers = remember(currentItem.id) {
        allWallpapers.filter { it.id != currentItem.id && (it.category == currentItem.category || it.lightingMood == currentItem.lightingMood) }
            .ifEmpty { allWallpapers.filter { it.id != currentItem.id } }
            .take(5)
    }

    if (showApplySheet) {
        AlertDialog(
            onDismissRequest = { showApplySheet = false },
            title = { Text(strings.setWallpaper) },
            text = { Text("${currentItem.title} (${uiState.settings.wallpaperQuality.label})") },
            confirmButton = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            showApplySheet = false
                            onApplyWallpaper(currentItem, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text(strings.homeAndLock) }
                    OutlinedButton(
                        onClick = {
                            showApplySheet = false
                            onApplyWallpaper(currentItem, WallpaperManager.FLAG_SYSTEM)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text(strings.homeOnly) }
                    OutlinedButton(
                        onClick = {
                            showApplySheet = false
                            onApplyWallpaper(currentItem, WallpaperManager.FLAG_LOCK)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text(strings.lockOnly) }
                }
            },
            dismissButton = {
                TextButton(onClick = { showApplySheet = false }) { Text(strings.cancel) }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = zoomScale <= 1.05f,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val item = allWallpapers[page]
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(page) {
                        detectTapGestures(
                            onDoubleTap = {
                                if (zoomScale > 1.1f) {
                                    zoomScale = 1f
                                    panOffset = Offset.Zero
                                } else {
                                    zoomScale = 2.2f
                                }
                            }
                        )
                    }
                    .pointerInput(page) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            val newScale = (zoomScale * zoom).coerceIn(1f, 3.5f)
                            zoomScale = newScale
                            if (newScale > 1.05f) {
                                val maxPanX = size.width * (newScale - 1f) * 0.5f
                                val maxPanY = size.height * (newScale - 1f) * 0.5f
                                panOffset = Offset(
                                    (panOffset.x + pan.x).coerceIn(-maxPanX, maxPanX),
                                    (panOffset.y + pan.y).coerceIn(-maxPanY, maxPanY)
                                )
                            } else {
                                panOffset = Offset.Zero
                            }
                        }
                    }
            ) {
                AstronomyArtworkImage(
                    imageUrl = item.imageUrl,
                    fallbackType = item.fallbackVisualType,
                    phaseFractionOverride = item.phaseFractionOverride,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = if (page == pagerState.currentPage) zoomScale else 1f
                            scaleY = if (page == pagerState.currentPage) zoomScale else 1f
                            translationX = if (page == pagerState.currentPage) panOffset.x else 0f
                            translationY = if (page == pagerState.currentPage) panOffset.y else 0f
                        }
                )
            }
        }

        // Top bar overlay (safe below display cutout)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .displayCutoutPadding()
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0x8804060B))
            ) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = strings.back, tint = Color.White)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LunarPillBadge(
                    text = "${pagerState.currentPage + 1} / ${allWallpapers.size}",
                    containerColor = Color(0x9904060B),
                    contentColor = Color.White
                )
                if (zoomScale > 1.05f) {
                    LunarPillBadge(
                        text = String.format("%.1f×", zoomScale),
                        containerColor = Color(0xAA1D3566),
                        contentColor = Color.White
                    )
                }
            }
            IconButton(
                onClick = { onToggleFavorite(currentItem.id) },
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0x8804060B))
            ) {
                Icon(
                    imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = strings.favorites,
                    tint = if (isFav) Color(0xFFFF7A90) else Color.White
                )
            }
        }

        // Bottom metadata, related thumbnails & action bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xDE04060B), Color(0xFA04060B))))
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LunarPillBadge(text = currentItem.viewingAngle)
                LunarPillBadge(text = currentItem.lightingMood)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(currentItem.title, style = MaterialTheme.typography.headlineSmall, color = Color.White)
            Text(
                text = "${currentItem.resolution} • ${currentItem.credit}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFC2CDE3)
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Related Wallpapers quick strip
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(relatedWallpapers, key = { "rel_${it.id}" }) { rel ->
                    Box(
                        modifier = Modifier
                            .size(width = 58.dp, height = 42.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(0.75.dp, Color(0x66DCE3F0), RoundedCornerShape(8.dp))
                            .clickable {
                                val idx = allWallpapers.indexOfFirst { it.id == rel.id }
                                if (idx >= 0) {
                                    scope.launch { pagerState.animateScrollToPage(idx) }
                                }
                            }
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = rel.imageUrl,
                            fallbackType = rel.fallbackVisualType,
                            phaseFractionOverride = rel.phaseFractionOverride,
                            contentDescription = rel.title,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { showApplySheet = true },
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp)
                        .testTag("wallpaper_set_btn")
                ) {
                    Icon(Icons.Outlined.Wallpaper, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(strings.setWallpaper)
                }
                FilledTonalIconButton(
                    onClick = { onSaveWallpaper(currentItem) },
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("wallpaper_save_btn")
                ) {
                    Icon(Icons.Outlined.Download, contentDescription = strings.save)
                }
                FilledTonalIconButton(
                    onClick = { onShareWallpaper(currentItem) },
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("wallpaper_share_btn")
                ) {
                    Icon(Icons.Outlined.Share, contentDescription = strings.share)
                }
            }
        }
    }
}

@Composable
fun FavoritesScreen(
    uiState: LunarUiState,
    onBack: () -> Unit,
    onOpenWallpaper: (String) -> Unit,
    onOpenArticle: (String) -> Unit,
    onOpenFeature: (String) -> Unit,
    onOpenEvent: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    val settings = uiState.settings
    val favWallpapers = LunarContentCatalog.wallpapers.filter { it.id in settings.favoriteWallpaperIds }
    val favArticles = LunarContentCatalog.learnArticles.filter { it.id in settings.favoriteArticleIds }
    val favFeatures = LunarContentCatalog.lunarFeatures.filter { it.id in settings.favoriteFeatureIds }
    val favEvents = (uiState.upcomingEvents + com.example.astronomy.AstronomyEngine.getKnownLunarEclipses())
        .distinctBy { it.id }
        .filter { it.id in settings.favoriteEventIds }

    val total = favWallpapers.size + favArticles.size + favFeatures.size + favEvents.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = strings.savedFavorites, onBack = onBack)

        if (total == 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(strings.noFavoritesYet, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Bookmark lunar features, astronomy articles, upcoming eclipses, or wallpapers anywhere in LUNAR to keep them ready here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (favWallpapers.isNotEmpty()) {
                    item { LunarSectionHeader(strings.wallpapersTitle, "${favWallpapers.size}") }
                    items(favWallpapers, key = { "fw_${it.id}" }) { wp ->
                        LunarSurfaceContainer(onClick = { onOpenWallpaper(wp.id) }, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(wp.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                                Text("${wp.collection} • ${wp.resolution}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
                if (favFeatures.isNotEmpty()) {
                    item { LunarSectionHeader(strings.moonAtlas, "${favFeatures.size}") }
                    items(favFeatures, key = { "ff_${it.id}" }) { feat ->
                        LunarSurfaceContainer(onClick = { onOpenFeature(feat.id) }, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(feat.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                                Text("${feat.type.label} • ${feat.coordinates}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
                if (favArticles.isNotEmpty()) {
                    item { LunarSectionHeader(strings.learnTitle, "${favArticles.size}") }
                    items(favArticles, key = { "fa_${it.id}" }) { art ->
                        LunarSurfaceContainer(onClick = { onOpenArticle(art.id) }, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(art.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                                Text("${art.category.title} • ${art.readTimeMinutes} min", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
                if (favEvents.isNotEmpty()) {
                    item { LunarSectionHeader(strings.eclipses, "${favEvents.size}") }
                    items(favEvents, key = { "fe_${it.id}" }) { ev ->
                        LunarSurfaceContainer(onClick = { onOpenEvent(ev.id) }, modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(ev.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                                Text(ev.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}
