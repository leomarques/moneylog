package lmm.moneylog.ui.components.fabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun HideFab(
    icon: ImageVector,
    onClick: () -> Unit
) {
    var showFab by remember { mutableStateOf(true) }

    if (showFab) {
        FloatingActionButton(
            modifier = Modifier.testTag("Fab"),
            onClick = {
                showFab = false
                onClick()
            }
        ) {
            Icon(
                icon,
                contentDescription = stringResource(R.string.fab_desc)
            )
        }
    }
}

@Preview
@Composable
fun HideFabPreview() {
    HideFab(
        onClick = {},
        icon = Icons.Default.Add
    )
}
