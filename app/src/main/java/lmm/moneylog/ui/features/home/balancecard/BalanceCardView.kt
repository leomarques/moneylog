package lmm.moneylog.ui.features.home.balancecard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R
import lmm.moneylog.data.balancecard.BalanceCardModel
import lmm.moneylog.data.balancecard.BalanceCardViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BalanceCardView(
    onClick: (String) -> Unit,
    viewModel: BalanceCardViewModel = koinViewModel(),
) {
    val model by viewModel.balanceCardModel.observeAsState(
        BalanceCardModel(
            stringResource(R.string.home_placeholdervalue),
            stringResource(R.string.home_placeholdervalue),
            stringResource(R.string.home_placeholdervalue)
        )
    )

    BalanceCard(
        total = model.total,
        credit = model.credit,
        debt = model.debt,
        onClick = onClick
    )
}
