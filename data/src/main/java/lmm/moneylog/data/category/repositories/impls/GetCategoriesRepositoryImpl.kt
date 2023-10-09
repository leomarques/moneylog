package lmm.moneylog.data.category.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.category.Category
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.repositories.GetCategoriesRepository

class GetCategoriesRepositoryImpl(private val categoryDao: CategoryDao) :
    GetCategoriesRepository {

    override fun getCategories(): Flow<List<Category>> {
        return categoryDao.selectCategories().map { categories ->
            categories.map { category ->
                with(category) {
                    Category(
                        id = id,
                        name = name,
                        color = color
                    )
                }
            }
        }
    }

    override suspend fun getCategoriesSuspend(): List<Category> {
        return categoryDao.selectCategoriesSuspend().map { categories ->
            with(categories) {
                Category(
                    id = id,
                    name = name,
                    color = color
                )
            }
        }
    }
}
