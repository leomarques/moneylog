package lmm.moneylog.ui.features.transactiondetail

import androidx.compose.runtime.MutableState
import lmm.moneylog.domain.time.DomainTime

data class TransactionDetailModel(
    val value: MutableState<String>,
    val isIncome: MutableState<Boolean>,
    val displayDate: MutableState<String>,
    val description: MutableState<String>,
    var date: DomainTime,
    val isEdit: Boolean,
    val id: Int,
    val titleResourceId: Int
)
