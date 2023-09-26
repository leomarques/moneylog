package lmm.moneylog.ui.features.category.categorydetail

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var model = CategoryDetailModel()

    init {
        viewModelScope.launch {
            savedStateHandle.getIdParam()?.let { id ->
                getCategoryRepository.getCategory(id)?.let { category ->
                    model = CategoryDetailModel(
                        name = mutableStateOf(category.name),
                        color = mutableLongStateOf(category.color),
                        isEdit = true,
                        id = category.id
                    )
                }
            }
        }
    }

    fun deleteCategory(id: Int) {
        viewModelScope.launch {
            deleteCategoryRepository.delete(id)
        }
    }

    fun onFabClick() {
        viewModelScope.launch {
            if (model.isEdit) {
                updateCategoryRepository.update(model.toCategory())
            } else {
                addCategoryRepository.save(model.toCategory())
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
