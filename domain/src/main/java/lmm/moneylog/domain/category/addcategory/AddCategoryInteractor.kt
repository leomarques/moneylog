package lmm.moneylog.domain.category.addcategory

import lmm.moneylog.domain.category.Category

class AddCategoryInteractor(private val addCategoryRepository: AddCategoryRepository) {
    suspend fun execute(category: Category) {
        addCategoryRepository.save(category)
    }
}
