package lmm.moneylog.ui.features.transaction.detail.view.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.AccountIcon
import lmm.moneylog.ui.components.icons.CategoryIcon
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.misc.IncomeRadioGroup
import lmm.moneylog.ui.components.textfields.ClickTextField
import lmm.moneylog.ui.components.textfields.StateTextField
import lmm.moneylog.ui.features.transaction.detail.model.TransactionDetailUIState
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome

@Composable
fun TransactionDetailFields(
    uiState: TransactionDetailUIState,
    onIsIncomeSelect: (Boolean) -> Unit,
    onAccountClick: () -> Unit,
    onCategoryClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    isCategoriesFieldEnabled: Boolean,
    uisAccountsFieldEnabled: Boolean,
    modifier: Modifier = Modifier,
    onDateClick: () -> Unit
) {
    Column {
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = uiState.value,
            title = stringResource(R.string.value),
            keyboardType = KeyboardType.Number,
            getFocus = !uiState.isEdit,
            leadingIcon = { ValueIcon(tint = if (uiState.isIncome) income else outcome) },
            onValueChange = onValueChange,
            testTag = "ValueTextField"
        )

        IncomeRadioGroup(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            isIncome = uiState.isIncome,
            onClick = { onIsIncomeSelect(it) }
        )

        ClickTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = uiState.displayDate,
            label = stringResource(R.string.detail_date),
            leadingIcon = { DateIcon() },
            onClick = onDateClick
        )

        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = uiState.description,
            title = stringResource(R.string.detail_description),
            keyboardType = KeyboardType.Text,
            leadingIcon = { DescriptionIcon() },
            onValueChange = onDescriptionChange
        )

        ClickTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            label = stringResource(R.string.account),
            value = uiState.displayAccount,
            enabled = uisAccountsFieldEnabled,
            leadingIcon = { AccountIcon(tint = uiState.displayAccountColor) },
            onClick = onAccountClick
        )

        ClickTextField(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            label = stringResource(R.string.category),
            value = uiState.displayCategory,
            enabled = isCategoriesFieldEnabled,
            leadingIcon = { CategoryIcon(tint = uiState.displayCategoryColor) },
            onClick = onCategoryClick
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun TransactionDetailFieldsPreview() {
    TransactionDetailFields(
        uiState = TransactionDetailUIState(),
        onIsIncomeSelect = {},
        onAccountClick = {},
        onCategoryClick = {},
        onValueChange = {},
        onDescriptionChange = {},
        isCategoriesFieldEnabled = true,
        uisAccountsFieldEnabled = true,
        onDateClick = {}
    )
}
