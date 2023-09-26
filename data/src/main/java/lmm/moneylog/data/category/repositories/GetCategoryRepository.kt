package lmm.moneylog.data.category.repositories

import lmm.moneylog.data.category.Category

interface GetCategoryRepository {
    suspend fun getCategory(id: Int): Category?
}
