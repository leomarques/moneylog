package lmm.moneylog.ui.features.account.transfer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.ClickTextField
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.features.transaction.transactiondetail.components.TextPicker
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTransferLayout(
    valueField: MutableState<String>,
    originAccountDisplay: String,
    destinationAccountDisplay: String,
    accounts: List<String>,
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    onOriginAccountPicked: (Int) -> Unit,
    onDestinationAccountPicked: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.account_transfer_topbar_title))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detailtransaction_arrowback_desc)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MyFab(
                onClick = onFabClick,
                icon = Icons.Default.Check
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(paddingValues)) {
                AccountTransferContent(
                    valueField = valueField,
                    originAccountDisplay = originAccountDisplay,
                    destinationAccountDisplay = destinationAccountDisplay,
                    accounts = accounts,
                    onOriginAccountPicked = onOriginAccountPicked,
                    onDestinationAccountPicked = onDestinationAccountPicked
                )
            }
        }
    )
}

@Composable
fun AccountTransferContent(
    valueField: MutableState<String>,
    originAccountDisplay: String,
    destinationAccountDisplay: String,
    accounts: List<String>,
    onOriginAccountPicked: (Int) -> Unit,
    onDestinationAccountPicked: (Int) -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        var showOriginAccountPicker by remember { mutableStateOf(false) }
        var showDestinationAccountPicker by remember { mutableStateOf(false) }

        if (showOriginAccountPicker) {
            TextPicker(
                list = accounts,
                onConfirm = { index ->
                    onOriginAccountPicked(index)
                },
                onDismiss = {
                    showOriginAccountPicker = false
                }
            )
        }

        if (showDestinationAccountPicker) {
            TextPicker(
                list = accounts,
                onConfirm = { index ->
                    onDestinationAccountPicked(index)
                },
                onDismiss = {
                    showDestinationAccountPicker = false
                }
            )
        }

        StateTextField(
            title = stringResource(R.string.detailtransaction_value),
            keyboardType = KeyboardType.Number,
            valueState = valueField,
            getFocus = true
        )

        Text(
            modifier = Modifier.padding(vertical = SpaceSize.DefaultSpaceSize),
            text = "Transferir de:"
        )

        ClickTextField(
            title = stringResource(R.string.account_transfer_origin_account),
            value = originAccountDisplay,
            enabled = accounts.isNotEmpty(),
            onClick = {
                showOriginAccountPicker = true
            }
        )

        Text(
            modifier = Modifier.padding(vertical = SpaceSize.DefaultSpaceSize),
            text = "Para:"
        )

        ClickTextField(
            title = stringResource(R.string.account_transfer_destination_account),
            value = destinationAccountDisplay,
            enabled = accounts.isNotEmpty(),
            onClick = {
                showDestinationAccountPicker = true
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AccountTransferLayoutPreview() {
    AccountTransferLayout(
        valueField = mutableStateOf(""),
        originAccountDisplay = "Nubank",
        destinationAccountDisplay = "Itaú",
        accounts = emptyList(),
        onArrowBackClick = {},
        onFabClick = {},
        onOriginAccountPicked = {},
        onDestinationAccountPicked = {}

    )
}
