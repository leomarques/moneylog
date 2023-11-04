package lmm.moneylog.ui.features.category.list

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
import androidx.compose.runtime.mutableStateOf
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
import lmm.moneylog.ui.components.fabs.MyFab
import lmm.moneylog.ui.components.misc.EmptyState
import lmm.moneylog.ui.components.misc.MyCircle
import lmm.moneylog.ui.theme.DarkRed
import lmm.moneylog.ui.theme.Size

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetCategoriesLayout(
    onArrowBackClick: () -> Unit,
    onFabClick: () -> Unit,
    list: List<CategoryModel>,
    onItemClick: (Int) -> Unit
) {
    var showFab by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.categories))
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.arrowback_desc)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (showFab) {
                MyFab(
                    onClick = {
                        showFab = false
                        onFabClick()
                    },
                    icon = Icons.Default.Add
                )
            }
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
        stringResource(id = R.string.incomes),
        stringResource(id = R.string.outcomes)
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            modifier = Modifier.padding(bottom = Size.DefaultSpaceSize),
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
                        showDivider = index != filteredList.size - 1
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
            .height(Size.TwoLinesListItemHeight)
            .padding(
                vertical = Size.DefaultSpaceSize,
                horizontal = Size.DefaultSpaceSize
            )
            .clickable { onItemClick(id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyCircle(
            color = color,
            letters = name
        )

        Text(
            modifier = Modifier.padding(start = Size.DefaultSpaceSize),
            text = name.ifEmpty {
                stringResource(R.string.no_description)
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
        Divider(Modifier.padding(horizontal = Size.DefaultSpaceSize))
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
