package lmm.moneylog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    val value: Double,
    val name: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}