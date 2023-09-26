package lmm.moneylog.data.category.repositories

import lmm.moneylog.data.category.Category

interface AddCategoryRepository {
    suspend fun save(category: Category)
}
