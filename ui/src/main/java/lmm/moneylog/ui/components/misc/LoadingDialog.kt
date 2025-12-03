package lmm.moneylog.ui.components.misc

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    Dialog(onDismissRequest = {}) {
        Box(modifier) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun LoadingDialogPreview() {
    LoadingDialog()
}
