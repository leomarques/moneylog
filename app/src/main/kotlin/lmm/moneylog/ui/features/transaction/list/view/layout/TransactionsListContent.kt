package lmm.moneylog.ui.features.transaction.list.view.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.features.transaction.list.model.TransactionModel
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.darkRed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsListContent(
    list: List<TransactionModel>,
    filter: String,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit
) {
    val grouped =
        list
            .filter { transaction ->
                transaction.description.startsWith(
                    prefix = filter,
                    ignoreCase = true
                )
            }
            .reversed()
            .groupBy { it.date }

    LazyColumn(
        modifier.background(
            color = MaterialTheme.colorScheme.inverseOnSurface,
            shape = RoundedCornerShape(Size.ListRoundedCornerSize)
        )
    ) {
        grouped.forEach { (date, transactions) ->
            stickyHeader {
                Surface(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = date,
                        modifier =
                            Modifier
                                .padding(vertical = Size.SmallSpaceSize)
                                .padding(start = Size.DefaultSpaceSize),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(transactions) { transaction ->
                TransactionsListItem(
                    transaction = transaction,
                    onItemClick = onItemClick,
                    color = transaction.categoryColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun TransactionsListContentPreview() {
    TransactionsListContent(
        list =
            listOf(
                TransactionModel(
                    value = "Nome",
                    isIncome = true,
                    description = "Desc",
                    account = "A",
                    category = "B",
                    date = "01/01/2001",
                    id = 0,
                    categoryColor = darkRed
                ),
                TransactionModel(
                    value = "Nome",
                    isIncome = true,
                    description = "Desc",
                    account = "A",
                    category = "B",
                    date = "01/01/2001",
                    id = 0,
                    categoryColor = darkRed
                ),
                TransactionModel(
                    value = "Nome",
                    isIncome = true,
                    description = "Desc",
                    account = "A",
                    category = "B",
                    date = "01/01/2001",
                    id = 0,
                    categoryColor = darkRed
                )
            ),
        filter = "",
        onItemClick = {}
    )
}
