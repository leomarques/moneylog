package lmm.moneylog.data.accounttransfer.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account_transfer")
data class AccountTransferEntity(
    val value: Double,
    val year: Int,
    val month: Int,
    val day: Int,
    val originAccountId: Int,
    val destinationAccountId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
