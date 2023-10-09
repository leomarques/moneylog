package lmm.moneylog.ui.features.category.categorydetail

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.category.Category
import lmm.moneylog.data.category.repositories.AddCategoryRepository
import lmm.moneylog.data.category.repositories.DeleteCategoryRepository
import lmm.moneylog.data.category.repositories.GetCategoryRepository
import lmm.moneylog.data.category.repositories.UpdateCategoryRepository
import lmm.moneylog.ui.features.transaction.transactiondetail.getIdParam

class CategoryDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getCategoryRepository: GetCategoryRepository,
    private val addCategoryRepository: AddCategoryRepository,
    private val updateCategoryRepository: UpdateCategoryRepository,
    private val deleteCategoryRepository: DeleteCategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryDetailModel())
    val uiState: StateFlow<CategoryDetailModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getCategoryRepository.getCategoryById(id)?.let { category ->
                    _uiState.update {
                        CategoryDetailModel(
                            name = mutableStateOf(category.name),
                            color = mutableLongStateOf(category.color),
                            isEdit = true,
                            id = category.id
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

    fun onFabClick() {
        viewModelScope.launch {
            if (_uiState.value.isEdit) {
                updateCategoryRepository.update(_uiState.value.toCategory())
            } else {
                addCategoryRepository.save(_uiState.value.toCategory())
            }
        }
    }
}

fun CategoryDetailModel.toCategory() =
    Category(
        id = id,
        name = name.value,
        color = color.value
    )
