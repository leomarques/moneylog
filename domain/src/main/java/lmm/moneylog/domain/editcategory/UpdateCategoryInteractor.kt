package lmm.moneylog.domain.editcategory

import lmm.moneylog.domain.models.Category

class UpdateCategoryInteractor(private val updateCategoryRepository: UpdateCategoryRepository) {

    suspend fun execute(category: Category) {
        updateCategoryRepository.update(category)
    }
}
