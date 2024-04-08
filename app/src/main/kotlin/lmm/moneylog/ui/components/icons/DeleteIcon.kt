package lmm.moneylog.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun DeleteIcon(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    IconButton(
        onClick = onDeleteClick,
        content = {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.delete)
            )
        }
    )
}

@Preview
@Composable
fun DeleteIconPreview() {
    DeleteIcon {}
}
