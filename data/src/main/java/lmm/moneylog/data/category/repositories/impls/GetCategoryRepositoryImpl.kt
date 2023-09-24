package lmm.moneylog.data.category.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.repositories.GetCategoryRepository
import lmm.moneylog.domain.category.Category

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
