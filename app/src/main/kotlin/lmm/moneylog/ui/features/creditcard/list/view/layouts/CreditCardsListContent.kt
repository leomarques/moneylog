package lmm.moneylog.ui.features.creditcard.list.view.layouts

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.misc.LazyList
import lmm.moneylog.ui.features.creditcard.list.model.CreditCardModel
import lmm.moneylog.ui.theme.neutralColor

@Composable
fun CreditCardsListContent(
    onItemClick: (Int) -> Unit,
    list: List<CreditCardModel>,
    modifier: Modifier = Modifier
) {
    if (list.isNotEmpty()) {
        LazyList(modifier) {
            itemsIndexed(list) { index, category ->
                CreditCardsListItem(
                    id = category.id,
                    name = category.name,
                    color = category.color,
                    showDivider = index != list.size - 1,
                    onItemClick = onItemClick
                )
            }
        }
    } else {
        EmptyState(
            modifier = modifier,
            title = stringResource(R.string.empty_creditcards_title),
            description = stringResource(R.string.empty_creditcards_desc)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreditCardsListContentPreview() {
    CreditCardsListContent(
        onItemClick = { },
        list =
            listOf(
                CreditCardModel(
                    id = 1,
                    name = "CreditCardy 1",
                    color = neutralColor
                ),
                CreditCardModel(
                    id = 2,
                    name = "CreditCard 2",
                    color = neutralColor
                ),
                CreditCardModel(
                    id = 3,
                    name = "CreditCard 3",
                    color = neutralColor
                )
            )
    )
}
