package lmm.moneylog.home.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lmm.moneylog.home.ui.cards.RecentExpensesCard
import lmm.moneylog.home.viewmodels.RecentExpensesViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Self-contained view for expenses summary card with category chart
 * Manages its own ViewModel and state
 *
 * @param onClick Callback when card is clicked
 * @param valuesMasked Whether to mask displayed values
 * @param modifier Modifier for the card container
 * @param viewModel ViewModel for expenses data (injected by Koin)
 */
@Composable
fun RecentExpensesView(
    onClick: () -> Unit,
    valuesMasked: Boolean,
    modifier: Modifier = Modifier,
    viewModel: RecentExpensesViewModel = koinViewModel()
) {
    val expensesSummary by viewModel.expensesSummary.collectAsState()

    if (expensesSummary == null) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        RecentExpensesCard(
            summary = expensesSummary!!,
            modifier = modifier,
            onClick = onClick,
            valuesMasked = valuesMasked
        )
    }
}
