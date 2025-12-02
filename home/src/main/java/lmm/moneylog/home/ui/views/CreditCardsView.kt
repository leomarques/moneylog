package lmm.moneylog.home.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import lmm.moneylog.home.ui.cards.CreditCardsCard
import lmm.moneylog.home.ui.cards.CreditCardsCardCallbacks
import lmm.moneylog.home.viewmodels.CreditCardsViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Self-contained view for credit cards section
 * Manages its own ViewModel and state
 *
 * @param callbacks Callbacks for credit card interactions
 * @param valuesMasked Whether to mask displayed values
 * @param modifier Modifier for the card container
 * @param viewModel ViewModel for credit cards data (injected by Koin)
 */
@Composable
fun CreditCardsView(
    callbacks: CreditCardsCardCallbacks,
    valuesMasked: Boolean,
    modifier: Modifier = Modifier,
    viewModel: CreditCardsViewModel = koinViewModel()
) {
    val creditCards by viewModel.creditCards.collectAsState()

    CreditCardsCard(
        creditCards = creditCards,
        callbacks = callbacks,
        valuesMasked = valuesMasked,
        modifier = modifier
    )
}
