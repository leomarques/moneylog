package lmm.moneylog.ui.features.category.getccategories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.data.category.Category
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetCategoriesLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    model: GetCategoriesModel,
    onItemClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.getcategories_topbar))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detailtransaction_arrowback_desc)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            MyFab(
                onClick = onFabClick,
                icon = Icons.Default.Add
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { paddingValues ->
            Surface(Modifier.padding(top = paddingValues.calculateTopPadding())) {
                Content(
                    model = model,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
fun Content(
    model: GetCategoriesModel,
    onItemClick: (Int) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        LazyColumn {
            items(model.categories.reversed()) { category ->
                CategoryItem(
                    category = category,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .height(SpaceSize.ListItemHeight)
            .padding(
                vertical = SpaceSize.SmallSpaceSize,
                horizontal = SpaceSize.DefaultSpaceSize
            )
            .clickable { onItemClick(category.id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(category) {
            Text(
                text = name.ifEmpty {
                    stringResource(R.string.gettransactions_nodescription)
                },
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                color = if (name.isEmpty()) {
                    Color.Gray
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }

    Divider()
}

@Preview
@Composable
fun Preview() {
    GetCategoriesLayout(
        onArrowBackClick = { },
        onFabClick = { },
        model = GetCategoriesModel(
            listOf(
                Category(
                    id = 0,
                    name = "Alimentação",
                    color = 0
                ),
                Category(
                    id = 0,
                    name = "Moradia",
                    color = 0
                ),
                Category(
                    id = 0,
                    name = "Transporte",
                    color = 0
                )
            )
        ),
        onItemClick = {}
    )
}
