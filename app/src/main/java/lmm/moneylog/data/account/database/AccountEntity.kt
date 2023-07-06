package lmm.moneylog.data.account.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountEntity(
    val name: String,
    val color: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
