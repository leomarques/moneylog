package lmm.moneylog.ui.features.account.transfer.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.AccountIcon
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.textfields.ClickTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferModel
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun AccountTransferFields(
    value: String,
    list: List<AccountTransferModel>,
    originAccountDisplay: String,
    originAccountColor: Color,
    destinationAccountDisplay: String,
    destinationAccountColor: Color,
    onValueChange: (String) -> Unit,
    onOriginAccountPick: () -> Unit,
    onDestinationAccountPick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = value,
            title = stringResource(R.string.value),
            keyboardType = KeyboardType.Number,
            getFocus = true,
            leadingIcon = { ValueIcon() },
            onValueChange = onValueChange
        )

        Text(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            text = stringResource(R.string.transfer_from)
        )

        ClickTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = originAccountDisplay,
            label = stringResource(R.string.transfer_origin),
            leadingIcon = { AccountIcon(tint = originAccountColor) },
            enabled = list.isNotEmpty(),
            onClick = onOriginAccountPick
        )

        Text(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            text = stringResource(R.string.transfer_to)
        )

        ClickTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = destinationAccountDisplay,
            label = stringResource(R.string.transfer_destination),
            leadingIcon = { AccountIcon(tint = destinationAccountColor) },
            enabled = list.isNotEmpty(),
            onClick = onDestinationAccountPick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TransferFieldsPreview() {
    AccountTransferFields(
        value = "100",
        list = emptyList(),
        originAccountDisplay = "Conta 1",
        originAccountColor = outcome,
        destinationAccountDisplay = "Conta 2",
        destinationAccountColor = Color.Blue,
        onValueChange = {},
        onOriginAccountPick = {},
        onDestinationAccountPick = {}
    )
}
