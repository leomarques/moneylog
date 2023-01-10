package lmm.moneylog.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import lmm.moneylog.R

@Composable
fun MyFab(onClick: () -> Unit, icon: ImageVector) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            icon,
            contentDescription = stringResource(R.string.fab_desc)
        )
    }
}
