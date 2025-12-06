package lmm.moneylog.data.demo.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import lmm.moneylog.data.categorypredictor.model.CategoryKeyword
import lmm.moneylog.data.categorypredictor.repositories.interfaces.CategoryKeywordRepository

class InMemoryCategoryKeywordRepository : CategoryKeywordRepository {
    private val keywords = MutableStateFlow<List<CategoryKeyword>>(emptyList())
    private var nextId = 1

    override fun getAllKeywords(): Flow<List<CategoryKeyword>> = keywords

    override fun getKeywordsByCategory(categoryId: Int): Flow<List<CategoryKeyword>> =
        keywords.map { list -> list.filter { it.categoryId == categoryId } }

    override suspend fun getKeywordById(id: Int): CategoryKeyword? =
        keywords.value.firstOrNull { it.id == id }

    override suspend fun addKeyword(categoryId: Int, keyword: String): Long {
        val newKeyword =
            CategoryKeyword(
                id = nextId++,
                categoryId = categoryId,
                keyword = keyword
            )
        keywords.value = keywords.value + newKeyword
        return newKeyword.id.toLong()
    }

    override suspend fun addKeywords(categoryId: Int, keywords: List<String>) {
        val newKeywords =
            keywords.map { keyword ->
                CategoryKeyword(
                    id = nextId++,
                    categoryId = categoryId,
                    keyword = keyword
                )
            }
        this.keywords.value = this.keywords.value + newKeywords
    }

    override suspend fun deleteKeyword(id: Int) {
        keywords.value = keywords.value.filterNot { it.id == id }
    }

    override suspend fun deleteKeywordsByCategory(categoryId: Int) {
        keywords.value = keywords.value.filterNot { it.categoryId == categoryId }
    }

    override suspend fun predictCategory(text: String): Int? {
        val lowerText = text.lowercase()
        val matchedKeyword =
            keywords.value.firstOrNull { keyword ->
                lowerText.contains(keyword.keyword.lowercase())
            }
        return matchedKeyword?.categoryId
    }

    internal fun clear() {
        keywords.value = emptyList()
        nextId = 1
    }

    internal fun setInitialData(data: List<CategoryKeyword>) {
        keywords.value = data
        nextId = (data.maxOfOrNull { it.id } ?: 0) + 1
    }
}
