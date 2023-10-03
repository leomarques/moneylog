package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import lmm.moneylog.R
import lmm.moneylog.data.transaction.time.DomainTime

data class TransactionDetailModel(
    val id: Int = -1,
    val titleResourceId: Int = R.string.detailtransaction_topbar_title_add,
    val isEdit: Boolean = false,
    val displayDate: String = "",
    val date: DomainTime = DomainTime(0, 0, 0),
    val value: MutableState<String> = mutableStateOf(""),
    val isIncome: MutableState<Boolean> = mutableStateOf(true),
    val description: MutableState<String> = mutableStateOf("")
)
