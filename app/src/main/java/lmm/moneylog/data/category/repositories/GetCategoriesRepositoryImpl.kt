package lmm.moneylog.data.category.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.domain.category.Category
import lmm.moneylog.domain.category.getcategories.GetCategoriesRepository

class GetCategoriesRepositoryImpl(private val categoryDao: CategoryDao) :
    GetCategoriesRepository {

    override fun getCategories(): Flow<List<Category>> {
        return categoryDao.selectCategories().map { categoriesList ->
            convertEntity(categoriesList)
        }
    }

    private fun convertEntity(list: List<CategoryEntity>): List<Category> {
        return list.map { category ->
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
