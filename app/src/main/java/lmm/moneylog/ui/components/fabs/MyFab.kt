package lmm.moneylog.ui.components.fabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun MyFab(
    onClick: () -> Unit,
    icon: ImageVector
) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            icon,
            contentDescription = stringResource(R.string.fab_desc)
        )
    }
}

@Preview
@Composable
fun MyFabPreview() {
    MyFab(
        onClick = {},
        icon = Icons.Default.Add
    )
}
