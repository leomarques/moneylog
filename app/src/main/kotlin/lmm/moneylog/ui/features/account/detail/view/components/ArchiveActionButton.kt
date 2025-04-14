package lmm.moneylog.ui.features.account.detail.view.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun ArchiveActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.outline_archive_24),
                contentDescription = stringResource(R.string.archive_desc)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ArchiveActionButtonPreview() {
    ArchiveActionButton(onClick = {})
}
