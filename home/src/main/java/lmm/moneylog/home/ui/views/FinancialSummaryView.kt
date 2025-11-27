package lmm.moneylog.home.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import lmm.moneylog.home.R
import lmm.moneylog.home.ui.cards.IncomeExpenseCard
import lmm.moneylog.home.viewmodels.FinancialSummaryViewModel
import lmm.moneylog.ui.theme.ExpenseColor
import lmm.moneylog.ui.theme.IncomeColor
import lmm.moneylog.ui.theme.Size
import org.koin.androidx.compose.koinViewModel

/**
 * Self-contained view for income and expense cards
 * Manages its own ViewModel and state
 *
 * @param onIncomeClick Callback when income card is clicked
 * @param onExpensesClick Callback when expenses card is clicked
 * @param valuesMasked Whether to mask displayed values
 * @param modifier Modifier for the row container
 * @param viewModel ViewModel for financial summary data (injected by Koin)
 */
@Composable
fun FinancialSummaryView(
    onIncomeClick: () -> Unit,
    onExpensesClick: () -> Unit,
    valuesMasked: Boolean,
    modifier: Modifier = Modifier,
    viewModel: FinancialSummaryViewModel = koinViewModel()
) {
    val income by viewModel.income.collectAsState()
    val expenses by viewModel.expenses.collectAsState()

    if (income == null || expenses == null) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(120.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(Size.DefaultSpaceSize)
        ) {
            IncomeExpenseCard(
                title = stringResource(R.string.income),
                amount = income!!.amount,
                icon = Icons.Default.ArrowDownward,
                iconColor = IncomeColor,
                modifier = Modifier.weight(1f),
                onClick = onIncomeClick,
                valuesMasked = valuesMasked
            )
            IncomeExpenseCard(
                title = stringResource(R.string.expenses),
                amount = expenses!!.amount,
                icon = Icons.Default.ArrowUpward,
                iconColor = ExpenseColor,
                modifier = Modifier.weight(1f),
                onClick = onExpensesClick,
                valuesMasked = valuesMasked
            )
        }
    }
}
