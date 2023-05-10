package lmm.moneylog.ui.features.addtransaction

import androidx.compose.runtime.mutableStateOf
import lmm.moneylog.domain.addtransaction.time.DomainTime

class AddTransactionModel {
    val value = mutableStateOf("")
    var isIncome: Boolean = true
    val displayDate = mutableStateOf("")
    val description = mutableStateOf("")
    lateinit var date: DomainTime
}
