package lmm.moneylog.data.category.repositories.impls

import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.AddCategoryRepository

class AddCategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : AddCategoryRepository {
    override suspend fun save(category: Category) {
        categoryDao.insert(
            with(category) {
                CategoryEntity(
                    name = name,
                    color = color,
                    isIncome = isIncome
                )
            }
        )
    }
}
