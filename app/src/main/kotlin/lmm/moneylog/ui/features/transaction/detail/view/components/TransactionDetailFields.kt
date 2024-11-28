package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.icons.AccountIcon
import lmm.moneylog.ui.components.icons.CategoryIcon
import lmm.moneylog.ui.components.icons.CreditCardIcon
import lmm.moneylog.ui.components.icons.InvoiceIcon
import lmm.moneylog.ui.components.icons.ValueIcon
import lmm.moneylog.ui.components.misc.IncomeRadioGroup
import lmm.moneylog.ui.components.radiogroup.RadioGroupType
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
    onCreditCardClick: () -> Unit,
    onInvoiceClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    isCategoriesFieldEnabled: Boolean,
    isAccountsFieldEnabled: Boolean,
    modifier: Modifier = Modifier,
    onDateClick: () -> Unit
) {
    Column(modifier) {
        val isDebtSelected = remember { mutableStateOf(true) }

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
            label = stringResource(R.string.category),
            value = uiState.displayCategory,
            enabled = isCategoriesFieldEnabled,
            leadingIcon = { CategoryIcon(tint = uiState.displayCategoryColor) },
            onClick = onCategoryClick
        )

        RadioGroupType(
            isDebtSelected = isDebtSelected.value,
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            onDebtSelected = { isDebtSelected.value = true },
            onCreditSelected = { isDebtSelected.value = false }
        )

        if (isDebtSelected.value) {
            ClickTextField(
                modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
                label = stringResource(R.string.account),
                value = uiState.displayAccount,
                enabled = isAccountsFieldEnabled,
                leadingIcon = { AccountIcon(tint = uiState.displayAccountColor) },
                onClick = onAccountClick
            )
        } else {
            ClickTextField(
                modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
                label = stringResource(R.string.credit_card),
                value = uiState.displayCreditCard,
                leadingIcon = { CreditCardIcon(tint = uiState.displayCreditCardColor) },
                onClick = onCreditCardClick
            )

            ClickTextField(
                modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
                label = stringResource(R.string.invoice),
                value = uiState.displayInvoice,
                leadingIcon = { InvoiceIcon(tint = uiState.displayInvoiceColor) },
                onClick = onInvoiceClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailFieldsPreview() {
    TransactionDetailFields(
        uiState = TransactionDetailUIState(),
        onIsIncomeSelect = {},
        onAccountClick = {},
        onCategoryClick = {},
        onValueChange = {},
        onDescriptionChange = {},
        isCategoriesFieldEnabled = true,
        isAccountsFieldEnabled = true,
        onDateClick = {},
        onCreditCardClick = {},
        onInvoiceClick = {}
    )
}
