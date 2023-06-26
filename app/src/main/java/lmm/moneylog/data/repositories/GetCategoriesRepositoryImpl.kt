package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.category.CategoryDao
import lmm.moneylog.data.database.category.CategoryEntity
import lmm.moneylog.domain.getcategories.GetCategoriesRepository
import lmm.moneylog.domain.models.Category

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
