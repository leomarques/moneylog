package lmm.moneylog.addtransaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.addtransaction.ui.AddTransactionModel
import lmm.moneylog.data.TransactionRepository

class AddTransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    fun saveTransaction(addTransactionModel: AddTransactionModel) {
        try {
            val value = addTransactionModel.value.value.toDouble()

            viewModelScope.launch {
                repository.save(value)
            }
        } catch (e: NumberFormatException) {
            Log.d("saveTransaction", e.localizedMessage ?: "NumberFormatException")
        }
    }
}
