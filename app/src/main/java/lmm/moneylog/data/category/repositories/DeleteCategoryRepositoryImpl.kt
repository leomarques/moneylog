package lmm.moneylog.data.category.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.domain.category.deletecategory.DeleteCategoryRepository

class DeleteCategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : DeleteCategoryRepository {

    override suspend fun delete(id: Int) {
        withContext(coroutineDispatcherProvider.provide()) {
            categoryDao.delete(id)
        }
    }
}
