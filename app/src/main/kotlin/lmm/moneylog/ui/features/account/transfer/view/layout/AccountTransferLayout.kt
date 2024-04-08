package lmm.moneylog.ui.features.account.transfer.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FabPosition
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
    onOriginAccountPicked: (Int) -> Unit,
    onDestinationAccountPicked: (Int) -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onFabClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AccountTransferTopBar(
                modifier = modifier,
                onArrowBackClick = onArrowBackClick
            )
        },
        floatingActionButton = {
            HideFab(
                onClick = onFabClick,
                icon = Icons.Default.Check
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                AccountTransferContent(
                    value = value,
                    list = accounts,
                    originAccountDisplay = originAccountDisplay,
                    destinationAccountDisplay = destinationAccountDisplay,
                    originAccountColor = originAccountColor,
                    destinationAccountColor = destinationAccountColor,
                    onOriginAccountPicked = onOriginAccountPicked,
                    onDestinationAccountPicked = onDestinationAccountPicked,
                    onValueChange = onValueChange
                )
            }
        }
    )
}

@Preview
@Composable
fun AccountTransferLayoutPreview() {
    AccountTransferLayout(
        value = "",
        accounts = emptyList(),
        originAccountDisplay = "Nubank",
        destinationAccountDisplay = "Itaú",
        originAccountColor = outcome,
        destinationAccountColor = Color.Blue,
        onArrowBackClick = {},
        onOriginAccountPicked = {},
        onDestinationAccountPicked = {},
        onValueChange = {},
        onFabClick = {}
    )
}
