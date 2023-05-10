package lmm.moneylog.ui.features.addtransaction

import androidx.compose.runtime.mutableStateOf
import lmm.moneylog.domain.addtransaction.time.DomainTime

class AddTransactionModel {
    val value = mutableStateOf("")
    val displayDate = mutableStateOf("")
    val description = mutableStateOf("")
    lateinit var date: DomainTime
}
