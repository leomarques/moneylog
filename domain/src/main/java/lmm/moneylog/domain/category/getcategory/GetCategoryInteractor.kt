package lmm.moneylog.domain.category.getcategory

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.category.Category

class GetCategoryInteractor(private val getCategoryRepository: GetCategoryRepository) {

    fun getCategory(id: Int): Flow<Category?> {
        return getCategoryRepository.getCategory(id)
    }
}
