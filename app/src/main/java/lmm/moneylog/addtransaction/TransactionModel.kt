package lmm.moneylog.addtransaction

import androidx.compose.runtime.mutableStateOf

class TransactionModel {
    val value = mutableStateOf("")
    val date = mutableStateOf("")
    val description = mutableStateOf("")
    val category = mutableStateOf("")
    val account = mutableStateOf("")
}