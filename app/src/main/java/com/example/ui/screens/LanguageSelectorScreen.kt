package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.localization.LocalLunarStrings
import com.example.localization.LunarLanguage
import com.example.localization.LunarLocaleManager
import com.example.ui.components.LunarDetailTopBar
import com.example.ui.components.LunarHairlineDivider
import com.example.ui.components.LunarPillBadge

/**
 * Searchable 50-language selector screen with native & English names, active indicator,
 * and mandatory localized Restart Confirmation Dialog before committing and restarting.
 */
@Composable
fun LanguageSelectorScreen(
    activeLanguageCode: String,
    onBack: () -> Unit,
    onConfirmLanguageChange: (String) -> Unit
) {
    val strings = LocalLunarStrings.current
    var searchQuery by remember { mutableStateOf("") }
    var pendingLanguage by remember { mutableStateOf<LunarLanguage?>(null) }

    val filteredLanguages = remember(searchQuery) {
        val q = searchQuery.trim().lowercase()
        if (q.isEmpty()) {
            LunarLocaleManager.supportedLanguages
        } else {
            LunarLocaleManager.supportedLanguages.filter { lang ->
                lang.nativeName.lowercase().contains(q) ||
                    lang.englishName.lowercase().contains(q) ||
                    lang.code.lowercase().contains(q)
            }
        }
    }

    // Mandatory Confirmation Dialog (displayed strictly in the CURRENT language)
    pendingLanguage?.let { target ->
        AlertDialog(
            onDismissRequest = { pendingLanguage = null },
            title = {
                Text(
                    text = strings.restartDialogTitle,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "${target.nativeName} (${target.englishName})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = strings.restartDialogMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val chosenCode = target.code
                        pendingLanguage = null
                        onConfirmLanguageChange(chosenCode)
                    },
                    modifier = Modifier.testTag("language_confirm_restart_button")
                ) {
                    Text(strings.restartDialogConfirm)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { pendingLanguage = null },
                    modifier = Modifier.testTag("language_cancel_restart_button")
                ) {
                    Text(strings.cancel)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("language_selector_screen")
    ) {
        LunarDetailTopBar(title = strings.languageLabel, onBack = onBack)

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(strings.languageSearchHint) },
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
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .testTag("language_search_input")
        )

        if (filteredLanguages.isEmpty()) {
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 40.dp)
            ) {
                items(filteredLanguages, key = { it.code }) { language ->
                    val isSelected = language.code == activeLanguageCode
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                else MaterialTheme.colorScheme.background
                            )
                            .then(
                                if (isSelected) Modifier.border(
                                    0.75.dp,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.45f),
                                    RoundedCornerShape(12.dp)
                                ) else Modifier
                            )
                            .clickable {
                                // Selecting the currently active language does nothing (no prompt, no restart)
                                if (!isSelected) {
                                    pendingLanguage = language
                                }
                            }
                            .minimumInteractiveComponentSize()
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                            .testTag("language_option_${language.code}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = language.nativeName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                if (language.isRtl) {
                                    LunarPillBadge(text = "RTL")
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${language.englishName}  •  ${language.code}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    LunarHairlineDivider(modifier = Modifier.padding(horizontal = 8.dp))
                }
            }
        }
    }
}
