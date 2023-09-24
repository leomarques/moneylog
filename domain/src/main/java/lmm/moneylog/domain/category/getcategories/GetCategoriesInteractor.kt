package lmm.moneylog.domain.category.getcategories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.category.Category

class GetCategoriesInteractor(private val repository: GetCategoriesRepository) {

    fun getCategories(): Flow<List<Category>> {
        return repository.getCategories()
    }
}