package lmm.moneylog.data.category.repositories.interfaces

import lmm.moneylog.data.category.model.Category

interface AddCategoryRepository {
    suspend fun save(category: Category)
}
