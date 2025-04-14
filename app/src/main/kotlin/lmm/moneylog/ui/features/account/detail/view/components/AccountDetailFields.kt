package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.AccountIcon
import lmm.moneylog.ui.components.icons.BrushIcon
import lmm.moneylog.ui.components.textfields.ColorTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.outcome

@Composable
fun AccountDetailFields(
    name: String,
    color: Color,
    isEdit: Boolean,
    onNameChange: (String) -> Unit,
    onColorDialogClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        StateTextField(
            modifier = Modifier.padding(bottom = Size.MediumSpaceSize),
            value = name,
            title = stringResource(R.string.name),
            keyboardType = KeyboardType.Text,
            getFocus = !isEdit,
            leadingIcon = { AccountIcon() },
            onValueChange = onNameChange,
            testTag = "AccountNameTextField"
        )

        ColorTextField(
            color = color,
            leadingIcon = { BrushIcon() },
            onClick = onColorDialogClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountDetailFieldsPreview() {
    AccountDetailFields(
        name = "Account",
        color = outcome,
        isEdit = false,
        onNameChange = {},
        onColorDialogClick = {}
    )
}
