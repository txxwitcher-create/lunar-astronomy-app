package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.astronomy.*
import com.example.data.LunarContentCatalog
import com.example.localization.LocalLunarStrings
import com.example.ui.components.*
import com.example.ui.viewmodel.LunarUiState

@Composable
fun LearnLibraryScreen(
    uiState: LunarUiState,
    onBack: () -> Unit,
    onOpenArticle: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    var selectedCategory by remember { mutableStateOf<LearnCategory?>(null) }
    val articles = remember(selectedCategory) {
        if (selectedCategory == null) LunarContentCatalog.learnArticles
        else LunarContentCatalog.learnArticles.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = strings.learnTitle, onBack = onBack)

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text(strings.all) }
                )
            }
            items(LearnCategory.entries) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                    label = { Text(cat.title) }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(articles, key = { it.id }) { article ->
                LunarSurfaceContainer(
                    onClick = { onOpenArticle(article.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("learn_article_${article.id}")
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = article.imageUrl,
                            fallbackType = article.fallbackVisualType,
                            contentDescription = article.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC04060B))))
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LunarPillBadge(
                                text = article.category.title.uppercase(),
                                containerColor = Color(0xBB04060B),
                                contentColor = Color(0xFFDCE7FF)
                            )
                            LunarPillBadge(
                                text = "${article.readTimeMinutes} MIN READ",
                                containerColor = Color(0xAA04060B),
                                contentColor = Color(0xFFB8C4DC)
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(article.title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = article.introduction,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun ArticleDetailScreen(
    articleId: String,
    uiState: LunarUiState,
    onBack: () -> Unit,
    onToggleFavorite: (String) -> Unit,
    onOpenRelatedArticle: (String) -> Unit
) {
    val article = remember(articleId) {
        LunarContentCatalog.learnArticles.find { it.id == articleId } ?: LunarContentCatalog.learnArticles.first()
    }
    val isFav = article.id in uiState.settings.favoriteArticleIds

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(
            title = article.category.title,
            onBack = onBack,
            isFavorite = isFav,
            onToggleFavorite = { onToggleFavorite(article.id) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = article.imageUrl,
                        fallbackType = article.fallbackVisualType,
                        contentDescription = article.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.background)))
                    )
                    Text(
                        text = "Credit: ${article.imageCredit}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFC4CFE6),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(14.dp)
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        LunarPillBadge(text = article.category.title.uppercase())
                        LunarPillBadge(text = "${article.readTimeMinutes} MIN READ")
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(article.title, style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = article.introduction,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f)
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    LunarHairlineDivider()
                }
            }

            items(article.sections) { sec ->
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    Text(sec.heading, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(sec.body, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f))
                }
            }

            item {
                LunarSurfaceContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text("KEY SCIENTIFIC FACTS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        article.keyFacts.forEach { fact ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text("•", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end = 8.dp))
                                Text(fact, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            val related = LunarContentCatalog.learnArticles.filter { it.id in article.relatedArticleIds }
            if (related.isNotEmpty()) {
                item {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)) {
                        Text("RELATED ARTICLES", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        related.forEach { rel ->
                            LunarSurfaceContainer(
                                onClick = { onOpenRelatedArticle(rel.id) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(rel.category.title.uppercase(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(rel.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
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
fun PhotographyHubScreen(
    onBack: () -> Unit,
    onOpenGuide: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    var selectedTrack by remember { mutableStateOf(PhotoGuideTrack.SMARTPHONE) }
    val guides = remember(selectedTrack) {
        LunarContentCatalog.photoGuides.filter { it.track == selectedTrack }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = strings.photoGuideTitle, onBack = onBack)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PhotoGuideTrack.entries.forEach { track ->
                val label = if (track == PhotoGuideTrack.SMARTPHONE) strings.smartphone else strings.cameraDslr
                FilterChip(
                    selected = selectedTrack == track,
                    onClick = { selectedTrack = track },
                    label = { Text(label) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(guides, key = { it.id }) { guide ->
                LunarSurfaceContainer(
                    onClick = { onOpenGuide(guide.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("photo_guide_${guide.id}")
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                    ) {
                        AstronomyArtworkImage(
                            imageUrl = guide.imageUrl,
                            fallbackType = guide.fallbackVisualType,
                            contentDescription = guide.title,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC04060B))))
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LunarPillBadge(text = guide.track.label.uppercase(), containerColor = Color(0xBB04060B), contentColor = Color(0xFFDCE7FF))
                            LunarPillBadge(text = guide.difficulty.uppercase(), containerColor = Color(0xAA04060B), contentColor = Color(0xFFB8C4DC))
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(guide.title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(guide.summary, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun PhotoGuideDetailScreen(
    guideId: String,
    onBack: () -> Unit
) {
    val guide = remember(guideId) {
        LunarContentCatalog.photoGuides.find { it.id == guideId } ?: LunarContentCatalog.photoGuides.first()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LunarDetailTopBar(title = guide.track.label, onBack = onBack)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 48.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                ) {
                    AstronomyArtworkImage(
                        imageUrl = guide.imageUrl,
                        fallbackType = guide.fallbackVisualType,
                        contentDescription = guide.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.background)))
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        LunarPillBadge(text = guide.track.label.uppercase())
                        LunarPillBadge(text = guide.difficulty.uppercase())
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(guide.title, style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(guide.summary, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)) {
                    Text("RECOMMENDED SETTINGS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    LunarSurfaceContainer(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            guide.settings.forEachIndexed { idx, item ->
                                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(item.name, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                                        Text(item.value, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                                    }
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(item.explanation, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                if (idx < guide.settings.lastIndex) {
                                    LunarHairlineDivider(modifier = Modifier.padding(vertical = 4.dp))
                                }
                            }
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                    Text("STEP-BY-STEP INSTRUCTIONS", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, letterSpacing = 1.4.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    guide.steps.forEachIndexed { index, step ->
                        Row(modifier = Modifier.padding(vertical = 6.dp)) {
                            Text(
                                text = "${index + 1}.",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.width(24.dp)
                            )
                            Text(step, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                    Text("COMMON MISTAKES TO AVOID", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.tertiary, letterSpacing = 1.4.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    guide.commonMistakes.forEach { mistake ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("•", color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.padding(end = 8.dp))
                            Text(mistake, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }

            item {
                LunarSurfaceContainer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("EXPECTED RESULT", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(guide.expectedResult, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
