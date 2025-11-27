package lmm.moneylog.ui.components.fabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MyFab(
        icon = Icons.Default.Add,
        onClick = onClick,
        modifier = modifier
    )
}

@Preview
@Composable
private fun AddFabPreview() {
    AddFab(
        onClick = {},
    )
}
