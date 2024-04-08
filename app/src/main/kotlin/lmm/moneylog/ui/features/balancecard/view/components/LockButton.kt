package lmm.moneylog.ui.features.balancecard.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.Size

@Composable
fun LockButton(
    onHideClick: () -> Unit,
    isHidden: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onHideClick,
        modifier = Modifier.padding(vertical = Size.XSmallSpaceSize)
    ) {
        Icon(
            imageVector = if (isHidden) Icons.Filled.Lock else Icons.Outlined.Lock,
            contentDescription = stringResource(R.string.lock_desc)
        )
    }
}

@Preview
@Composable
fun LockButtonPreview() {
    LockButton(
        onHideClick = {},
        isHidden = false
    )
}
