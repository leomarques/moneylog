package lmm.moneylog.ui.features.categorykeywords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lmm.moneylog.R
import lmm.moneylog.ui.features.categorykeywords.components.AddKeywordDialog
import lmm.moneylog.ui.features.categorykeywords.components.DeleteKeywordDialog
import lmm.moneylog.ui.features.categorykeywords.model.CategoryWithKeywords
import lmm.moneylog.ui.features.categorykeywords.model.KeywordItem
import lmm.moneylog.ui.features.categorykeywords.viewmodel.CategoryKeywordsViewModel
import lmm.moneylog.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryKeywordsScreen(
    onArrowBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoryKeywordsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.keywords_title)) },
                windowInsets = WindowInsets(0.dp),
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.action_back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState.categories.isNotEmpty()) {
                FloatingActionButton(
                    onClick = { viewModel.showAddKeywordDialog() }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = stringResource(R.string.keywords_add_single)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.categories.isEmpty() -> {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.empty_categories_not_found),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    AllKeywordsList(
                        categories = uiState.categories,
                        onDeleteKeyword = { viewModel.showDeleteConfirmDialog(it) }
                    )
                }
            }
        }

        // Dialogs
        if (uiState.showAddKeywordDialog) {
            AddKeywordDialog(
                categories = uiState.categories,
                selectedCategoryId = uiState.selectedCategoryIdForAdd,
                onCategorySelected = { viewModel.selectCategoryForAdd(it) },
                onDismiss = { viewModel.hideAddKeywordDialog() },
                onAddKeyword = { categoryId, keyword ->
                    viewModel.addKeyword(categoryId, keyword)
                },
                onAddMultipleKeywords = { categoryId, keywords ->
                    viewModel.addKeywords(categoryId, keywords)
                }
            )
        }

        if (uiState.showDeleteConfirmDialog && uiState.keywordToDelete != null) {
            DeleteKeywordDialog(
                keyword = uiState.keywordToDelete!!.keyword,
                onDismiss = { viewModel.hideDeleteConfirmDialog() },
                onConfirm = { viewModel.deleteKeyword() }
            )
        }
    }
}

@Composable
private fun AllKeywordsList(
    categories: List<CategoryWithKeywords>,
    onDeleteKeyword: (KeywordItem) -> Unit,
    modifier: Modifier = Modifier
) {
    // Flatten all keywords with their category info
    val allKeywords =
        categories
            .flatMap { category ->
                category.keywords.map { keyword ->
                    keyword to category
                }
            }.sortedBy { it.first.keyword }

    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.keywords_match_info),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (allKeywords.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.empty_keywords_title),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = stringResource(R.string.empty_keywords_description),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        } else {
            items(allKeywords) { (keyword, category) ->
                KeywordListItem(
                    keyword = keyword,
                    category = category,
                    onDeleteKeyword = { onDeleteKeyword(keyword) }
                )
            }
        }
    }
}

@Composable
private fun KeywordListItem(
    keyword: KeywordItem,
    category: CategoryWithKeywords,
    onDeleteKeyword: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(category.color)
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = keyword.keyword,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onDeleteKeyword) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.action_delete),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryKeywordsScreen() {
    AppTheme {
        CategoryKeywordsScreen(onArrowBackClick = {})
    }
}
