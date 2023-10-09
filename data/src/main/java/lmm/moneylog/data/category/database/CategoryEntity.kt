package lmm.moneylog.data.category.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    val name: String,
    val color: Long,
    val isIncome: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
