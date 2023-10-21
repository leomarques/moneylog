package lmm.moneylog.ui.features.category.getcategories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.components.EmptyState
import lmm.moneylog.ui.components.MyCircle
import lmm.moneylog.ui.components.MyFab
import lmm.moneylog.ui.theme.DarkRed
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
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(id = R.string.balancecard_income),
        stringResource(id = R.string.balancecard_outcome)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            modifier = Modifier.padding(bottom = SpaceSize.DefaultSpaceSize),
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        val filteredList = list.filter { it.isIncome == (tabIndex == 0) }

        if (filteredList.isNotEmpty()) {
            LazyColumn(
                Modifier.background(
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    shape = RoundedCornerShape(20.dp)
                )
            ) {
                itemsIndexed(filteredList.reversed()) { index, category ->
                    CategoryItem(
                        onItemClick = onItemClick,
                        id = category.id,
                        name = category.name,
                        color = category.color,
                        showDivider = index != list.size - 1
                    )
                }
            }
        } else {
            EmptyState(
                title = stringResource(id = R.string.empty_categories_title),
                description = stringResource(id = R.string.empty_categories_desc)
            )
        }
    }
}

@Composable
fun CategoryItem(
    onItemClick: (Int) -> Unit,
    id: Int,
    name: String,
    color: Color,
    showDivider: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SpaceSize.TwoLinesListItemHeight)
            .padding(
                vertical = SpaceSize.DefaultSpaceSize,
                horizontal = SpaceSize.DefaultSpaceSize
            )
            .clickable { onItemClick(id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyCircle(
            color = color,
            letters = name
        )

        Text(
            modifier = Modifier.padding(start = SpaceSize.DefaultSpaceSize),
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

    if (showDivider) {
        Divider(Modifier.padding(horizontal = SpaceSize.DefaultSpaceSize))
    }
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
                color = DarkRed,
                isIncome = true
            ),
            CategoryModel(
                id = 0,
                name = "Moradia",
                color = DarkRed,
                isIncome = true
            ),
            CategoryModel(
                id = 0,
                name = "Transporte",
                color = DarkRed,
                isIncome = false
            )
        ),
        onItemClick = {}
    )
}
