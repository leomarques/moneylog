package lmm.moneylog.data.categorypredictor.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryKeywordDao {
    @Query("SELECT * FROM category_keywords")
    fun getAllKeywords(): Flow<List<CategoryKeywordEntity>>

    @Query("SELECT * FROM category_keywords WHERE categoryId = :categoryId")
    fun getKeywordsByCategory(categoryId: Int): Flow<List<CategoryKeywordEntity>>

    @Query("SELECT * FROM category_keywords WHERE id = :id")
    suspend fun getKeywordById(id: Int): CategoryKeywordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keyword: CategoryKeywordEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keywords: List<CategoryKeywordEntity>)

    @Delete
    suspend fun delete(keyword: CategoryKeywordEntity)

    @Query("DELETE FROM category_keywords WHERE categoryId = :categoryId")
    suspend fun deleteByCategory(categoryId: Int)

    @Query("DELETE FROM category_keywords WHERE id = :id")
    suspend fun deleteById(id: Int)
}
