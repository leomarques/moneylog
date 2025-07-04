package lmm.moneylog.ui.features.category.detail.view.components

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
import lmm.moneylog.ui.components.misc.IncomeRadioGroup
import lmm.moneylog.ui.components.textfields.ColorTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.theme.Size

@Composable
fun CategoryDetailFields(
    name: String,
    isEdit: Boolean,
    onNameChange: (String) -> Unit,
    isIncome: Boolean,
    onIncomeChange: (Boolean) -> Unit,
    color: Color,
    onColorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = name,
            title = stringResource(R.string.name),
            keyboardType = KeyboardType.Text,
            getFocus = !isEdit,
            onValueChange = onNameChange,
            testTag = "CategoryNameTextField"
        )

        IncomeRadioGroup(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            isIncome = isIncome,
            onClick = onIncomeChange
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
private fun CategoryDetailFieldsPreview() {
    CategoryDetailFields(
        name = "",
        isEdit = false,
        onNameChange = {},
        isIncome = false,
        onIncomeChange = {},
        color = Color.White,
        onColorClick = {}
    )
}
