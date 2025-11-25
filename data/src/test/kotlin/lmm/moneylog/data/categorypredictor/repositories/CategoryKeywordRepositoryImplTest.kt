package lmm.moneylog.data.categorypredictor.repositories

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import lmm.moneylog.data.categorypredictor.database.CategoryKeywordDao
import lmm.moneylog.data.categorypredictor.database.CategoryKeywordEntity
import lmm.moneylog.data.categorypredictor.repositories.impls.CategoryKeywordRepositoryImpl
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CategoryKeywordRepositoryImplTest {
    private lateinit var dao: CategoryKeywordDao
    private lateinit var repository: CategoryKeywordRepositoryImpl

    @Before
    fun setup() {
        dao = mockk()
        repository = CategoryKeywordRepositoryImpl(dao)
    }

    @Test
    fun `getAllKeywords should return all keywords`() = runBlocking {
        val entities = listOf(
            CategoryKeywordEntity(1, 1, "restaurant"),
            CategoryKeywordEntity(2, 1, "food"),
            CategoryKeywordEntity(3, 2, "uber")
        )
        every { dao.getAllKeywords() } returns flowOf(entities)

        val result = repository.getAllKeywords().first()

        assertEquals(3, result.size)
        assertEquals("restaurant", result[0].keyword)
        assertEquals("food", result[1].keyword)
        assertEquals("uber", result[2].keyword)
    }

    @Test
    fun `getKeywordsByCategory should return keywords for specific category`() = runBlocking {
        val entities = listOf(
            CategoryKeywordEntity(1, 1, "restaurant"),
            CategoryKeywordEntity(2, 1, "food")
        )
        every { dao.getKeywordsByCategory(1) } returns flowOf(entities)

        val result = repository.getKeywordsByCategory(1).first()

        assertEquals(2, result.size)
        assertEquals(1, result[0].categoryId)
        assertEquals(1, result[1].categoryId)
    }

    @Test
    fun `addKeyword should normalize and save keyword`() = runBlocking {
        coEvery { dao.insert(any()) } returns 1L

        val id = repository.addKeyword(1, "  RESTAURANT  ")

        coVerify {
            dao.insert(
                match {
                    it.categoryId == 1 && it.keyword == "restaurant"
                }
            )
        }
        assertEquals(1L, id)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `addKeyword should throw exception for empty keyword`(): Unit = runBlocking {
        repository.addKeyword(1, "   ")
    }

    @Test
    fun `addKeywords should normalize and save multiple keywords`() = runBlocking {
        coEvery { dao.insertAll(any()) } returns Unit

        repository.addKeywords(1, listOf("RESTAURANT", "  Food  ", "dining"))

        coVerify {
            dao.insertAll(
                match { entities ->
                    entities.size == 3 &&
                            entities[0].keyword == "restaurant" &&
                            entities[1].keyword == "food" &&
                            entities[2].keyword == "dining"
                }
            )
        }
    }

    @Test
    fun `addKeywords should filter blank keywords and remove duplicates`() = runBlocking {
        coEvery { dao.insertAll(any()) } returns Unit

        repository.addKeywords(1, listOf("restaurant", "  ", "restaurant", "food"))

        coVerify {
            dao.insertAll(
                match { entities ->
                    entities.size == 2 &&
                            entities[0].keyword == "restaurant" &&
                            entities[1].keyword == "food"
                }
            )
        }
    }

    @Test
    fun `predictCategory should return null for blank text`() = runBlocking {
        val result = repository.predictCategory("   ")
        assertNull(result)
    }

    @Test
    fun `predictCategory should return categoryId for matching keyword`() = runBlocking {
        val entities = listOf(
            CategoryKeywordEntity(1, 1, "restaurant"),
            CategoryKeywordEntity(2, 2, "uber")
        )
        every { dao.getAllKeywords() } returns flowOf(entities)

        val result = repository.predictCategory("IFD*RESTAURANT BAKO S")

        assertEquals(1, result)
    }

    @Test
    fun `predictCategory should be case insensitive`() = runBlocking {
        val entities = listOf(
            CategoryKeywordEntity(1, 1, "restaurant")
        )
        every { dao.getAllKeywords() } returns flowOf(entities)

        val result = repository.predictCategory("FANCY RESTAURANT")

        assertEquals(1, result)
    }

    @Test
    fun `predictCategory should prioritize longer keywords`() = runBlocking {
        val entities = listOf(
            CategoryKeywordEntity(1, 1, "uber"),
            CategoryKeywordEntity(2, 2, "uber eats")
        )
        every { dao.getAllKeywords() } returns flowOf(entities)

        val result = repository.predictCategory("Compra em UBER EATS")

        assertEquals(2, result)
    }

    @Test
    fun `predictCategory should return null for no matches`() = runBlocking {
        val entities = listOf(
            CategoryKeywordEntity(1, 1, "restaurant")
        )
        every { dao.getAllKeywords() } returns flowOf(entities)

        val result = repository.predictCategory("SUPERMERCADO ABC")

        assertNull(result)
    }

    @Test
    fun `predictCategory should match partial words`() = runBlocking {
        val entities = listOf(
            CategoryKeywordEntity(1, 1, "ifd*")
        )
        every { dao.getAllKeywords() } returns flowOf(entities)

        val result = repository.predictCategory("IFD*RESTAURANTE BAKO S")

        assertEquals(1, result)
    }

    @Test
    fun `deleteKeyword should call dao deleteById`() = runBlocking {
        coEvery { dao.deleteById(1) } returns Unit

        repository.deleteKeyword(1)

        coVerify { dao.deleteById(1) }
    }

    @Test
    fun `deleteKeywordsByCategory should call dao deleteByCategory`() = runBlocking {
        coEvery { dao.deleteByCategory(1) } returns Unit

        repository.deleteKeywordsByCategory(1)

        coVerify { dao.deleteByCategory(1) }
    }
}
