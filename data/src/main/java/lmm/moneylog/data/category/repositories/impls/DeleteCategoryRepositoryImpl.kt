package lmm.moneylog.data.category.repositories.impls

import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.repositories.interfaces.DeleteCategoryRepository

class DeleteCategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : DeleteCategoryRepository {

    override suspend fun delete(id: Int) {
        categoryDao.delete(id)
    }
}
