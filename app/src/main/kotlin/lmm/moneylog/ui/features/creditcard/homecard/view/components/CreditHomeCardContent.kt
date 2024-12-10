package lmm.moneylog.ui.features.creditcard.homecard.view.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.creditcard.homecard.model.CreditCardHomeCardItem
import lmm.moneylog.ui.features.creditcard.homecard.model.mockCards
import lmm.moneylog.ui.features.creditcard.homecard.view.layout.CreditCardsHomeCardItem

@Composable
fun CreditHomeCardContent(
    onClick: (Int) -> Unit,
    cards: List<CreditCardHomeCardItem>,
    hideValues: Boolean,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(cards) { index, item ->
            CreditCardsHomeCardItem(
                id = item.id,
                name = item.name,
                color = item.color.toComposeColor(),
                showDivider = index != cards.size - 1,
                onItemClick = onClick,
                hideValues = hideValues,
                value = item.value
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreditHomeCardContentPreview() {
    CreditHomeCardContent(
        onClick = {},
        cards = mockCards,
        hideValues = false
    )
}
