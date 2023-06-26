package lmm.moneylog.domain.getcategory

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.domain.models.Category

class GetCategoryInteractor(private val getCategoryRepository: GetCategoryRepository) {

    fun getCategory(id: Int): Flow<Category?> {
        return getCategoryRepository.getCategory(id)
    }
}
