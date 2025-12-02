package lmm.moneylog.home.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import lmm.moneylog.home.ui.HomeHeader
import lmm.moneylog.home.viewmodels.HeaderViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Self-contained view for the home screen header
 * Manages its own ViewModel to get the period information
 *
 * @param valuesMasked Whether values are currently masked
 * @param onMaskToggle Callback when mask toggle is clicked
 * @param modifier Modifier for the header container
 * @param viewModel ViewModel for period information (injected by Koin)
 */
@Composable
fun HeaderView(
    valuesMasked: Boolean,
    onMaskToggle: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HeaderViewModel = koinViewModel()
) {
    val periodInfo by viewModel.periodInfo.collectAsState()

    HomeHeader(
        periodInfo = periodInfo,
        valuesMasked = valuesMasked,
        onMaskToggle = onMaskToggle,
        modifier = modifier.fillMaxWidth()
    )
}
