package lmm.moneylog.data.database.category

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountEntity: CategoryEntity)

    @Query("SELECT * FROM `category` where id=:id")
    fun selectCategory(id: Int): Flow<CategoryEntity?>

    @Update
    fun update(categoryEntity: CategoryEntity)

    @Query("DELETE FROM `category` WHERE id = :id")
    fun delete(id: Int)
}
