package lmm.moneylog.data.creditcard.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credit_card")
data class CreditCardEntity(
    val name: String,
    val closeDay: Int,
    val payDay: Int,
    val limit: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
