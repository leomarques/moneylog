package lmm.moneylog.domain.addcategory

import lmm.moneylog.domain.models.Category

class AddCategoryInteractor(private val addCategoryRepository: AddCategoryRepository) {
    suspend fun execute(category: Category) {
        addCategoryRepository.save(category)
    }
}
