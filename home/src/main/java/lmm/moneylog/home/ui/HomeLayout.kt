package lmm.moneylog.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.home.ui.views.BalanceCardView
import lmm.moneylog.home.ui.views.CreditCardsView
import lmm.moneylog.home.ui.views.FinancialSummaryView
import lmm.moneylog.home.ui.views.HeaderView
import lmm.moneylog.ui.components.fabs.AddFab
import lmm.moneylog.ui.theme.AppTheme
import lmm.moneylog.ui.theme.Size

/**
 * Main home screen view that composes self-contained card views
 * Each card view manages its own ViewModel and state independently
 *
 * @param callbacks Callbacks for user interactions
 * @param modifier Modifier for the screen container
 */
@Composable
fun HomeLayout(
    callbacks: HomeLayoutCallbacks,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        floatingActionButton = {
            AddFab(onClick = callbacks.onFabClick)
        }
    ) { paddingValues ->
        HomeContent(
            paddingValues = paddingValues,
            callbacks = callbacks
        )
    }
}

@Composable
private fun HomeContent(
    paddingValues: PaddingValues,
    callbacks: HomeLayoutCallbacks,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Size.DefaultSpaceSize)
    ) {
        var valuesMasked by remember { mutableStateOf(false) }

        HeaderView(
            valuesMasked = valuesMasked,
            onMaskToggle = {
                valuesMasked = !valuesMasked
                callbacks.onMaskToggle()
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.MediumSpaceSize)
        )

        BalanceCardView(
            callbacks = callbacks.balanceCardCallbacks,
            valuesMasked = valuesMasked,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.MediumSpaceSize)
        )

        FinancialSummaryView(
            onIncomeClick = callbacks.onIncomeClick,
            onExpensesClick = callbacks.onExpensesClick,
            valuesMasked = valuesMasked,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Size.MediumSpaceSize)
        )

        CreditCardsView(
            callbacks = callbacks.creditCardsCallbacks,
            valuesMasked = valuesMasked,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeLayoutPreview() {
    AppTheme {
        HomeLayout(
            callbacks = HomePreviewData.sampleHomeLayoutCallbacks()
        )
    }
}
