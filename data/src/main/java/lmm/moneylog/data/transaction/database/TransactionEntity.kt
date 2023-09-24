package lmm.moneylog.data.transaction.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    val value: Double,
    val description: String,
    val year: Int,
    val month: Int,
    val day: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
