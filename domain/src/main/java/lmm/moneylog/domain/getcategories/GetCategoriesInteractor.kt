package lmm.moneylog.domain.getcategories

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Category

class GetCategoriesInteractor(private val repository: GetCategoriesRepository) {

    fun getCategories(): Flow<List<Category>> {
        return repository.getCategories()
    }
}
