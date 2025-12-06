package lmm.moneylog.ui.components.fabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R

/**
 * A customizable floating action button with an icon
 *
 * @param icon The icon to display in the FAB
 * @param onClick Callback when the FAB is clicked
 * @param modifier Modifier for the FAB
 * @param contentDescription Optional accessibility description for the icon
 */
@Composable
fun IconFab(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = stringResource(R.string.ui_cd_fab)
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun IconFabPreview() {
    IconFab(
        onClick = {},
        icon = Icons.Default.Add
    )
}
