package lmm.moneylog.ui.features.addtransaction

import androidx.compose.runtime.mutableStateOf

class AddTransactionModel {
    val value = mutableStateOf("")
    val date = mutableStateOf("")
    val description = mutableStateOf("")
}