package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import lmm.moneylog.R
import lmm.moneylog.data.transaction.time.DomainTime

data class TransactionDetailModel(
    val value: MutableState<String> = mutableStateOf(""),
    val isIncome: MutableState<Boolean> = mutableStateOf(true),
    val description: MutableState<String> = mutableStateOf(""),
    val displayDate: String = "",
    val isEdit: Boolean = false,
    val titleResourceId: Int = R.string.detailtransaction_topbar_title_add,
    val id: Int = -1,
    val date: DomainTime = DomainTime(0, 0, 0)
)
