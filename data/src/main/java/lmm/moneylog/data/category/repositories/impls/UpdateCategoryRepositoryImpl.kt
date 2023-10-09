package lmm.moneylog.data.category.repositories.impls

import lmm.moneylog.data.category.Category
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.category.repositories.UpdateCategoryRepository

class UpdateCategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) :
    UpdateCategoryRepository {

    override suspend fun update(category: Category) {
        categoryDao.update(
            with(category) {
                CategoryEntity(
                    name = name,
                    color = color,
                    isIncome = isIncome
                ).also {
                    it.id = id
                }
            }
        )
    }
}
