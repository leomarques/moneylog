package lmm.moneylog.data.creditcard.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credit_card")
data class CreditCardEntity(
    val name: String,
    val closingDay: Int,
    val dueDay: Int,
    val limit: Int,
    val color: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
