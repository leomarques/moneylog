package lmm.moneylog.ui.features.category.categorydetail

import androidx.compose.runtime.mutableStateOf
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
import lmm.moneylog.data.category.Category
import lmm.moneylog.data.category.repositories.AddCategoryRepository
import lmm.moneylog.data.category.repositories.DeleteCategoryRepository
import lmm.moneylog.data.category.repositories.GetCategoriesRepository
import lmm.moneylog.data.category.repositories.UpdateCategoryRepository
import lmm.moneylog.ui.features.transaction.transactiondetail.getIdParam

class CategoryDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getCategoriesRepository: GetCategoriesRepository,
    private val addCategoryRepository: AddCategoryRepository,
    private val updateCategoryRepository: UpdateCategoryRepository,
    private val deleteCategoryRepository: DeleteCategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryDetailModel())
    val uiState: StateFlow<CategoryDetailModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getCategoriesRepository.getCategoryById(id)?.let { category ->
                    _uiState.update {
                        CategoryDetailModel(
                            name = mutableStateOf(category.name),
                            color = Color(category.color.toULong()),
                            isEdit = true,
                            id = category.id,
                            isIncome = mutableStateOf(category.isIncome)
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

    fun onFabClick(
        onSuccess: () -> Unit,
        onError: (Int) -> Unit
    ) {
        val name = _uiState.value.name.value.trim()
        if (name.isEmpty()) {
            onError(R.string.detail_no_name)
        } else {
            viewModelScope.launch {
                if (_uiState.value.isEdit) {
                    updateCategoryRepository.update(_uiState.value.toCategory())
                } else {
                    addCategoryRepository.save(_uiState.value.toCategory())
                }
                onSuccess()
            }
        }
    }

    fun onColorPicked(color: Color) {
        _uiState.update {
            it.copy(
                color = color
            )
        }
    }
}

fun CategoryDetailModel.toCategory() =
    Category(
        id = id,
        name = name.value.trim(),
        color = color.value.toLong(),
        isIncome = isIncome.value
    )
