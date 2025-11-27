package lmm.moneylog.ui.screens.home.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.screens.home.components.HomeHeader
import lmm.moneylog.ui.screens.home.components.cards.CreditCardsCard
import lmm.moneylog.ui.screens.home.components.cards.IncomeExpenseCard
import lmm.moneylog.ui.screens.home.components.cards.TotalBalanceCard
import lmm.moneylog.ui.screens.home.mocks.HomePreviewData
import lmm.moneylog.ui.screens.home.models.FinancialSummary
import lmm.moneylog.ui.screens.home.models.HomeScreenData
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome

@Composable
fun HomeLayout(
    data: HomeScreenData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(Size.DefaultSpaceSize)
    ) {
        HomeHeader(
            text = data.periodTitle,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.MediumSpaceSize)
        )

        TotalBalanceCard(
            balanceInfo = data.balanceInfo,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.MediumSpaceSize)
        )

        IncomeExpenseRow(
            incomeData = data.income,
            expensesData = data.expenses,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.MediumSpaceSize)
        )

        CreditCardsCard(
            creditCards = data.creditCards,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun IncomeExpenseRow(
    incomeData: FinancialSummary,
    expensesData: FinancialSummary,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Size.DefaultSpaceSize)
    ) {
        IncomeExpenseCard(
            title = incomeData.title,
            amount = incomeData.amount,
            icon = Icons.Default.ArrowDownward,
            iconColor = income,
            modifier = Modifier.weight(1f)
        )
        IncomeExpenseCard(
            title = expensesData.title,
            amount = expensesData.amount,
            icon = Icons.Default.ArrowUpward,
            iconColor = outcome,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeLayoutPreview() {
    AppTheme {
        HomeLayout(data = HomePreviewData.sampleHomeScreenData())
    }
}
