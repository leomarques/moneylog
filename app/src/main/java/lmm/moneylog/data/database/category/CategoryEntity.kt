package lmm.moneylog.data.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    val name: String,
    val color: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
