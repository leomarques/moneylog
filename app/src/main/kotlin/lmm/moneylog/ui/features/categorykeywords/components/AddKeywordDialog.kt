package lmm.moneylog.ui.features.categorykeywords.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import lmm.moneylog.R
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.categorykeywords.model.CategoryWithKeywords

@Composable
fun AddKeywordDialog(
    categories: List<CategoryWithKeywords>,
    selectedCategoryId: Int?,
    onCategorySelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    onAddKeyword: (Int, String) -> Unit,
    onAddMultipleKeywords: (Int, List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    var keywordText by remember { mutableStateOf("") }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    val selectedCategory = categories.find { it.id == selectedCategoryId }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.keywords_add),
                    style = MaterialTheme.typography.titleLarge
                )

                // Category selector
                Text(
                    text = stringResource(R.string.common_category),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Box {
                    OutlinedCard(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { showCategoryDropdown = true }
                    ) {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (selectedCategory != null) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(selectedCategory.color)
                                )
                                Text(
                                    text = selectedCategory.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f).padding(start = 12.dp)
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.keywords_select_category),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showCategoryDropdown,
                        onDismissRequest = { showCategoryDropdown = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .size(24.dp)
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(category.color)
                                        )
                                        Text(category.name)
                                    }
                                },
                                onClick = {
                                    onCategorySelected(category.id)
                                    showCategoryDropdown = false
                                }
                            )
                        }
                    }
                }

                StateTextField(
                    value = keywordText,
                    title = stringResource(R.string.keywords_label),
                    keyboardType = KeyboardType.Text,
                    onValueChange = { keywordText = it },
                    getFocus = false
                )

                Text(
                    text = stringResource(R.string.keywords_tip),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.action_cancel))
                    }

                    TextButton(
                        onClick = {
                            if (selectedCategoryId != null && keywordText.isNotBlank()) {
                                // Check if contains comma - if yes, split into multiple
                                if (keywordText.contains(",")) {
                                    val keywords =
                                        keywordText
                                            .split(",")
                                            .map { it.trim() }
                                            .filter { it.isNotBlank() }
                                    if (keywords.isNotEmpty()) {
                                        onAddMultipleKeywords(selectedCategoryId, keywords)
                                    }
                                } else {
                                    onAddKeyword(selectedCategoryId, keywordText.trim())
                                }
                            }
                        },
                        enabled = selectedCategoryId != null && keywordText.isNotBlank()
                    ) {
                        Text(stringResource(R.string.action_add))
                    }
                }
            }
        }
    }
}
