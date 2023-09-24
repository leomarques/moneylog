package lmm.moneylog.data.category.repositories

interface DeleteCategoryRepository {
    suspend fun delete(id: Int)
}
