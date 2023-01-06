package lmm.moneylog.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import lmm.moneylog.data.TransactionRepository

class HomeViewModel(repository: TransactionRepository) : ViewModel() {

    private val transactions = repository.get()

    val balanceModel: LiveData<BalanceModel> = Transformations.map(transactions) { list ->
        var credit = 0.0
        var debt = 0.0

        for (transaction in list) {
            if (transaction.value > 0)
                credit += transaction.value
            else
                debt += transaction.value
        }

        val total = credit + debt

        BalanceModel(formatValue(total), formatValue(credit), formatValue(debt))
    }

    private fun formatValue(value: Double) = "%.2f".format(value)
}
