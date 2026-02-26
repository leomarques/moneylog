package lmm.moneylog.ui.features.transaction.detail.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Amount and Type Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor =
                        if (uiState.isIncome) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.errorContainer
                        }
                )
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {
                // Section Header
                Text(
                    text = stringResource(R.string.transaction_section_amount),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                StateTextField(
                    modifier = Modifier.fillMaxWidth(),
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

                Spacer(modifier = Modifier.height(16.dp))

                IncomeRadioGroup(
                    isIncome = uiState.isIncome,
                    onClick = {
                        onClearSuggestions()
                        onIsIncomeSelect(it)
                    }
                )
            }
        }

        // Date and Description Card
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
                    text = stringResource(R.string.transaction_section_details),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ClickTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.displayDate,
                    label = stringResource(R.string.common_date),
                    leadingIcon = { DateIcon() },
                    onClick = {
                        onClearSuggestions()
                        onDateClick()
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box {
                    Column {
                        StateTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiState.description,
                            title = stringResource(R.string.common_description),
                            keyboardType = KeyboardType.Text,
                            leadingIcon = { DescriptionIcon() },
                            onValueChange = onDescriptionChange
                        )

                        if (uiState.descriptionSuggestions.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            DescriptionAutocomplete(
                                suggestions = uiState.descriptionSuggestions,
                                onSuggestionClick = { suggestion ->
                                    focusManager.clearFocus()
                                    onSuggestionClick(suggestion)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Category Card
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
                    text = stringResource(R.string.transaction_section_category),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ClickTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.common_category),
                    value = uiState.displayCategory,
                    enabled = isCategoriesFieldEnabled,
                    leadingIcon = { CategoryIcon(tint = uiState.displayCategoryColor) },
                    onClick = {
                        onClearSuggestions()
                        onCategoryClick()
                    }
                )
            }
        }

        // Payment Method Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.transaction_section_payment),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                RadioGroupType(
                    isDebtSelect = isDebtSelected,
                    onDebtSelect = {
                        onClearSuggestions()
                        onDebtSelect()
                    },
                    onCreditSelect = {
                        onClearSuggestions()
                        onCreditSelect()
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (isDebtSelected) {
                    ClickTextField(
                        modifier = Modifier.fillMaxWidth(),
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
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.common_credit_card),
                        value = uiState.displayCreditCard,
                        enabled = isCreditCardFieldEnabled,
                        leadingIcon = { CreditCardIcon(tint = uiState.displayCreditCardColor) },
                        onClick = {
                            onClearSuggestions()
                            onCreditCardClick()
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ClickTextField(
                        modifier = Modifier.fillMaxWidth(),
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
