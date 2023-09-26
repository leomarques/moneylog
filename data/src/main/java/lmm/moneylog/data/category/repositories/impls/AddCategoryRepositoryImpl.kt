package lmm.moneylog.data.category.repositories.impls

import kotlinx.coroutines.withContext
import lmm.moneylog.data.category.Category
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.category.repositories.AddCategoryRepository
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider

class AddCategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : AddCategoryRepository {

    override suspend fun save(category: Category) {
        withContext(coroutineDispatcherProvider.provide()) {
            categoryDao.insert(
                with(category) {
                    CategoryEntity(
                        name = name,
                        color = color
                    )
                }
            )
        }
    }
}
