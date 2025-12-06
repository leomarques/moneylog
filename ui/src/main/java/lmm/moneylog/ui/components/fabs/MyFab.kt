package lmm.moneylog.ui.components.fabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.ui.R

@Composable
fun MyFab(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier.testTag("Fab"),
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        elevation =
            FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                hoveredElevation = 8.dp
            )
    ) {
        Icon(
            icon,
            contentDescription = stringResource(R.string.ui_cd_fab)
        )
    }
}

@Preview
@Composable
private fun MyFabPreview() {
    MyFab(
        onClick = {},
        icon = Icons.Default.Add
    )
}
