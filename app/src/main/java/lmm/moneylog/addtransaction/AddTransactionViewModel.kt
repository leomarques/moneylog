package lmm.moneylog.addtransaction

import android.util.Log
import androidx.lifecycle.*
import lmm.moneylog.data.Repository
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class AddTransactionViewModel(private val repository: Repository) : ViewModel() {

    private fun addTransaction(value: Double) {
        viewModelScope.launch {
            repository.save(value)
        }
    }

    fun onBtnClick(transactionModel: TransactionModel) {
        try {
            val value = transactionModel.value.value.toDouble()
            addTransaction(value)
        } catch (e: NumberFormatException) {
            Log.d("moneylog", e.localizedMessage ?: "NumberFormatException")
        }
    }
}