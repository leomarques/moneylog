package lmm.moneylog.data.transaction.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.Index
import androidx.room.PrimaryKey
import lmm.moneylog.data.account.database.AccountEntity
import lmm.moneylog.data.category.database.CategoryEntity
import lmm.moneylog.data.creditcard.database.CreditCardEntity

@Entity(
    tableName = "transaction",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            childColumns = ["accountId"],
            parentColumns = ["id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            childColumns = ["categoryId"],
            parentColumns = ["id"],
            onDelete = SET_NULL
        ),
        ForeignKey(
            entity = CreditCardEntity::class,
            childColumns = ["creditCardId"],
            parentColumns = ["id"],
            onDelete = SET_NULL
        )
    ],
    indices = [
        Index("accountId"),
        Index("categoryId"),
        Index("creditCardId")
    ]
)
data class TransactionEntity(
    val value: Double,
    val description: String,
    val day: Int,
    val month: Int,
    val year: Int,
    val paidDay: Int? = null,
    val paidMonth: Int? = null,
    val paidYear: Int? = null,
    val invoiceMonth: Int? = null,
    val invoiceYear: Int? = null,
    val accountId: Int? = null,
    val categoryId: Int? = null,
    val creditCardId: Int? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
