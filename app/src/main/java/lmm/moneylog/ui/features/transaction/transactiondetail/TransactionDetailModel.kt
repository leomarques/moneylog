package lmm.moneylog.ui.features.transaction.transactiondetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import lmm.moneylog.R
import lmm.moneylog.domain.time.DomainTime

data class TransactionDetailModel(
    val value: MutableState<String> = mutableStateOf(""),
    val isIncome: MutableState<Boolean> = mutableStateOf(true),
    val displayDate: MutableState<String> = mutableStateOf(""),
    val description: MutableState<String> = mutableStateOf(""),
    var date: DomainTime = DomainTime(0, 0, 0),
    val isEdit: Boolean = false,
    val id: Int = 0,
    val titleResourceId: Int = R.string.detailtransaction_topbar_title_add
)
