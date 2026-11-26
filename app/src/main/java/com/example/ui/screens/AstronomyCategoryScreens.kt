package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.data.AstronomyEncyclopediaCatalog
import com.example.localization.LocalLunarStrings
import com.example.ui.components.*

// =========================================================================
// PLANETS — ALL 8 WORLDS OF THE SOLAR SYSTEM
// =========================================================================

@Composable
fun PlanetsScreen(
    settings: AppSettings,
    onBack: () -> Unit,
    onOpenPlanet: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    val planets = remember { AstronomyEncyclopediaCatalog.planets }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("planets_screen")
    ) {
        LunarDetailTopBar(title = strings.planets, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "Explore all eight planets of our Solar System with real NASA spacecraft photography and verified planetary telemetry.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            items(planets, key = { it.id }) { planet ->
                LunarSurfaceContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("planet_card_${planet.id}"),
                    onClick = { onOpenPlanet(planet.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(78.dp)
                                .clip(RoundedCornerShape(14.dp))
                        ) {
                            AstronomyArtworkImage(
                                imageUrl = planet.imageUrl,
                                fallbackType = VisualFallbackType.DEEP_SPACE,
                                contentDescription = planet.name,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(planet.accentHex))
                                )
                                Text(
                                    text = "#${planet.orderFromSun} • ${planet.distanceFromSunAu}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = planet.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = planet.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${formatDistance(planet.diameterKm, settings.distanceUnit)} • Moons: ${planet.moonsCount}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetDetailScreen(
    planetId: String,
    settings: AppSettings,
    onBack: () -> Unit
) {
    val planet = remember(planetId) { AstronomyEncyclopediaCatalog.planetById(planetId) }
        ?: AstronomyEncyclopediaCatalog.planets.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("planet_detail_screen")
    ) {
        LunarDetailTopBar(title = planet.name, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 44.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = planet.imageUrl,
                        fallbackType = VisualFallbackType.DEEP_SPACE,
                        contentDescription = planet.name,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, MaterialTheme.colorScheme.background)
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        LunarPillBadge(
                            text = "PLANET #${planet.orderFromSun} FROM THE SUN",
                            contentColor = Color(planet.accentHex),
                            containerColor = Color(planet.accentHex).copy(alpha = 0.16f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = planet.name,
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${planet.subtitle} • ${planet.imageCredit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                LunarSurfaceContainer(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TelemetryStatBlock("Diameter", formatDistance(planet.diameterKm, settings.distanceUnit), modifier = Modifier.weight(1f))
                            TelemetryStatBlock("Distance", planet.distanceFromSunAu, modifier = Modifier.weight(1f))
                        }
                        LunarHairlineDivider()
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TelemetryStatBlock("Rotation Day", planet.rotationPeriod, modifier = Modifier.weight(1f))
                            TelemetryStatBlock("Orbital Year", planet.orbitalPeriod, modifier = Modifier.weight(1f))
                        }
                        LunarHairlineDivider()
                        TelemetryStatBlock("Natural Satellites", planet.moonsCount)
                    }
                }
            }

            item {
                EncyclopediaTextBlock(
                    eyebrow = "ATMOSPHERE",
                    title = "Composition & Weather",
                    body = planet.atmosphere
                )
            }
            item {
                EncyclopediaTextBlock(
                    eyebrow = "GEOLOGY & STRUCTURE",
                    title = "Surface & Interior",
                    body = planet.surface
                )
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "KEY SCIENTIFIC FACTS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.4.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LunarSurfaceContainer {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            planet.facts.forEach { fact ->
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Icon(
                                        imageVector = Icons.Rounded.AutoAwesome,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp).padding(top = 2.dp)
                                    )
                                    Text(
                                        text = fact,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "SPACECRAFT EXPLORATION",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.4.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = planet.missions.joinToString("  •  "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// =========================================================================
// CONSTELLATIONS
// =========================================================================

@Composable
fun ConstellationsScreen(
    onBack: () -> Unit,
    onOpenConstellation: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    val itemsList = remember { AstronomyEncyclopediaCatalog.constellations }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("constellations_screen")
    ) {
        LunarDetailTopBar(title = strings.constellations, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(itemsList, key = { it.id }) { item ->
                LunarSurfaceContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("constellation_card_${item.id}"),
                    onClick = { onOpenConstellation(item.id) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${item.season} • ${item.hemisphere}".uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    letterSpacing = 1.1.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${item.name} — ${item.latinMeaning}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.starPattern,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item.mainStars.take(3).joinToString(" • "),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConstellationDetailScreen(
    constellationId: String,
    onBack: () -> Unit
) {
    val item = remember(constellationId) { AstronomyEncyclopediaCatalog.constellationById(constellationId) }
        ?: AstronomyEncyclopediaCatalog.constellations.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("constellation_detail_screen")
    ) {
        LunarDetailTopBar(title = item.name, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 44.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = item.imageUrl,
                        fallbackType = VisualFallbackType.NIGHT_SKY,
                        contentDescription = item.name,
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
                        LunarPillBadge(text = item.season.uppercase())
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${item.name} (${item.latinMeaning})",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${item.hemisphere} • ${item.imageCredit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                EncyclopediaTextBlock("STAR PATTERN", "Visual Shape", item.starPattern)
            }
            item {
                EncyclopediaTextBlock("SKY NAVIGATION", "How to Locate It", item.howToLocate)
            }
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "PRINCIPAL STARS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.4.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LunarSurfaceContainer {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            item.mainStars.forEach { star ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = star,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                EncyclopediaTextBlock("MYTH & HERITAGE", "Origin Story", item.originStory)
            }
            item {
                EncyclopediaTextBlock("ASTROPHYSICS", "Deep-Sky Science", item.scientificNotes)
            }
        }
    }
}

// =========================================================================
// DEEP SPACE (NEBULAE, GALAXIES, STAR CLUSTERS, BLACK HOLES, SUPERNOVAE)
// =========================================================================

@Composable
fun DeepSpaceScreen(
    onBack: () -> Unit,
    onOpenDeepSpace: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    val objects = remember { AstronomyEncyclopediaCatalog.deepSpaceObjects }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("deep_space_screen")
    ) {
        LunarDetailTopBar(title = strings.deepSpace, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(objects, key = { it.id }) { obj ->
                LunarSurfaceContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("deep_space_card_${obj.id}"),
                    onClick = { onOpenDeepSpace(obj.id) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(175.dp)
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = obj.imageUrl,
                            fallbackType = VisualFallbackType.GALAXY,
                            contentDescription = obj.name,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xD905070E))))
                        )
                        LunarPillBadge(
                            text = obj.catalogId,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(12.dp)
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(14.dp)
                        ) {
                            Text(
                                text = obj.category.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = obj.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${obj.distanceLabel} • ${obj.constellation}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = obj.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeepSpaceDetailScreen(
    objectId: String,
    onBack: () -> Unit
) {
    val obj = remember(objectId) { AstronomyEncyclopediaCatalog.deepSpaceById(objectId) }
        ?: AstronomyEncyclopediaCatalog.deepSpaceObjects.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("deep_space_detail_screen")
    ) {
        LunarDetailTopBar(title = obj.name, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 44.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = obj.imageUrl,
                        fallbackType = VisualFallbackType.GALAXY,
                        contentDescription = obj.name,
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
                        LunarPillBadge(text = "${obj.catalogId} • ${obj.category.uppercase()}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = obj.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = obj.imageCredit,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            item {
                LunarSurfaceContainer(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                        TelemetryStatBlock("Distance", obj.distanceLabel, modifier = Modifier.weight(1f))
                        TelemetryStatBlock("Constellation", obj.constellation, modifier = Modifier.weight(1f))
                    }
                }
            }
            item { EncyclopediaTextBlock("OVERVIEW", "Cosmic Significance", obj.summary) }
            item { EncyclopediaTextBlock("ASTROPHYSICS", "Scientific Detail", obj.scienceDetail) }
            item { EncyclopediaTextBlock("OBSERVING", "How to Observe", obj.observationNotes) }
        }
    }
}

// =========================================================================
// SPACE MISSIONS (APOLLO, VOYAGER, CASSINI, LRO, JWST, CHANDRAYAAN & ARTEMIS)
// =========================================================================

@Composable
fun SpaceMissionsScreen(
    onBack: () -> Unit,
    onOpenMission: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    val missions = remember { AstronomyEncyclopediaCatalog.spaceMissions }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("space_missions_screen")
    ) {
        LunarDetailTopBar(title = strings.spaceMissions, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(missions, key = { it.id }) { mission ->
                LunarSurfaceContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("mission_card_${mission.id}"),
                    onClick = { onOpenMission(mission.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(82.dp)
                                .clip(RoundedCornerShape(14.dp))
                        ) {
                            AstronomyArtworkImage(
                                imageUrl = mission.imageUrl,
                                fallbackType = VisualFallbackType.APOLLO_SURFACE,
                                contentDescription = mission.name,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${mission.year} • ${mission.agency}".uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = mission.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = mission.target,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = mission.summary,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpaceMissionDetailScreen(
    missionId: String,
    onBack: () -> Unit
) {
    val mission = remember(missionId) { AstronomyEncyclopediaCatalog.missionById(missionId) }
        ?: AstronomyEncyclopediaCatalog.spaceMissions.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("space_mission_detail_screen")
    ) {
        LunarDetailTopBar(title = mission.name, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 44.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = mission.imageUrl,
                        fallbackType = VisualFallbackType.APOLLO_SURFACE,
                        contentDescription = mission.name,
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
                        LunarPillBadge(text = "${mission.agency} • ${mission.year}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = mission.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "${mission.era} • ${mission.imageCredit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                LunarSurfaceContainer(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TelemetryStatBlock("Destination / Target", mission.target)
                    }
                }
            }

            item {
                EncyclopediaTextBlock("MISSION BRIEF", "Overview", mission.summary)
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "LANDMARK ACHIEVEMENTS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.4.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LunarSurfaceContainer {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            mission.achievements.forEach { ach ->
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Icon(
                                        imageVector = Icons.Rounded.RocketLaunch,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp).padding(top = 2.dp)
                                    )
                                    Text(
                                        text = ach,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
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
private fun EncyclopediaTextBlock(
    eyebrow: String,
    title: String,
    body: String
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = eyebrow,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 1.4.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
