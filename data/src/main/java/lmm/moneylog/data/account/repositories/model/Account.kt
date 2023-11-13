package lmm.moneylog.data.account.repositories.model

data class Account(
    val id: Int = -1,
    val name: String,
    val color: Long,
    val archived: Boolean = false
)
