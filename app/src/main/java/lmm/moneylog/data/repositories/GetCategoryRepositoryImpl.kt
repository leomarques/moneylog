package lmm.moneylog.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.database.category.CategoryDao
import lmm.moneylog.domain.getcategory.GetCategoryRepository
import lmm.moneylog.domain.models.Category

class GetCategoryRepositoryImpl(private val categoryDao: CategoryDao) :
    GetCategoryRepository {

    override fun getCategory(id: Int): Flow<Category?> {
        return categoryDao.selectCategory(id).map { entity ->
            if (entity != null) {
                with(entity) {
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
}
