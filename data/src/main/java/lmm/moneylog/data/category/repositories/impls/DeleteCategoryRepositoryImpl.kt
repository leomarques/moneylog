package lmm.moneylog.data.category.repositories.impls

import kotlinx.coroutines.withContext
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.repositories.DeleteCategoryRepository
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider

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
