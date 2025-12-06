package lmm.moneylog.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.ui.R

@Composable
fun ArrowBackIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    IconButton(
        modifier = modifier,
        onClick = {
            focusManager.clearFocus()
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.ui_cd_arrow_back)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArrowBackIconPreview() {
    ArrowBackIcon(onClick = {})
}
