package lmm.moneylog.ui.features.category.detail.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.R
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.AddCategoryRepository
import lmm.moneylog.data.category.repositories.interfaces.DeleteCategoryRepository
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository
import lmm.moneylog.data.category.repositories.interfaces.UpdateCategoryRepository
import lmm.moneylog.ui.extensions.getIdParam
import lmm.moneylog.ui.extensions.toComposeColor
import lmm.moneylog.ui.features.category.detail.model.CategoryDetailUIState

class CategoryDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getCategoriesRepository: GetCategoriesRepository,
    private val addCategoryRepository: AddCategoryRepository,
    private val updateCategoryRepository: UpdateCategoryRepository,
    private val deleteCategoryRepository: DeleteCategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryDetailUIState())
    val uiState: StateFlow<CategoryDetailUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getCategoriesRepository.getCategoryById(id)?.let { category ->
                    _uiState.update {
                        CategoryDetailUIState(
                            id = category.id,
                            name = category.name,
                            color = category.color.toComposeColor(),
                            isIncome = category.isIncome,
                            isEdit = true
                        )
                    }
                }
            }
        }
    }

    fun deleteCategory() {
        viewModelScope.launch {
            deleteCategoryRepository.delete(_uiState.value.id)
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name.trim()) }
    }

    fun onIncomeChange(isIncome: Boolean) {
        _uiState.update { it.copy(isIncome = isIncome) }
    }

    fun onColorPick(color: Color) {
        _uiState.update { it.copy(color = color) }
    }

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val state = _uiState.value
        if (state.name.isEmpty()) {
            onError(R.string.detail_no_name)
            return
        }

        _uiState.update { it.copy(showFab = false) }

        viewModelScope.launch {
            if (state.isEdit) {
                updateCategoryRepository.update(state.toCategory())
            } else {
                addCategoryRepository.save(state.toCategory())
            }

            onSuccess()
        }
    }
}

fun CategoryDetailUIState.toCategory() =
    Category(
        id = id,
        name = name,
        color = color.value.toLong(),
        isIncome = isIncome
    )
