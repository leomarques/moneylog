package lmm.moneylog.data.repositories

import kotlinx.coroutines.withContext
import lmm.moneylog.data.CoroutineDispatcherProvider
import lmm.moneylog.data.database.category.CategoryDao
import lmm.moneylog.data.database.category.CategoryEntity
import lmm.moneylog.domain.editcategory.UpdateCategoryRepository
import lmm.moneylog.domain.models.Category

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
