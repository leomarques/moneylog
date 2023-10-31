package lmm.moneylog.ui.features.transaction.gettransactions

import android.annotation.SuppressLint
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.ui.theme.DarkRed
import lmm.moneylog.ui.theme.Size

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GetTransactionsList(
    list: List<TransactionModel>,
    filter: MutableState<String>,
    onItemClick: (Int) -> Unit
) {
    val grouped = list
        .filter { transaction ->
            transaction.description
                .startsWith(
                    prefix = filter.value,
                    ignoreCase = true
                )
        }
        .reversed()
        .groupBy { it.date }

    LazyColumn(
        Modifier.background(
            color = MaterialTheme.colorScheme.inverseOnSurface,
            shape = RoundedCornerShape(20.dp)
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
                        modifier = Modifier
                            .padding(vertical = Size.SmallSpaceSize)
                            .padding(start = Size.DefaultSpaceSize),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(transactions) { transaction ->
                GetTransactionsItem(
                    transaction = transaction,
                    onItemClick = onItemClick,
                    color = transaction.categoryColor
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun GetTransactionsListPreview() {
    GetTransactionsList(
        list = listOf(
            TransactionModel(
                value = "Nome",
                isIncome = true,
                description = "Desc",
                account = "A",
                category = "B",
                date = "01/01/2001",
                id = 0,
                categoryColor = DarkRed
            ),
            TransactionModel(
                value = "Nome",
                isIncome = true,
                description = "Desc",
                account = "A",
                category = "B",
                date = "01/01/2001",
                id = 0,
                categoryColor = DarkRed
            ),
            TransactionModel(
                value = "Nome",
                isIncome = true,
                description = "Desc",
                account = "A",
                category = "B",
                date = "01/01/2001",
                id = 0,
                categoryColor = DarkRed
            )
        ),
        filter = mutableStateOf(""),
        onItemClick = {}
    )
}
