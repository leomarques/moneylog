package lmm.moneylog.ui.features.creditcard.homecard.view.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.features.creditcard.homecard.model.CreditCardHomeCardItem
import lmm.moneylog.ui.features.creditcard.homecard.model.mockCards
import lmm.moneylog.ui.features.creditcard.homecard.view.components.CreditHomeCardContent
import lmm.moneylog.ui.theme.MoneylogTheme
import lmm.moneylog.ui.theme.Size

@Composable
fun CreditHomeCardLayout(
    hideValues: Boolean,
    onClick: (Int) -> Unit,
    cards: List<CreditCardHomeCardItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.credit_cards),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = Size.XSmallSpaceSize)
        )

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Size.DefaultSpaceSize))
                    .background(MaterialTheme.colorScheme.tertiaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreditHomeCardContent(
                onClick = onClick,
                hideValues = hideValues,
                cards = cards
            )
        }
    }
}

@Preview
@Composable
private fun CreditHomeCardLayoutPreview() {
    MoneylogTheme {
        CreditHomeCardLayout(
            hideValues = false,
            onClick = {},
            cards = mockCards
        )
    }
}
