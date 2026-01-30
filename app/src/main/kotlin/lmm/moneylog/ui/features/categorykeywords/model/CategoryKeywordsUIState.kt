package lmm.moneylog.ui.features.categorykeywords.model

import androidx.compose.ui.graphics.Color

data class CategoryKeywordsUIState(
    val categories: List<CategoryWithKeywords> = emptyList(),
    val isLoading: Boolean = false,
    val showAddKeywordDialog: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val keywordToDelete: KeywordItem? = null,
    val selectedCategoryIdForAdd: Int? = null
)

data class CategoryWithKeywords(
    val id: Int,
    val name: String,
    val color: Color,
    val keywords: List<KeywordItem>
)

data class KeywordItem(
    val id: Int,
    val keyword: String,
    val categoryId: Int
)
