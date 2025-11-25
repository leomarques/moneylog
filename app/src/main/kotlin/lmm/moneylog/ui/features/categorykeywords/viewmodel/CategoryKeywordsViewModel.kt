package lmm.moneylog.ui.features.categorykeywords.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.categorykeywords.model.CategoryKeywordsUIState
import lmm.moneylog.ui.features.categorykeywords.model.CategoryWithKeywords
import lmm.moneylog.ui.features.categorykeywords.model.KeywordItem

class CategoryKeywordsViewModel(
    private val getCategoriesRepository: GetCategoriesRepository,
    private val categoryKeywordRepository: CategoryKeywordRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryKeywordsUIState())
    val uiState: StateFlow<CategoryKeywordsUIState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            combine(
                getCategoriesRepository.getCategories(),
                categoryKeywordRepository.getAllKeywords()
            ) { categories, keywords ->
                categories.map { category ->
                    CategoryWithKeywords(
                        id = category.id,
                        name = category.name,
                        color = category.color.toComposeColor(),
                        keywords = keywords
                            .filter { it.categoryId == category.id }
                            .map { keyword ->
                                KeywordItem(
                                    id = keyword.id,
                                    keyword = keyword.keyword,
                                    categoryId = keyword.categoryId
                                )
                            }
                            .sortedBy { it.keyword }
                    )
                }.sortedBy { it.name }
            }.collect { categoriesWithKeywords ->
                _uiState.update {
                    it.copy(
                        categories = categoriesWithKeywords,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun selectCategory(category: CategoryWithKeywords?) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun showAddKeywordDialog() {
        _uiState.update { it.copy(showAddKeywordDialog = true) }
    }

    fun hideAddKeywordDialog() {
        _uiState.update { it.copy(showAddKeywordDialog = false) }
    }

    fun addKeyword(categoryId: Int, keyword: String) {
        viewModelScope.launch {
            try {
                categoryKeywordRepository.addKeyword(categoryId, keyword)
                hideAddKeywordDialog()
            } catch (e: Exception) {
                // Handle error - could add error state to UI
            }
        }
    }

    fun addKeywords(categoryId: Int, keywords: List<String>) {
        viewModelScope.launch {
            try {
                categoryKeywordRepository.addKeywords(categoryId, keywords)
                hideAddKeywordDialog()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun showDeleteConfirmDialog(keyword: KeywordItem) {
        _uiState.update {
            it.copy(
                showDeleteConfirmDialog = true,
                keywordToDelete = keyword
            )
        }
    }

    fun hideDeleteConfirmDialog() {
        _uiState.update {
            it.copy(
                showDeleteConfirmDialog = false,
                keywordToDelete = null
            )
        }
    }

    fun deleteKeyword() {
        viewModelScope.launch {
            val keyword = _uiState.value.keywordToDelete
            if (keyword != null) {
                try {
                    categoryKeywordRepository.deleteKeyword(keyword.id)
                    hideDeleteConfirmDialog()
                } catch (e: Exception) {
                    // Handle error
                }
            }
        }
    }

    fun clearCategoryKeywords(categoryId: Int) {
        viewModelScope.launch {
            try {
                categoryKeywordRepository.deleteKeywordsByCategory(categoryId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
