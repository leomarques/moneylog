package lmm.moneylog.ui.features.invoice.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.textfields.StateTextField

@Composable
fun AdjustInvoiceDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var valueText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.invoice_adjust_value_title),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = stringResource(R.string.invoice_adjust_value_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                StateTextField(
                    value = valueText,
                    title = stringResource(R.string.invoice_adjust_value_new),
                    keyboardType = KeyboardType.Decimal,
                    leadingIcon = { ValueIcon() },
                    onValueChange = { valueText = it },
                    getFocus = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.action_cancel))
                    }

                    TextButton(
                        onClick = {
                            if (valueText.isNotBlank()) {
                                onConfirm(valueText.trim())
                            }
                        },
                        enabled = valueText.isNotBlank()
                    ) {
                        Text(stringResource(R.string.action_ok))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AdjustInvoiceDialogPreview() {
    AdjustInvoiceDialog(
        onDismiss = {},
        onConfirm = {}
    )
}
