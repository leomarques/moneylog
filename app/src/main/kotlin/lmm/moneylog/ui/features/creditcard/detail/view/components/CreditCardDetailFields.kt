package lmm.moneylog.ui.features.creditcard.detail.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.BrushIcon
import lmm.moneylog.ui.components.icons.CreditCardIcon
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.textfields.ColorTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.transaction.detail.view.components.DateIcon
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income

@Composable
fun CreditCardDetailFields(
    name: String,
    closingDay: Int,
    dueDay: Int,
    limit: Int,
    isEdit: Boolean,
    color: Color,
    onNameChange: (String) -> Unit,
    onClosingDayChange: (String) -> Unit,
    onDueDayDayChange: (String) -> Unit,
    onLimitChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onColorClick: () -> Unit
) {
    Column(modifier) {
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = name,
            title = stringResource(R.string.name),
            keyboardType = KeyboardType.Text,
            leadingIcon = { CreditCardIcon() },
            getFocus = !isEdit,
            onValueChange = onNameChange,
            testTag = "CreditCardNameTextField"
        )
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = closingDay.toString(),
            title = stringResource(R.string.creditcard_closingday),
            keyboardType = KeyboardType.Number,
            onValueChange = onClosingDayChange,
            leadingIcon = { DateIcon() },
            testTag = "CreditCardClosingDayTextField"
        )
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = dueDay.toString(),
            title = stringResource(R.string.creditcard_dueday),
            keyboardType = KeyboardType.Number,
            onValueChange = onDueDayDayChange,
            leadingIcon = { DateIcon() },
            testTag = "CreditCardDueDayTextField"
        )
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = limit.toString(),
            title = stringResource(R.string.creditcard_limit),
            keyboardType = KeyboardType.Number,
            onValueChange = onLimitChange,
            leadingIcon = { ValueIcon(tint = income) },
            testTag = "CreditCardLimitTextField"
        )

        ColorTextField(
            color = color,
            leadingIcon = { BrushIcon() },
            onClick = onColorClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreditCardDetailFieldsPreview() {
    CreditCardDetailFields(
        name = "Food",
        closingDay = 1,
        dueDay = 1,
        limit = 1000,
        isEdit = true,
        color = Color.Red,
        onNameChange = {},
        onClosingDayChange = {},
        onDueDayDayChange = {},
        onLimitChange = {},
        onColorClick = {}
    )
}
