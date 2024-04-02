package lmm.moneylog.data.category.repositories.interfaces

interface DeleteCategoryRepository {
    suspend fun delete(id: Int)
}
