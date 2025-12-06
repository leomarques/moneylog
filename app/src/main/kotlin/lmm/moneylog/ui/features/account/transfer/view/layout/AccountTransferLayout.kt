package lmm.moneylog.ui.features.account.transfer.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.components.fabs.HideFab
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferModel
import lmm.moneylog.ui.features.account.transfer.view.components.AccountTransferTopBar
import lmm.moneylog.ui.theme.outcome

@Composable
fun AccountTransferLayout(
    value: String,
    accounts: List<AccountTransferModel>,
    originAccountDisplay: String,
    destinationAccountDisplay: String,
    originAccountColor: Color,
    destinationAccountColor: Color,
    onArrowBackClick: () -> Unit,
    onOriginAccountPick: (Int) -> Unit,
    onDestinationAccountPick: (Int) -> Unit,
    onValueChange: (String) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { AccountTransferTopBar(onArrowBackClick = onArrowBackClick) },
        floatingActionButton = {
            HideFab(
                onClick = onFabClick,
                icon = Icons.Default.Check
            )
        },
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                AccountTransferContent(
                    value = value,
                    list = accounts,
                    originAccountDisplay = originAccountDisplay,
                    destinationAccountDisplay = destinationAccountDisplay,
                    originAccountColor = originAccountColor,
                    destinationAccountColor = destinationAccountColor,
                    onOriginAccountPick = onOriginAccountPick,
                    onDestinationAccountPick = onDestinationAccountPick,
                    onValueChange = onValueChange
                )
            }
        }
    )
}

@Preview
@Composable
private fun AccountTransferLayoutPreview() {
    AccountTransferLayout(
        value = "",
        accounts = emptyList(),
        originAccountDisplay = "Nubank",
        destinationAccountDisplay = "Itaú",
        originAccountColor = outcome,
        destinationAccountColor = Color.Blue,
        onArrowBackClick = {},
        onOriginAccountPick = {},
        onDestinationAccountPick = {},
        onValueChange = {},
        onFabClick = {}
    )
}
