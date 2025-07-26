package lmm.moneylog.data.category.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.model.Category
import lmm.moneylog.data.category.repositories.interfaces.GetCategoriesRepository

class GetCategoriesRepositoryImpl(
    private val categoryDao: CategoryDao
) : GetCategoriesRepository {
    override suspend fun getCategoryById(id: Int): Category? {
        val category = categoryDao.selectCategory(id)
        return if (category != null) {
            with(category) {
                Category(
                    id = id,
                    name = name,
                    color = color,
                    isIncome = isIncome
                )
            }
        } else {
            null
        }
    }

    override fun getCategories(): Flow<List<Category>> =
        categoryDao.selectCategories().map { categories ->
            categories.map { category ->
                with(category) {
                    Category(
                        id = id,
                        name = name,
                        color = color,
                        isIncome = isIncome
                    )
                }
            }
        }

    override suspend fun getCategoriesSuspend(): List<Category> =
        categoryDao.selectCategoriesSuspend().map { categories ->
            with(categories) {
                Category(
                    id = id,
                    name = name,
                    color = color,
                    isIncome = isIncome
                )
            }
        }
}
