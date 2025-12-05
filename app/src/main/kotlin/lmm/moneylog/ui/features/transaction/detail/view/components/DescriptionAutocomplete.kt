package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import lmm.moneylog.data.transaction.model.TransactionSuggestion
import lmm.moneylog.ui.extensions.toComposeColor

@Composable
fun DescriptionAutocomplete(
    suggestions: List<TransactionSuggestion>,
    onSuggestionClick: (TransactionSuggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    if (suggestions.isEmpty()) return

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .zIndex(1f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyColumn {
            items(suggestions) { suggestion ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onSuggestionClick(suggestion) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(24.dp)
                                .background(
                                    color = suggestion.categoryColor.toComposeColor(),
                                    shape = CircleShape
                                )
                    )

                    Text(
                        text = suggestion.description,
                        modifier = Modifier.padding(start = 12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
