package lmm.moneylog.home.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lmm.moneylog.home.ui.cards.TotalBalanceCard
import lmm.moneylog.home.ui.cards.TotalBalanceCardCallbacks
import lmm.moneylog.home.viewmodels.BalanceCardViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Self-contained view for the balance card
 * Manages its own ViewModel and state
 *
 * @param callbacks Callbacks for balance card interactions
 * @param valuesMasked Whether to mask displayed values
 * @param modifier Modifier for the card container
 * @param viewModel ViewModel for balance data (injected by Koin)
 */
@Composable
fun BalanceCardView(
    callbacks: TotalBalanceCardCallbacks,
    valuesMasked: Boolean,
    modifier: Modifier = Modifier,
    viewModel: BalanceCardViewModel = koinViewModel()
) {
    val balanceInfo by viewModel.balanceInfo.collectAsStateWithLifecycle()

    if (balanceInfo == null) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    TotalBalanceCard(
        balanceInfo = balanceInfo!!,
        callbacks = callbacks,
        valuesMasked = valuesMasked,
        modifier = modifier
    )
}
