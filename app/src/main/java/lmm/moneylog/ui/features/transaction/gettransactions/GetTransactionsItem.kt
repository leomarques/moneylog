package lmm.moneylog.ui.features.transaction.gettransactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyCircle
import lmm.moneylog.ui.theme.DarkRed
import lmm.moneylog.ui.theme.SpaceSize
import lmm.moneylog.ui.theme.income

@Composable
fun GetTransactionsItem(
    transaction: TransactionModel,
    onItemClick: (Int) -> Unit,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SpaceSize.TwoLinesListItemHeight)
            .padding(
                vertical = SpaceSize.SmallSpaceSize,
                horizontal = SpaceSize.DefaultSpaceSize
            )
            .clickable { onItemClick(transaction.id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(transaction) {
            Row(
                Modifier
                    .weight(0.75f)
                    .padding(end = SpaceSize.SmallSpaceSize),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyCircle(
                    color = color,
                    letters = category
                )

                Column(Modifier.padding(start = SpaceSize.DefaultSpaceSize)) {
                    Text(
                        text = description.ifEmpty {
                            stringResource(R.string.gettransactions_nodescription)
                        },
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        color = if (description.isEmpty()) {
                            Color.Gray
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )

                    Text(
                        text = "$category | $account",
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                modifier = Modifier
                    .weight(0.25f)
                    .padding(start = SpaceSize.DefaultSpaceSize),
                text = value,
                color =
                if (isIncome) {
                    income
                } else {
                    color
                },
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun GetTransactionsItemPreview() {
    GetTransactionsItem(
        transaction = TransactionModel(
            value = "Nome",
            isIncome = true,
            description = "Desc",
            account = "A",
            category = "B",
            date = "01/01/2001",
            id = 0,
            categoryColor = DarkRed
        ),
        onItemClick = {},
        color = DarkRed
    )
}
