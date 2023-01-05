package lmm.moneylog.addtransaction

import androidx.compose.runtime.mutableStateOf

class AddTransactionModel {
    val value = mutableStateOf("")
    val date = mutableStateOf("")
    val description = mutableStateOf("")
    val category = mutableStateOf("")
    val account = mutableStateOf("")
}
