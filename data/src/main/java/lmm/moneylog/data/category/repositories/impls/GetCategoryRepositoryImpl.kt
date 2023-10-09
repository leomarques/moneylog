package lmm.moneylog.data.category.repositories.impls

import lmm.moneylog.data.category.Category
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.repositories.GetCategoryRepository

class GetCategoryRepositoryImpl(private val categoryDao: CategoryDao) :
    GetCategoryRepository {

    override suspend fun getCategoryById(id: Int): Category? {
        val category = categoryDao.selectCategory(id)
        return if (category != null) {
            with(category) {
                Category(
                    id = id,
                    name = name,
                    color = color
                )
            }
        } else {
            null
        }
    }
}
