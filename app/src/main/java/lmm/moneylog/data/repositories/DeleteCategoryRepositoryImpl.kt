package lmm.moneylog.data.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.category.CategoryDao
import lmm.moneylog.domain.deletecategory.DeleteCategoryRepository

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
