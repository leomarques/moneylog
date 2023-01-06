package lmm.moneylog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    val value: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
