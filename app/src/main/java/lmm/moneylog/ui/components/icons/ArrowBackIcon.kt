package lmm.moneylog.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun ArrowBackIcon(onClick: () -> Unit) {
    val focusManager = LocalFocusManager.current

    IconButton(
        onClick = {
            focusManager.clearFocus()
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.arrowback_desc)
        )
    }
}

@Preview
@Composable
fun ArrowBackIconPreview() {
    ArrowBackIcon(onClick = {})
}
