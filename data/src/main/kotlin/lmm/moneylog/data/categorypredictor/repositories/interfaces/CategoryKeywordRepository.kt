package lmm.moneylog.data.categorypredictor.repositories.interfaces

import kotlinx.coroutines.flow.Flow
import lmm.moneylog.data.categorypredictor.model.CategoryKeyword

interface CategoryKeywordRepository {
    fun getAllKeywords(): Flow<List<CategoryKeyword>>

    fun getKeywordsByCategory(categoryId: Int): Flow<List<CategoryKeyword>>

    suspend fun getKeywordById(id: Int): CategoryKeyword?

    suspend fun addKeyword(categoryId: Int, keyword: String): Long

    suspend fun addKeywords(categoryId: Int, keywords: List<String>)

    suspend fun deleteKeyword(id: Int)

    suspend fun deleteKeywordsByCategory(categoryId: Int)

    suspend fun predictCategory(text: String): Int?
}
