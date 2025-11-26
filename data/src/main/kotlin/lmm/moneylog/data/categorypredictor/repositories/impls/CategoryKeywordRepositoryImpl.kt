package lmm.moneylog.data.categorypredictor.repositories.impls

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.categorypredictor.database.CategoryKeywordDao
import lmm.moneylog.data.categorypredictor.database.CategoryKeywordEntity
import lmm.moneylog.data.categorypredictor.model.CategoryKeyword
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository

class CategoryKeywordRepositoryImpl(
    private val categoryKeywordDao: CategoryKeywordDao
) : CategoryKeywordRepository {
    override fun getAllKeywords(): Flow<List<CategoryKeyword>> =
        categoryKeywordDao.getAllKeywords().map { entities ->
            entities.map { it.toCategoryKeyword() }
        }

    override fun getKeywordsByCategory(categoryId: Int): Flow<List<CategoryKeyword>> =
        categoryKeywordDao.getKeywordsByCategory(categoryId).map { entities ->
            entities.map { it.toCategoryKeyword() }
        }

    override suspend fun getKeywordById(id: Int): CategoryKeyword? =
        categoryKeywordDao.getKeywordById(id)?.toCategoryKeyword()

    override suspend fun addKeyword(
        categoryId: Int,
        keyword: String
    ): Long {
        val normalizedKeyword = keyword.trim().lowercase()
        if (normalizedKeyword.isBlank()) {
            throw IllegalArgumentException("Keyword cannot be empty")
        }

        val entity =
            CategoryKeywordEntity(
                categoryId = categoryId,
                keyword = normalizedKeyword
            )
        return categoryKeywordDao.insert(entity)
    }

    override suspend fun addKeywords(
        categoryId: Int,
        keywords: List<String>
    ) {
        val entities =
            keywords
                .map { it.trim().lowercase() }
                .filter { it.isNotBlank() }
                .distinct()
                .map { keyword ->
                    CategoryKeywordEntity(
                        categoryId = categoryId,
                        keyword = keyword
                    )
                }

        if (entities.isNotEmpty()) {
            categoryKeywordDao.insertAll(entities)
        }
    }

    override suspend fun deleteKeyword(id: Int) {
        categoryKeywordDao.deleteById(id)
    }

    override suspend fun deleteKeywordsByCategory(categoryId: Int) {
        categoryKeywordDao.deleteByCategory(categoryId)
    }

    override suspend fun predictCategory(text: String): Int? {
        if (text.isBlank()) return null

        val normalizedText = text.trim().lowercase()
        val allKeywords = categoryKeywordDao.getAllKeywords().first()

        // Find keywords that match the text, prioritizing longer matches
        val matches =
            allKeywords
                .filter { keyword ->
                    normalizedText.contains(keyword.keyword)
                }.sortedByDescending { it.keyword.length }

        // Return the category of the first (longest) match
        return matches.firstOrNull()?.categoryId
    }

    private fun CategoryKeywordEntity.toCategoryKeyword() =
        CategoryKeyword(
            id = id,
            categoryId = categoryId,
            keyword = keyword
        )
}
