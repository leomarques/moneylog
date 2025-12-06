package lmm.moneylog.ui.features.account.transfer.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.AccountIcon
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.textfields.ClickTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.account.transfer.model.AccountTransferModel
import lmm.moneylog.ui.theme.income
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
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Transfer Amount Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.transfer_section_amount),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                StateTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    title = stringResource(R.string.common_value),
                    keyboardType = KeyboardType.Number,
                    getFocus = true,
                    leadingIcon = { ValueIcon(tint = income) },
                    onValueChange = onValueChange
                )
            }
        }

        // Origin Account Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.transfer_section_from),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ClickTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = originAccountDisplay,
                    label = stringResource(R.string.transfer_origin),
                    leadingIcon = { AccountIcon(tint = originAccountColor) },
                    enabled = list.isNotEmpty(),
                    onClick = onOriginAccountPick
                )
            }
        }

        // Destination Account Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.transfer_section_to),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ClickTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = destinationAccountDisplay,
                    label = stringResource(R.string.transfer_destination),
                    leadingIcon = { AccountIcon(tint = destinationAccountColor) },
                    enabled = list.isNotEmpty(),
                    onClick = onDestinationAccountPick
                )
            }
        }
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
