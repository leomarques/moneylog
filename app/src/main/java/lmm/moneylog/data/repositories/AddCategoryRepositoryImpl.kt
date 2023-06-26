package lmm.moneylog.data.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.category.CategoryDao
import lmm.moneylog.data.database.category.CategoryEntity
import lmm.moneylog.domain.addcategory.AddCategoryRepository
import lmm.moneylog.domain.models.Category

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
