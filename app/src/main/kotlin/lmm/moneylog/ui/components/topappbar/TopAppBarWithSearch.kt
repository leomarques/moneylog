package lmm.moneylog.ui.components.topappbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R

@Composable
fun TopAppBarWithSearch(
    onSearchTextChanged: (String) -> Unit,
    onArrowBackClick: () -> Unit,
    filter: String,
    titleResourceId: Int
) {
    val showTopBar = remember { mutableStateOf(true) }

    if (showTopBar.value) {
        MyTopAppBar(
            titleResourceId = titleResourceId,
            onArrowBackClick = onArrowBackClick,
            onSearchClick = { showTopBar.value = false }
        )
    } else {
        SearchTopBar(
            searchText = filter,
            placeholderText = stringResource(R.string.search_placeholder),
            onSearchTextChanged = onSearchTextChanged,
            onClearClick = {
                onSearchTextChanged("")
                showTopBar.value = true
            },
            onArrowBackClick = onArrowBackClick
        )
    }
}

@Preview
@Composable
private fun TopAppBarWithSearchPreview() {
    TopAppBarWithSearch(
        onSearchTextChanged = {},
        onArrowBackClick = {},
        filter = "",
        titleResourceId = R.string.app_name
    )
}
