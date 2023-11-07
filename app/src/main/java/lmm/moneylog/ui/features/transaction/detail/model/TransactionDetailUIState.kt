package lmm.moneylog.ui.features.transaction.detail.model

import androidx.compose.ui.graphics.Color
import lmm.moneylog.R
import lmm.moneylog.data.account.Account
import lmm.moneylog.data.category.Category
import lmm.moneylog.data.transaction.time.DomainTime
import lmm.moneylog.ui.theme.defaultColor

data class TransactionDetailUIState(
    val id: Int = -1,
    val titleResourceId: Int = R.string.detail_topbar_transaction_add,
    val isEdit: Boolean = false,
    val showFab: Boolean = true,
    val isIncome: Boolean = true,
    val value: String = "",
    val description: String = "",
    val displayDate: String = "",
    val displayAccount: String = "",
    val displayCategory: String = "",
    val displayAccountColor: Color = defaultColor,
    val displayCategoryColor: Color = defaultColor,
    val accountId: Int? = null,
    val categoryId: Int? = null,
    val date: DomainTime = DomainTime(),
    val accounts: List<Account> = emptyList(),
    val categories: List<Category> = emptyList()
)
