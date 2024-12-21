package lmm.moneylog.ui.components.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheet(
    onConfirm: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    list: List<Pair<String, Color>>,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest
    ) {
        BottomSheetContent(
            list = list,
            onConfirm = onConfirm,
            onDismiss = onDismissRequest,
        )
    }
}
