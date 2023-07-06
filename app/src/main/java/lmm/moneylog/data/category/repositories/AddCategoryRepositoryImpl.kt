package lmm.moneylog.data.category.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.domain.category.Category
import lmm.moneylog.domain.category.addcategory.AddCategoryRepository

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
