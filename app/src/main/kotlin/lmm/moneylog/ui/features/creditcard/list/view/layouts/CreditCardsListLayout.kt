package lmm.moneylog.ui.features.creditcard.list.view.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.HideFab
import lmm.moneylog.ui.features.creditcard.list.model.CreditCardModel
import lmm.moneylog.ui.features.creditcard.list.view.components.CreditCardsListTopBar
import lmm.moneylog.ui.theme.darkRed

@Composable
fun CreditCardsListLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    list: List<CreditCardModel>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { CreditCardsListTopBar(onArrowBackClick = onArrowBackClick) },
        floatingActionButton = {
            HideFab(
                onClick = onFabClick,
                icon = Icons.Default.Add
            )
        },
        content = { paddingValues ->
            Box(
                Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .testTag("CreditCardsListScreen")
            ) {
                CreditCardsListContent(
                    list = list,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Preview
@Composable
private fun CreditCardsListLayoutPreview() {
    CreditCardsListLayout(
        onArrowBackClick = { },
        onFabClick = { },
        list =
            listOf(
                CreditCardModel(
                    id = 0,
                    name = "Renner",
                    color = darkRed
                ),
                CreditCardModel(
                    id = 0,
                    name = "Leader",
                    color = darkRed
                )
            ),
        onItemClick = {}
    )
}
