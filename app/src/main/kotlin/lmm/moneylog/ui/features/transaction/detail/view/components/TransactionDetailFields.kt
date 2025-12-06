package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.data.transaction.model.TransactionSuggestion
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
    onDebtSelect: () -> Unit,
    onCreditSelect: () -> Unit,
    onValueChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSuggestionClick: (TransactionSuggestion) -> Unit,
    isCategoriesFieldEnabled: Boolean,
    isAccountsFieldEnabled: Boolean,
    isCreditCardFieldEnabled: Boolean,
    isDebtSelected: Boolean,
    onDateClick: () -> Unit,
    onClearSuggestions: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        StateTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = uiState.value,
            title = stringResource(R.string.common_value),
            keyboardType = KeyboardType.Number,
            getFocus = !uiState.isEdit,
            leadingIcon = { ValueIcon(tint = if (uiState.isIncome) income else outcome) },
            onValueChange = {
                onClearSuggestions()
                onValueChange(it)
            },
            testTag = "ValueTextField"
        )

        IncomeRadioGroup(
            modifier = Modifier.padding(bottom = Size.SmallSpaceSize),
            isIncome = uiState.isIncome,
            onClick = {
                onClearSuggestions()
                onIsIncomeSelect(it)
            }
        )

        ClickTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            value = uiState.displayDate,
            label = stringResource(R.string.common_date),
            leadingIcon = { DateIcon() },
            onClick = {
                onClearSuggestions()
                onDateClick()
            }
        )

        Box {
            Column {
                StateTextField(
                    modifier =
                        Modifier.padding(
                            bottom =
                                if (uiState.descriptionSuggestions.isEmpty()) {
                                    Size.DefaultSpaceSize
                                } else {
                                    Size.SmallSpaceSize
                                }
                        ),
                    value = uiState.description,
                    title = stringResource(R.string.common_description),
                    keyboardType = KeyboardType.Text,
                    leadingIcon = { DescriptionIcon() },
                    onValueChange = onDescriptionChange
                )

                if (uiState.descriptionSuggestions.isNotEmpty()) {
                    DescriptionAutocomplete(
                        suggestions = uiState.descriptionSuggestions,
                        onSuggestionClick = onSuggestionClick,
                        modifier = Modifier.padding(bottom = Size.DefaultSpaceSize)
                    )
                }
            }
        }

        ClickTextField(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            label = stringResource(R.string.common_category),
            value = uiState.displayCategory,
            enabled = isCategoriesFieldEnabled,
            leadingIcon = { CategoryIcon(tint = uiState.displayCategoryColor) },
            onClick = {
                onClearSuggestions()
                onCategoryClick()
            }
        )

        RadioGroupType(
            isDebtSelect = isDebtSelected,
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
            onDebtSelect = {
                onClearSuggestions()
                onDebtSelect()
            },
            onCreditSelect = {
                onClearSuggestions()
                onCreditSelect()
            }
        )

        if (isDebtSelected) {
            ClickTextField(
                modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
                label = stringResource(R.string.common_account),
                value = uiState.displayAccount,
                enabled = isAccountsFieldEnabled,
                leadingIcon = { AccountIcon(tint = uiState.displayAccountColor) },
                onClick = {
                    onClearSuggestions()
                    onAccountClick()
                }
            )
        } else {
            ClickTextField(
                modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
                label = stringResource(R.string.common_credit_card),
                value = uiState.displayCreditCard,
                enabled = isCreditCardFieldEnabled,
                leadingIcon = { CreditCardIcon(tint = uiState.displayCreditCardColor) },
                onClick = {
                    onClearSuggestions()
                    onCreditCardClick()
                }
            )

            ClickTextField(
                modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
                label = stringResource(R.string.common_invoice),
                value = uiState.displayInvoice,
                leadingIcon = { InvoiceIcon() },
                onClick = {
                    onClearSuggestions()
                    onInvoiceClick()
                }
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
        onSuggestionClick = {},
        isCategoriesFieldEnabled = true,
        isAccountsFieldEnabled = true,
        onDateClick = {},
        onCreditCardClick = {},
        onInvoiceClick = {},
        onDebtSelect = {},
        onCreditSelect = {},
        isDebtSelected = false,
        isCreditCardFieldEnabled = true,
        onClearSuggestions = {}
    )
}
