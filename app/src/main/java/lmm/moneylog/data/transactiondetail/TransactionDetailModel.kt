package lmm.moneylog.data.transactiondetail

import androidx.compose.runtime.MutableState
import lmm.moneylog.domain.time.DomainTime

data class TransactionDetailModel(
    val id: Int,
    val value: MutableState<String>,
    val isIncome: MutableState<Boolean>,
    var date: DomainTime,
    val displayDate: MutableState<String>,
    val description: MutableState<String>,
    val isEdit: Boolean,
    val titleResourceId: Int
)
