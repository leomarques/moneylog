package lmm.moneylog.data.category.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM `category`")
    fun selectCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM `category`")
    suspend fun selectCategoriesSuspend(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountEntity: CategoryEntity)

    @Query("SELECT * FROM `category` where id=:id")
    suspend fun selectCategory(id: Int): CategoryEntity?

    @Update
    suspend fun update(categoryEntity: CategoryEntity)

    @Query("DELETE FROM `category` WHERE id = :id")
    suspend fun delete(id: Int)
}
