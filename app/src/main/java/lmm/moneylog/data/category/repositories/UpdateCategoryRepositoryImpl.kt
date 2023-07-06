package lmm.moneylog.data.category.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.category.database.CategoryDao
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.coroutine.CoroutineDispatcherProvider
import lmm.moneylog.domain.category.Category
import lmm.moneylog.domain.category.editcategory.UpdateCategoryRepository

class UpdateCategoryRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) :
    UpdateCategoryRepository {

    override suspend fun update(category: Category) {
        withContext(coroutineDispatcherProvider.provide()) {
            categoryDao.update(
                with(category) {
                    CategoryEntity(
                        name = name,
                        color = color
                    ).also {
                        it.id = id
                    }
                }
            )
        }
    }
}
