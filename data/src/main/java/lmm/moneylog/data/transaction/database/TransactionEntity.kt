package lmm.moneylog.data.transaction.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.PrimaryKey
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.category.database.CategoryEntity

@Entity(
    tableName = "transaction",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            childColumns = ["accountId"],
            parentColumns = ["id"],
            onDelete = SET_NULL
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            childColumns = ["categoryId"],
            parentColumns = ["id"],
            onDelete = SET_NULL
        )
    ]
)
data class TransactionEntity(
    val value: Double,
    val description: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val accountId: Int?,
    val categoryId: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
