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
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.outline_archive_24),
                contentDescription = stringResource(R.string.archive_desc)
            )
        }
    )
}

@Preview
@Composable
fun ArchiveActionButtonPreview() {
    ArchiveActionButton(onClick = {})
}
