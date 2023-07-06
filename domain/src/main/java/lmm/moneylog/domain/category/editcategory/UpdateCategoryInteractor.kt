package lmm.moneylog.domain.category.editcategory

import lmm.moneylog.domain.category.Category

class UpdateCategoryInteractor(private val updateCategoryRepository: UpdateCategoryRepository) {

    suspend fun execute(category: Category) {
        updateCategoryRepository.update(category)
    }
}
