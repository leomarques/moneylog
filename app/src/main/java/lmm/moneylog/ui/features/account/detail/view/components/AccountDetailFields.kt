package lmm.moneylog.ui.features.account.detail.view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

@Composable
fun AccountDetailFields(
    name: String,
    color: Color,
    isEdit: Boolean,
    showColorsDialog: MutableState<Boolean>,
    onNameChange: (String) -> Unit
) {
    Column {
        StateTextField(
            modifier = Modifier.padding(bottom = Size.MediumSpaceSize),
            value = name,
            title = stringResource(R.string.name),
            leadingIcon = { AccountIcon() },
            keyboardType = KeyboardType.Text,
            getFocus = !isEdit,
            onValueChange = onNameChange
        )

        ColorTextField(
            color = color,
            leadingIcon = { BrushIcon() },
            onClick = {
                showColorsDialog.value = true
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AccountDetailFieldsPreview() {
    AccountDetailFields(
        name = "Account",
        isEdit = false,
        onNameChange = {},
        color = Color.Red,
        showColorsDialog = mutableStateOf(false)
    )
}
