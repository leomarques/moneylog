package lmm.moneylog.ui.features.transaction.list.view

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
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.features.transaction.list.model.TransactionModel
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.darkRed
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.neutralColor
import lmm.moneylog.ui.theme.outcome

@Composable
fun TransactionsListItem(
    transaction: TransactionModel,
    onItemClick: (Int) -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(Size.TwoLinesListItemHeight)
                .padding(
                    vertical = Size.SmallSpaceSize,
                    horizontal = Size.DefaultSpaceSize
                )
                .clickable { onItemClick(transaction.id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(transaction) {
            Row(
                Modifier
                    .weight(0.75f)
                    .padding(end = Size.SmallSpaceSize),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyCircle(
                    color = color,
                    letters = category
                )

                Column(Modifier.padding(start = Size.DefaultSpaceSize)) {
                    Text(
                        text =
                            description.ifEmpty {
                                stringResource(R.string.no_description)
                            },
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        color =
                            if (description.isEmpty()) {
                                neutralColor
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                    )

                    val categoryString =
                        category.ifEmpty {
                            stringResource(R.string.no_category)
                        }

                    val sb = StringBuilder(categoryString)
                    if (creditCard.isNotEmpty()) {
                        sb.append(" | $creditCard")
                    }
                    if (account.isNotEmpty()) {
                        sb.append(" | $account")
                    }

                    Text(
                        text = sb.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                modifier =
                    Modifier
                        .weight(0.25f)
                        .padding(start = Size.DefaultSpaceSize),
                text = value,
                color = if (isIncome) income else outcome,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionsListItemPreview() {
    Column {
        TransactionsListItem(
            transaction = transactionModelPreview1,
            onItemClick = {},
            color = darkRed
        )
        TransactionsListItem(
            transaction = transactionModelPreview2,
            onItemClick = {},
            color = darkRed
        )
        TransactionsListItem(
            transaction = transactionModelPreview3,
            onItemClick = {},
            color = darkRed
        )
    }
}
