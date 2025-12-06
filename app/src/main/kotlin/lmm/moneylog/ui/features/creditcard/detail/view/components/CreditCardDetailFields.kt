package lmm.moneylog.ui.features.creditcard.detail.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import lmm.moneylog.ui.components.icons.BrushIcon
import lmm.moneylog.ui.components.icons.CreditCardIcon
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.textfields.ColorTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.transaction.detail.view.components.DateIcon
import lmm.moneylog.ui.theme.income

@Composable
fun CreditCardDetailFields(
    name: String,
    closingDay: String,
    dueDay: String,
    limit: String,
    isEdit: Boolean,
    color: Color,
    onNameChange: (String) -> Unit,
    onClosingDayChange: (String) -> Unit,
    onDueDayDayChange: (String) -> Unit,
    onLimitChange: (String) -> Unit,
    onColorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card Information Card
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
                    text = stringResource(R.string.creditcard_section_info),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                StateTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    title = stringResource(R.string.common_name),
                    keyboardType = KeyboardType.Text,
                    leadingIcon = { CreditCardIcon() },
                    getFocus = !isEdit,
                    onValueChange = onNameChange,
                    testTag = "CreditCardNameTextField"
                )

                Spacer(modifier = Modifier.height(12.dp))

                ColorTextField(
                    modifier = Modifier.fillMaxWidth(),
                    color = color,
                    leadingIcon = { BrushIcon() },
                    onClick = onColorClick
                )
            }
        }

        // Billing Cycle Card
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
                    text = stringResource(R.string.creditcard_section_billing),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StateTextField(
                        modifier = Modifier.weight(1f),
                        value = closingDay,
                        title = stringResource(R.string.creditcard_closing_day),
                        keyboardType = KeyboardType.Number,
                        onValueChange = onClosingDayChange,
                        leadingIcon = { DateIcon() },
                        testTag = "CreditCardClosingDayTextField"
                    )

                    StateTextField(
                        modifier = Modifier.weight(1f),
                        value = dueDay,
                        title = stringResource(R.string.creditcard_due_day),
                        keyboardType = KeyboardType.Number,
                        onValueChange = onDueDayDayChange,
                        leadingIcon = { DateIcon() },
                        testTag = "CreditCardDueDayTextField"
                    )
                }
            }
        }

        // Credit Limit Card
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
                    text = stringResource(R.string.creditcard_section_limit),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                StateTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = limit,
                    title = stringResource(R.string.creditcard_limit),
                    keyboardType = KeyboardType.Number,
                    onValueChange = onLimitChange,
                    leadingIcon = { ValueIcon(tint = income) },
                    testTag = "CreditCardLimitTextField"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreditCardDetailFieldsPreview() {
    CreditCardDetailFields(
        name = "Food",
        closingDay = "1",
        dueDay = "1",
        limit = "1000",
        isEdit = true,
        color = Color.Red,
        onNameChange = {},
        onClosingDayChange = {},
        onDueDayDayChange = {},
        onLimitChange = {},
        onColorClick = {}
    )
}
