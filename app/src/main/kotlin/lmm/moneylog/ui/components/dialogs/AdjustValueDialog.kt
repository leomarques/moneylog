package lmm.moneylog.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.category.list.model.CategoryModel

@Composable
fun AdjustValueDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int?) -> Unit,
    title: String,
    description: String,
    inputLabel: String,
    modifier: Modifier = Modifier,
    categories: List<CategoryModel> = emptyList(),
    showCategorySelector: Boolean = false
) {
    var valueText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<CategoryModel?>(null) }
    var showCategorySelectorDialog by remember { mutableStateOf(false) }

    if (showCategorySelectorDialog) {
        CategorySelectorDialog(
            categories = categories,
            selectedCategory = selectedCategory,
            onDismiss = { showCategorySelectorDialog = false },
            onCategorySelect = {
                selectedCategory = it
                showCategorySelectorDialog = false
            }
        )
    }

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
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                StateTextField(
                    value = valueText,
                    title = inputLabel,
                    keyboardType = KeyboardType.Decimal,
                    leadingIcon = { ValueIcon() },
                    onValueChange = { valueText = it },
                    getFocus = true
                )

                if (showCategorySelector) {
                    // Category selector
                    Text(
                        text = stringResource(lmm.moneylog.ui.R.string.ui_label_category),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp)
                                ).clickable { showCategorySelectorDialog = true }
                                .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (selectedCategory != null) {
                            Surface(
                                modifier = Modifier.size(24.dp),
                                shape = CircleShape,
                                color = selectedCategory!!.color
                            ) {}

                            Text(
                                text = selectedCategory!!.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.category_select),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.action_cancel))
                    }

                    TextButton(
                        onClick = {
                            if (valueText.isNotBlank()) {
                                onConfirm(valueText.trim(), selectedCategory?.id)
                            }
                        },
                        enabled = valueText.isNotBlank()
                    ) {
                        Text(stringResource(R.string.action_ok))
                    }
                }
            }
        }
    }
}

@Composable
private fun CategorySelectorDialog(
    categories: List<CategoryModel>,
    selectedCategory: CategoryModel?,
    onDismiss: () -> Unit,
    onCategorySelect: (CategoryModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.category_select),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (categories.isEmpty()) {
                    Text(
                        text = stringResource(R.string.empty_categories_not_found),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { category ->
                            CategorySelectorItem(
                                name = category.name,
                                color = category.color,
                                isSelected = category.id == selectedCategory?.id,
                                onClick = { onCategorySelect(category) }
                            )
                        }
                    }
                }

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.action_cancel))
                    }
                }
            }
        }
    }
}

@Composable
private fun CategorySelectorItem(
    name: String,
    color: androidx.compose.ui.graphics.Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color =
                        if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                    shape = RoundedCornerShape(8.dp)
                ).clickable(onClick = onClick)
                .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.size(24.dp),
            shape = CircleShape,
            color = color
        ) {}

        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            color =
                if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
        )
    }
}

@Preview
@Composable
private fun AdjustValueDialogPreview() {
    AdjustValueDialog(
        onDismiss = {},
        onConfirm = { _, _ -> },
        title = "Adjust Balance",
        description = "Enter the new balance value",
        inputLabel = "New balance"
    )
}
