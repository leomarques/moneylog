package lmm.moneylog.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    var showCloseIcon by remember { mutableStateOf(false) }

    Box {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(SpaceSize.SmallSpaceSize),
            query = text,
            onQueryChange = {
                text = it
                showCloseIcon = text.isNotEmpty()
                onSearch(it)
            },
            onSearch = onSearch,
            active = false,
            onActiveChange = {},
            placeholder = {
                Text(
                    text = stringResource(R.string.search_label),
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon_desc)
                )
            },
            trailingIcon = {
                if (showCloseIcon) {
                    IconButton(onClick = {
                        text = ""
                        showCloseIcon = false
                        onSearch("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_search_icon_desc)
                        )
                    }
                }
            }
        ) {}
    }
}

@Preview
@Composable
fun MySearchBarPreview() {
    MySearchBar {}
}
