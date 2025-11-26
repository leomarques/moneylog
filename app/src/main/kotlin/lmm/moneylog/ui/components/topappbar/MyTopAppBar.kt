package lmm.moneylog.ui.components.topappbar

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar(
    titleResourceId: Int,
    onArrowBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(titleResourceId))
        },
        navigationIcon = {
            IconButton(onClick = onArrowBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.arrowback_desc)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSearchClick,
                content = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )
        },
        windowInsets = WindowInsets(0.dp)
    )
}

@Preview
@Composable
private fun MyTopAppBarPreview() {
    MyTopAppBar(
        titleResourceId = R.string.app_name,
        onArrowBackClick = {},
        onSearchClick = {}
    )
}
