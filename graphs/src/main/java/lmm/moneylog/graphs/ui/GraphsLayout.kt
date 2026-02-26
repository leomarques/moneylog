package lmm.moneylog.graphs.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.graphs.R
import lmm.moneylog.graphs.ui.components.BarChart
import lmm.moneylog.graphs.ui.components.LineChart
import lmm.moneylog.graphs.ui.components.MonthSelector
import lmm.moneylog.graphs.ui.components.PieChart
import lmm.moneylog.graphs.viewmodel.GraphsViewModel
import lmm.moneylog.ui.extensions.formatForRs
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size
import lmm.moneylog.ui.theme.income
import lmm.moneylog.ui.theme.outcome
import org.koin.androidx.compose.koinViewModel

/**
 * Main graphs screen layout with tabbed interface
 * Shows different types of graphs: Pie Chart, and placeholders for future graphs
 *
 * @param modifier Modifier for the screen container
 * @param viewModel The ViewModel for managing graph data
 */
@Composable
fun GraphsLayout(
    modifier: Modifier = Modifier,
    viewModel: GraphsViewModel = koinViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs =
        listOf(
            stringResource(R.string.graphs_tab_pie_chart),
            stringResource(R.string.graphs_tab_bar_chart),
            stringResource(R.string.graphs_tab_net_worth)
        )

    val incomeByCategory by viewModel.incomeByCategory.collectAsState()
    val expensesByCategory by viewModel.expensesByCategory.collectAsState()
    val monthlyTotals by viewModel.monthlyTotals.collectAsState()
    val netWorthHistory by viewModel.netWorthHistory.collectAsState()
    val isIncome by viewModel.isIncome.collectAsState()
    val monthName by viewModel.monthName.collectAsState()
    val selectedYear by viewModel.selectedYear.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Tab Row
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        // Content based on selected tab
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(Size.DefaultSpaceSize)
        ) {
            when (selectedTabIndex) {
                0 ->
                    PieChartTab(
                        isIncome = isIncome,
                        incomeData = incomeByCategory,
                        expensesData = expensesByCategory,
                        monthName = monthName,
                        year = selectedYear,
                        onToggleIncomeExpense = { viewModel.toggleIncomeExpense() },
                        onPreviousMonth = { viewModel.goToPreviousMonth() },
                        onNextMonth = { viewModel.goToNextMonth() }
                    )

                1 ->
                    BarChartTab(
                        monthlyTotals = monthlyTotals
                    )

                2 ->
                    LineChartTab(
                        netWorthHistory = netWorthHistory
                    )
            }
        }
    }
}

/**
 * Tab content for the pie chart showing income/expenses by category
 */
@Composable
private fun PieChartTab(
    isIncome: Boolean,
    incomeData: List<lmm.moneylog.data.graphs.model.CategoryAmount>,
    expensesData: List<lmm.moneylog.data.graphs.model.CategoryAmount>,
    monthName: String,
    year: Int,
    onToggleIncomeExpense: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
    ) {
        // Month Selector
        MonthSelector(
            monthName = monthName,
            year = year,
            onPreviousMonth = onPreviousMonth,
            onNextMonth = onNextMonth,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = Size.SmallSpaceSize)
        )

        // Toggle between Income and Expenses
        SecondaryTabRow(
            selectedTabIndex = if (isIncome) 1 else 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = !isIncome,
                onClick = { if (isIncome) onToggleIncomeExpense() },
                text = { Text(stringResource(R.string.graphs_pie_expenses)) }
            )
            Tab(
                selected = isIncome,
                onClick = { if (!isIncome) onToggleIncomeExpense() },
                text = { Text(stringResource(R.string.graphs_pie_income)) }
            )
        }

        // Total value display
        val currentData = if (isIncome) incomeData else expensesData
        val totalValue = currentData.sumOf { it.totalAmount }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = Size.MediumSpaceSize),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isIncome) {
                    stringResource(R.string.graphs_pie_total_income)
                } else {
                    stringResource(R.string.graphs_pie_total_expenses)
                },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = totalValue.formatForRs(),
                style = MaterialTheme.typography.headlineMedium,
                color = if (isIncome) income else outcome
            )
        }

        // Pie Chart
        PieChart(
            data = currentData,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Tab content for the bar chart showing monthly income vs expenses
 */
@Composable
private fun BarChartTab(
    monthlyTotals: List<lmm.moneylog.data.graphs.model.MonthlyTotal>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = Size.DefaultSpaceSize)
    ) {
        Text(
            text = stringResource(R.string.graphs_bar_last_12_months),
            style = MaterialTheme.typography.titleLarge,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.DefaultSpaceSize),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        BarChart(
            data = monthlyTotals,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Tab content for the line chart showing net worth over time
 */
@Composable
private fun LineChartTab(
    netWorthHistory: List<lmm.moneylog.data.graphs.model.NetWorthPoint>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = Size.DefaultSpaceSize)
    ) {
        LineChart(
            data = netWorthHistory,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GraphsLayoutPreview() {
    AppTheme {
        GraphsLayout()
    }
}
