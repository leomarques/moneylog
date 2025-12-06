package lmm.moneylog.ui.features.categorykeywords.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import lmm.moneylog.R
import lmm.moneylog.ui.components.textfields.StateTextField

@Composable
fun AddKeywordDialog(
    categoryName: String,
    onDismiss: () -> Unit,
    onAddKeyword: (String) -> Unit,
    onAddMultipleKeywords: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    var keywordText by remember { mutableStateOf("") }

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

                Text(
                    text = stringResource(R.string.category_label, categoryName),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                StateTextField(
                    value = keywordText,
                    title = stringResource(R.string.keywords_label),
                    keyboardType = KeyboardType.Text,
                    onValueChange = { keywordText = it },
                    getFocus = true
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
                            if (keywordText.isNotBlank()) {
                                // Check if contains comma - if yes, split into multiple
                                if (keywordText.contains(",")) {
                                    val keywords =
                                        keywordText
                                            .split(",")
                                            .map { it.trim() }
                                            .filter { it.isNotBlank() }
                                    if (keywords.isNotEmpty()) {
                                        onAddMultipleKeywords(keywords)
                                    }
                                } else {
                                    onAddKeyword(keywordText.trim())
                                }
                            }
                        },
                        enabled = keywordText.isNotBlank()
                    ) {
                        Text(stringResource(R.string.action_add))
                    }
                }
            }
        }
    }
}
