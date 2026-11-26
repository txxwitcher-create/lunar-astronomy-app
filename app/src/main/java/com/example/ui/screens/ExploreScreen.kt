package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.data.LunarContentCatalog
import com.example.localization.LocalLunarStrings
import com.example.ui.components.*
import com.example.ui.viewmodel.LunarUiState
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ExploreScreen(
    uiState: LunarUiState,
    onOpenFeatureDetail: (String) -> Unit,
    onOpenAllPhases: () -> Unit,
    onOpenLearn: () -> Unit,
    onOpenPhotography: () -> Unit,
    onOpenWallpapers: () -> Unit,
    onOpenPlanets: () -> Unit = {},
    onOpenConstellations: () -> Unit = {},
    onOpenDeepSpace: () -> Unit = {},
    onOpenSpaceMissions: () -> Unit = {},
    onOpenEvents: () -> Unit = {}
) {
    val strings = LocalLunarStrings.current
    var scale by remember { mutableFloatStateOf(1f) }
    var rotationDeg by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var showLabels by remember { mutableStateOf(true) }
    var selectedFeature by remember { mutableStateOf(LunarContentCatalog.lunarFeatures.first()) }
    var selectedFilter by remember { mutableStateOf<LunarFeatureType?>(null) }

    val filteredFeatures = remember(selectedFilter) {
        if (selectedFilter == null) LunarContentCatalog.lunarFeatures
        else LunarContentCatalog.lunarFeatures.filter { it.type == selectedFilter }
    }

    fun focusOnFeature(feature: LunarFeature, mapSizePx: Float) {
        selectedFeature = feature
        scale = 2.0f
        rotationDeg = 0f
        val dx = (0.5f - feature.mapX) * mapSizePx * scale
        val dy = (0.5f - feature.mapY) * mapSizePx * scale
        offset = Offset(dx.coerceIn(-mapSizePx * 0.7f, mapSizePx * 0.7f), dy.coerceIn(-mapSizePx * 0.7f, mapSizePx * 0.7f))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 110.dp)
    ) {
        item(key = "explore_header") {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .displayCutoutPadding()
                    .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 14.dp)
            ) {
                Text(
                    text = strings.navExplore.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 2.sp),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = strings.exploreTitle,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = strings.exploreSubtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // INTERACTIVE LUNAR SURFACE VIEWPORT
        item(key = "interactive_lunar_map") {
            var viewportPx by remember { mutableFloatStateOf(900f) }

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(Color(0xFF020409))
                        .border(0.75.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.7f), MaterialTheme.shapes.extraLarge)
                        .clipToBounds()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, rotation ->
                                val newScale = (scale * zoom).coerceIn(1f, 3.6f)
                                scale = newScale
                                rotationDeg = (rotationDeg + rotation) % 360f
                                val maxPan = (size.width * (newScale - 1f) * 0.55f).coerceAtLeast(80f)
                                offset = Offset(
                                    (offset.x + pan.x).coerceIn(-maxPan, maxPan),
                                    (offset.y + pan.y).coerceIn(-maxPan, maxPan)
                                )
                            }
                        }
                        .testTag("interactive_moon_viewport"),
                    contentAlignment = Alignment.Center
                ) {
                    val boxSizePx = constraints.maxWidth.toFloat()
                    LaunchedEffect(boxSizePx) { viewportPx = boxSizePx }

                    // Zoomable, Pannable, Rotatable Moon Disc
                    Box(
                        modifier = Modifier
                            .fillMaxSize(0.92f)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                rotationZ = rotationDeg,
                                translationX = offset.x,
                                translationY = offset.y
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        RealisticMoonDisc(
                            phaseFraction = 0.5, // Full near-side illumination for clear selenography
                            sizeDp = 340.dp,
                            includeOuterHalo = false,
                            contentDescription = "Interactive near-side Moon map",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Scientifically positioned surface markers transformed cleanly in sync with pan/zoom/rotate
                    if (showLabels) {
                        val discPx = boxSizePx * 0.92f
                        val rad = Math.toRadians(rotationDeg.toDouble())
                        val cosR = cos(rad).toFloat()
                        val sinR = sin(rad).toFloat()

                        LunarContentCatalog.lunarFeatures.forEach { feature ->
                            val localX = (feature.mapX - 0.5f) * discPx * scale
                            val localY = (feature.mapY - 0.5f) * discPx * scale
                            val rotX = localX * cosR - localY * sinR
                            val rotY = localX * sinR + localY * cosR
                            val screenX = rotX + offset.x
                            val screenY = rotY + offset.y
                            val isSelected = selectedFeature.id == feature.id

                            Box(
                                modifier = Modifier
                                    .offset { IntOffset(screenX.roundToInt(), screenY.roundToInt()) }
                                    .clickable { selectedFeature = feature }
                                    .testTag("moon_marker_${feature.id}"),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(50))
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.92f)
                                            else Color(0xBB050812)
                                        )
                                        .border(
                                            width = if (isSelected) 1.5.dp else 0.75.dp,
                                            color = if (isSelected) Color.White else Color(0x88DCE6FA),
                                            shape = RoundedCornerShape(50)
                                        )
                                        .padding(horizontal = 7.dp, vertical = 3.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(if (isSelected) Color(0xFF04060B) else Color(0xFF8AAEFF))
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = feature.name.substringBefore(" (").substringBefore(" Crater"),
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.5.sp),
                                        color = if (isSelected) Color(0xFF04060B) else Color(0xFFF1F5FF),
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    // Overlay map toolbar controls
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            SmallMapControlChip(
                                icon = if (showLabels) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                label = if (showLabels) strings.labelsOn else strings.labelsOff,
                                onClick = { showLabels = !showLabels },
                                testTagName = "explore_toggle_labels"
                            )
                            SmallMapControlChip(
                                icon = Icons.Rounded.RotateRight,
                                label = strings.rotate,
                                onClick = { rotationDeg = (rotationDeg + 30f) % 360f },
                                testTagName = "explore_rotate_button"
                            )
                        }
                        SmallMapControlChip(
                            icon = Icons.Rounded.RestartAlt,
                            label = strings.reset,
                            onClick = {
                                scale = 1f
                                rotationDeg = 0f
                                offset = Offset.Zero
                            },
                            testTagName = "explore_reset_view"
                        )
                    }

                    // Zoom level pill
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                    ) {
                        LunarPillBadge(
                            text = String.format("%.1f×", scale),
                            containerColor = Color(0xAA04060B),
                            contentColor = Color(0xFFE6ECF9)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Selected Feature Live Inspector Bar
                LunarSurfaceContainer(
                    onClick = { onOpenFeatureDetail(selectedFeature.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("explore_selected_inspector")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                LunarPillBadge(text = selectedFeature.type.label.uppercase())
                                Text(
                                    text = selectedFeature.coordinates,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = selectedFeature.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = selectedFeature.summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        FilledTonalIconButton(
                            onClick = { onOpenFeatureDetail(selectedFeature.id) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = "Open feature details")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Focus chips for instant camera jump on map
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(LunarContentCatalog.lunarFeatures, key = { "focus_${it.id}" }) { feat ->
                        FilterChip(
                            selected = selectedFeature.id == feat.id,
                            onClick = { focusOnFeature(feat, viewportPx) },
                            label = { Text(feat.name.substringBefore(" ("), maxLines = 1) }
                        )
                    }
                }
            }
        }

        // SCIENTIFIC NOTE: MARIA VS OCEANS
        item(key = "maria_vs_oceans") {
            LunarSurfaceContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "SELENOGRAPHY NOTE",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 1.4.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Lunar Maria Are Volcanic Plains, Not Oceans",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Early observers named the Moon's dark lowlands 'maria' (Latin for seas) believing they held water. In reality, lunar maria are solidified ancient flood-basalt lava plains that filled giant impact basins 3–4 billion years ago.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // EXPANDED ASTRONOMY ENCYCLOPEDIA CATEGORIES
        item(key = "explore_cosmos_categories") {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
                LunarSectionHeader(eyebrow = strings.astronomyKnowledge, title = strings.deepSpace)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickGatewayChip(Icons.Outlined.Public, strings.planets, onOpenPlanets, "explore_planets_btn", Modifier.weight(1f))
                    QuickGatewayChip(Icons.Outlined.AutoAwesome, strings.constellations, onOpenConstellations, "explore_constellations_btn", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickGatewayChip(Icons.Outlined.BlurOn, strings.deepSpace, onOpenDeepSpace, "explore_deepspace_btn", Modifier.weight(1f))
                    QuickGatewayChip(Icons.Outlined.RocketLaunch, strings.spaceMissions, onOpenSpaceMissions, "explore_missions_btn", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickGatewayChip(Icons.Outlined.Brightness3, strings.eclipses, onOpenEvents, "explore_eclipses_btn", Modifier.weight(1f))
                    QuickGatewayChip(Icons.Outlined.Brightness2, strings.homePhaseGuide, onOpenAllPhases, "explore_phases_btn", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickGatewayChip(Icons.Outlined.MenuBook, strings.learnTitle, onOpenLearn, "explore_learn_btn", Modifier.weight(1f))
                    QuickGatewayChip(Icons.Outlined.PhotoCamera, strings.homePhotography, onOpenPhotography, "explore_photo_btn", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                QuickGatewayChip(Icons.Outlined.Wallpaper, strings.wallpapersTitle, onOpenWallpapers, "explore_wp_btn", Modifier.fillMaxWidth())
            }
        }

        // FILTERABLE COMPLETE 7-CATEGORY MOON ATLAS
        item(key = "features_catalog_header") {
            Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 10.dp)) {
                LunarSectionHeader(eyebrow = strings.moonAtlas, title = strings.exploreTitle)
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = selectedFilter == null,
                            onClick = { selectedFilter = null },
                            label = { Text("${strings.all} (${LunarContentCatalog.lunarFeatures.size})") }
                        )
                    }
                    items(LunarFeatureType.entries) { type ->
                        val count = LunarContentCatalog.lunarFeatures.count { it.type == type }
                        if (count > 0) {
                            val localizedLabel = when (type) {
                                LunarFeatureType.CRATER -> strings.craters
                                LunarFeatureType.MARE -> strings.lunarMaria
                                LunarFeatureType.MOUNTAIN -> strings.mountainsValleys
                                LunarFeatureType.LUNAR_POLE -> strings.lunarPoles
                                LunarFeatureType.RILLE_CHANNEL -> strings.rillesChannels
                                LunarFeatureType.LANDING_SITE -> strings.landingSites
                                LunarFeatureType.LUNAR_GEOLOGY -> strings.lunarGeology
                                LunarFeatureType.OCEANUS -> type.label
                            }
                            FilterChip(
                                selected = selectedFilter == type,
                                onClick = { selectedFilter = if (selectedFilter == type) null else type },
                                label = { Text("$localizedLabel ($count)") }
                            )
                        }
                    }
                }
            }
        }

        items(filteredFeatures, key = { it.id }) { feature ->
            LunarSurfaceContainer(
                onClick = { onOpenFeatureDetail(feature.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(66.dp)
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = feature.imageUrl,
                            fallbackType = feature.fallbackVisualType,
                            contentDescription = feature.name,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${feature.type.label.uppercase()} • ${feature.coordinates}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = feature.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Ø ${formatDistance(feature.diameterKm, uiState.settings.distanceUnit)} • ${feature.ageEstimate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SmallMapControlChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    testTagName: String
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xCC060912))
            .border(0.75.dp, Color(0x44DCE6FA), RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 7.dp)
            .testTag(testTagName),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(icon, contentDescription = label, tint = Color(0xFFF0F4FC), modifier = Modifier.size(15.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color(0xFFF0F4FC))
    }
}

@Composable
fun FeatureDetailScreen(
    featureId: String,
    uiState: LunarUiState,
    onBack: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onOpenRelatedFeature: (String) -> Unit
) {
    val feature = remember(featureId) {
        LunarContentCatalog.lunarFeatures.find { it.id == featureId } ?: LunarContentCatalog.lunarFeatures.first()
    }
    val isFav = feature.id in uiState.settings.favoriteFeatureIds

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(
            title = feature.name,
            onBack = onBack,
            isFavorite = isFav,
            onToggleFavorite = { onToggleFavorite(feature.id) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = feature.imageUrl,
                        fallbackType = feature.fallbackVisualType,
                        contentDescription = feature.name,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.background)))
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        LunarPillBadge(text = feature.type.label.uppercase())
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(feature.name, style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onBackground)
                        Text(feature.latinMeaning, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    LunarHairlineDivider()
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TelemetryStatBlock("Coordinates", feature.coordinates, modifier = Modifier.weight(1f))
                        TelemetryStatBlock("Diameter", formatDistance(feature.diameterKm, uiState.settings.distanceUnit), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TelemetryStatBlock("Estimated Age", feature.ageEstimate, modifier = Modifier.weight(1f))
                        TelemetryStatBlock("Best Viewing", feature.bestViewingPhase.substringBefore(";"), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    LunarHairlineDivider()
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DetailSectionBlock("Formation & Geology", feature.formation)
                    DetailSectionBlock("Key Characteristics", feature.characteristics)
                    DetailSectionBlock("Scientific Significance", feature.scientificSignificance)
                    DetailSectionBlock("Exploration History", feature.explorationHistory)

                    if (feature.relatedMissions.isNotEmpty()) {
                        Column {
                            Text("RELATED MISSIONS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(8.dp))
                            feature.relatedMissions.forEach { m ->
                                Text("•  $m", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }
                    }

                    val related = LunarContentCatalog.lunarFeatures.filter { it.id in feature.relatedFeatureIds }
                    if (related.isNotEmpty()) {
                        Column {
                            Text("RELATED LUNAR FEATURES", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                items(related) { rel ->
                                    LunarSurfaceContainer(
                                        onClick = { onOpenRelatedFeature(rel.id) },
                                        modifier = Modifier.width(180.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(14.dp)) {
                                            Text(rel.type.label.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                            Spacer(modifier = Modifier.height(3.dp))
                                            Text(rel.name, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                                            Text(rel.coordinates, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailSectionBlock(title: String, body: String) {
    Column {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.4.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
        )
    }
}

@Composable
fun PhasesOverviewScreen(
    currentPhase: LunarPhaseType,
    onBack: () -> Unit,
    onSelectPhase: (LunarPhaseType) -> Unit
) {
    val strings = LocalLunarStrings.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = strings.homePhaseGuide, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "The Moon does not generate its own visible light—every phase is sculpted by changing Sun–Earth–Moon geometry across the 29.53-day synodic cycle.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
            items(LunarPhaseType.entries, key = { it.name }) { phase ->
                val isCurrent = phase == currentPhase
                val detail = LunarContentCatalog.phaseDetails[phase]
                val fraction = phase.nominalAgeDays / AstronomyEngine.SYNODIC_MONTH_DAYS

                LunarSurfaceContainer(
                    onClick = { onSelectPhase(phase) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("phase_item_${phase.name}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RealisticMoonDisc(
                            phaseFraction = fraction,
                            sizeDp = 62.dp,
                            includeOuterHalo = false,
                            modifier = Modifier.size(62.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = strings.phaseName(phase),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (isCurrent) {
                                    LunarPillBadge(text = strings.today.uppercase())
                                }
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "${phase.nominalIllumination}% Illumination • Day ${String.format("%.1f", phase.nominalAgeDays)}",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = detail?.headline ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun PhaseDetailScreen(
    phase: LunarPhaseType,
    uiState: LunarUiState,
    onBack: () -> Unit,
    onNavigatePhase: (LunarPhaseType) -> Unit
) {
    val strings = LocalLunarStrings.current
    val content = LunarContentCatalog.phaseDetails[phase] ?: return
    val isCurrentPhase = uiState.currentSnapshot.phaseType == phase
    val fraction = if (isCurrentPhase) uiState.currentSnapshot.phaseFraction else (phase.nominalAgeDays / AstronomyEngine.SYNODIC_MONTH_DAYS)
    val illum = if (isCurrentPhase) uiState.currentSnapshot.illuminationPercent.roundToInt() else phase.nominalIllumination
    val ageDays = if (isCurrentPhase) uiState.currentSnapshot.lunarAgeDays else phase.nominalAgeDays
    val statusText = when (phase.isWaxing) {
        true -> strings.waxing
        false -> strings.waning
        null -> if (phase == LunarPhaseType.FULL_MOON) strings.phaseFullMoon else strings.phaseNewMoon
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = strings.phaseName(phase), onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RealisticMoonDisc(
                        phaseFraction = fraction,
                        sizeDp = 220.dp,
                        includeOuterHalo = true,
                        contentDescription = strings.phaseName(phase),
                        modifier = Modifier.size(220.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(strings.phaseName(phase), style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(content.headline, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }
            }

            item {
                Column {
                    LunarHairlineDivider()
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TelemetryStatBlock("Illumination", "$illum%", modifier = Modifier.weight(1f))
                        TelemetryStatBlock("Lunar Age", String.format("%.1f days", ageDays), modifier = Modifier.weight(1f))
                        TelemetryStatBlock("Cycle Trend", statusText.substringBefore(" "), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    if (isCurrentPhase) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TelemetryStatBlock("Today Rise", formatInstantTime(uiState.currentSnapshot.moonriseMillis, uiState.settings.use24HourTime), modifier = Modifier.weight(1f))
                            TelemetryStatBlock("Today Set", formatInstantTime(uiState.currentSnapshot.moonsetMillis, uiState.settings.use24HourTime), modifier = Modifier.weight(1f))
                            TelemetryStatBlock("Duration", "29.53d cycle", modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }
                    LunarHairlineDivider()
                }
            }

            item { DetailSectionBlock("Scientific Overview", content.explanation) }
            item { DetailSectionBlock("What It Looks Like From Earth", content.appearanceFromEarth) }
            item { DetailSectionBlock("Why This Phase Occurs", content.whyItOccurs) }
            item { DetailSectionBlock("Duration & Typical Rise / Set", "${content.durationInfo}\n\n${content.typicalRiseSet}") }

            item {
                Column {
                    Text("OBSERVATION TIPS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    content.observationTips.forEach { tip ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("•", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end = 8.dp))
                            Text(tip, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }

            item {
                LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("PHOTOGRAPHY HINT", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(content.photographyHint, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }

            // Previous / Next Phase Navigation
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 36.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigatePhase(phase.previous()) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("phase_prev_button")
                    ) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(phase.previous().shortName, maxLines = 1)
                    }
                    Button(
                        onClick = { onNavigatePhase(phase.next()) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("phase_next_button")
                    ) {
                        Text(phase.next().shortName, maxLines = 1)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
