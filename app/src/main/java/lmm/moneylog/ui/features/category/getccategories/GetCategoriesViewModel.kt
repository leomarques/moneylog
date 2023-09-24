package lmm.moneylog.ui.features.category.getccategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lmm.moneylog.data.category.repositories.GetCategoriesRepository

class GetCategoriesViewModel(private val getCategoriesRepository: GetCategoriesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(GetCategoriesModel())
    val uiState: StateFlow<GetCategoriesModel> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCategoriesRepository.getCategories().collect { categories ->
                _uiState.update {
                    GetCategoriesModel(categories)
                }
            }
        }
    }
}
