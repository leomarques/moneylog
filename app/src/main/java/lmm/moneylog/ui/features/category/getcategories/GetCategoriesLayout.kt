package lmm.moneylog.ui.features.category.getcategories

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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import lmm.moneylog.R
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.SpaceSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetCategoriesLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    list: List<CategoryModel>,
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
                GetCategoriesContent(
                    list = list,
                    onItemClick = onItemClick
                )
            }
        }
    )
}

@Composable
fun GetCategoriesContent(
    onItemClick: (Int) -> Unit,
    list: List<CategoryModel>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var checked by remember {
            mutableStateOf(false)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.balancecard_income),
                style = MaterialTheme.typography.titleMedium
            )

            Switch(
                modifier = Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize),
                checked = checked,
                onCheckedChange = {
                    checked = !checked
                }
            )

            Text(
                text = stringResource(id = R.string.balancecard_outcome),
                style = MaterialTheme.typography.titleMedium
            )
        }

        LazyColumn {
            items(
                list
                    .filter {
                        it.isIncome == !checked
                    }
                    .reversed()
            ) { category ->
                CategoryItem(
                    onItemClick = onItemClick,
                    id = category.id,
                    name = category.name
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    onItemClick: (Int) -> Unit,
    id: Int,
    name: String
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
            .clickable { onItemClick(id) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
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

    Divider()
}

@Preview
@Composable
fun Preview() {
    GetCategoriesLayout(
        onArrowBackClick = { },
        onFabClick = { },
        list =
        listOf(
            CategoryModel(
                id = 0,
                name = "Alimentação",
                color = 0,
                isIncome = true
            ),
            CategoryModel(
                id = 0,
                name = "Moradia",
                color = 0,
                isIncome = true
            ),
            CategoryModel(
                id = 0,
                name = "Transporte",
                color = 0,
                isIncome = false
            )
        ),
        onItemClick = {}
    )
}
