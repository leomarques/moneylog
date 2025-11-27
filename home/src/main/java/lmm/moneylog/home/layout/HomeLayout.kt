package lmm.moneylog.home.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.home.components.HomeHeader
import lmm.moneylog.home.components.cards.CreditCardsCard
import lmm.moneylog.home.components.cards.IncomeExpenseCard
import lmm.moneylog.home.components.cards.TotalBalanceCard
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.home.models.FinancialSummary
import lmm.moneylog.home.models.HomeScreenData
import lmm.moneylog.ui.components.fabs.AddFab
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.ExpenseColor
import lmm.moneylog.ui.theme.IncomeColor
import lmm.moneylog.ui.theme.Size

/**
 * Main layout for the home screen displaying financial summary including balance,
 * income, expenses, and credit cards
 *
 * @param data The home screen data to display
 * @param modifier Modifier for the layout container
 * @param callbacks Callbacks for user interactions within the home screen
 */
@Composable
fun HomeLayout(
    data: HomeScreenData,
    callbacks: HomeLayoutCallbacks,
    modifier: Modifier = Modifier
) {
    var valuesMasked by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            AddFab(onClick = callbacks.onFabClick)
        }
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Size.DefaultSpaceSize)
        ) {
            HomeHeader(
                text = data.periodTitle,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = Size.MediumSpaceSize),
                valuesMasked = valuesMasked,
                onMaskToggle = {
                    valuesMasked = !valuesMasked
                    callbacks.onMaskToggle()
                }
            )

            TotalBalanceCard(
                balanceInfo = data.balanceInfo,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = Size.MediumSpaceSize),
                callbacks = callbacks.balanceCardCallbacks,
                valuesMasked = valuesMasked
            )

            IncomeExpenseRow(
                incomeData = data.income,
                expensesData = data.expenses,
                onIncomeClick = callbacks.onIncomeClick,
                onExpensesClick = callbacks.onExpensesClick,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = Size.MediumSpaceSize),
                valuesMasked = valuesMasked
            )

            CreditCardsCard(
                creditCards = data.creditCards,
                modifier = Modifier.fillMaxWidth(),
                callbacks = callbacks.creditCardsCallbacks,
                valuesMasked = valuesMasked
            )
        }
    }
}

@Composable
private fun IncomeExpenseRow(
    incomeData: FinancialSummary,
    expensesData: FinancialSummary,
    modifier: Modifier = Modifier,
    onIncomeClick: () -> Unit = {},
    onExpensesClick: () -> Unit = {},
    valuesMasked: Boolean = false
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Size.DefaultSpaceSize)
    ) {
        IncomeExpenseCard(
            title = incomeData.title,
            amount = incomeData.amount,
            icon = Icons.Default.ArrowDownward,
            iconColor = IncomeColor,
            modifier = Modifier.weight(1f),
            onClick = onIncomeClick,
            valuesMasked = valuesMasked
        )
        IncomeExpenseCard(
            title = expensesData.title,
            amount = expensesData.amount,
            icon = Icons.Default.ArrowUpward,
            iconColor = ExpenseColor,
            modifier = Modifier.weight(1f),
            onClick = onExpensesClick,
            valuesMasked = valuesMasked
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeLayoutPreview() {
    AppTheme {
        HomeLayout(
            data = HomePreviewData.sampleHomeScreenData(),
            callbacks = HomePreviewData.sampleHomeLayoutCallbacks()
        )
    }
}
