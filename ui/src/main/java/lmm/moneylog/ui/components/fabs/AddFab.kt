package lmm.moneylog.ui.components.fabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R

/**
 * A specialized floating action button with an Add icon
 *
 * @param onClick Callback when the FAB is clicked
 * @param modifier Modifier for the FAB
 */
@Composable
fun AddFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconFab(
        icon = Icons.Default.Add,
        onClick = onClick,
        modifier = modifier,
        contentDescription = stringResource(R.string.fab_desc)
    )
}

@Preview
@Composable
private fun AddFabPreview() {
    AddFab(
        onClick = {}
    )
}
