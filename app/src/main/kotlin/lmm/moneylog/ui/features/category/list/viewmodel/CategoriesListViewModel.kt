package lmm.moneylog.ui.features.category.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.category.list.model.CategoriesListUIState
import lmm.moneylog.ui.features.category.list.model.CategoryModel

class CategoriesListViewModel(private val getCategoriesRepository: GetCategoriesRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesListUIState())
    val uiState: StateFlow<CategoriesListUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCategoriesRepository.getCategories().collect { categories ->
                _uiState.update {
                    CategoriesListUIState(
                        categories
                            .toCategoryModelList()
                            .reversed()
                    )
                }
            }
        }
    }
}

fun List<Category>.toCategoryModelList(): List<CategoryModel> {
    return this.map {
        CategoryModel(
            id = it.id,
            name = it.name,
            color = it.color.toComposeColor(),
            isIncome = it.isIncome
        )
    }
}
