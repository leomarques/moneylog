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
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.BottomSheetContent
import lmm.moneylog.ui.components.ClickTextField
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.components.StateTextField
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTransferLayout(
    valueField: MutableState<String>,
    originAccountDisplay: String,
    destinationAccountDisplay: String,
    originAccountColor: Color,
    destinationAccountColor: Color,
    accounts: List<AccountTransferModel>,
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
                    originAccountColor = originAccountColor,
                    destinationAccountColor = destinationAccountColor,
                    list = accounts,
                    onOriginAccountPicked = onOriginAccountPicked,
                    onDestinationAccountPicked = onDestinationAccountPicked
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTransferContent(
    valueField: MutableState<String>,
    originAccountDisplay: String,
    destinationAccountDisplay: String,
    originAccountColor: Color,
    destinationAccountColor: Color,
    list: List<AccountTransferModel>,
    onOriginAccountPicked: (Int) -> Unit,
    onDestinationAccountPicked: (Int) -> Unit
) {
    Column(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize)) {
        var showOriginAccountPicker by remember { mutableStateOf(false) }
        var showDestinationAccountPicker by remember { mutableStateOf(false) }

        if (showOriginAccountPicker) {
            ModalBottomSheet(
                onDismissRequest = {
                    showOriginAccountPicker = false
                }
            ) {
                BottomSheetContent(
                    list = list.map { it.name to it.color },
                    onConfirm = { index ->
                        onOriginAccountPicked(index)
                    }
                ) {
                    showOriginAccountPicker = false
                }
            }
        }

        if (showDestinationAccountPicker) {
            ModalBottomSheet(
                onDismissRequest = {
                    showOriginAccountPicker = false
                }
            ) {
                BottomSheetContent(
                    list = list.map { it.name to it.color },
                    onConfirm = { index ->
                        onDestinationAccountPicked(index)
                    }
                ) {
                    showDestinationAccountPicker = false
                }
            }
        }

        StateTextField(
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_attach_money_24),
                    contentDescription = stringResource(R.string.detailtransaction_value_icon_desc)
                )
            },
            modifier = Modifier.padding(bottom = SpaceSize.DefaultSpaceSize),
            title = stringResource(R.string.detailtransaction_value),
            keyboardType = KeyboardType.Number,
            valueState = valueField,
            getFocus = true
        )

        Text(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            text = stringResource(R.string.transfer_from)
        )

        ClickTextField(
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_account_balance_24),
                    contentDescription = stringResource(R.string.detailtransaction_account_icon_desc),
                    tint = originAccountColor
                )
            },
            modifier = Modifier.padding(bottom = SpaceSize.DefaultSpaceSize),
            value = originAccountDisplay,
            title = stringResource(R.string.account_transfer_origin_account),
            enabled = list.isNotEmpty(),
            onClick = {
                showOriginAccountPicker = true
            }
        )

        Text(
            modifier = Modifier.padding(bottom = SpaceSize.SmallSpaceSize),
            text = stringResource(R.string.transfer_to)
        )

        ClickTextField(
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_account_balance_24),
                    contentDescription = stringResource(R.string.detailtransaction_account_icon_desc),
                    tint = destinationAccountColor
                )
            },
            modifier = Modifier.padding(bottom = SpaceSize.DefaultSpaceSize),
            value = destinationAccountDisplay,
            title = stringResource(R.string.account_transfer_destination_account),
            enabled = list.isNotEmpty(),
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
        onDestinationAccountPicked = {},
        originAccountColor = Color.Red,
        destinationAccountColor = Color.Blue
    )
}
