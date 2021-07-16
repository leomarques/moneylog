package lmm.moneylog.home

import androidx.lifecycle.*
import lmm.moneylog.data.Repository
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeViewModel(repository: Repository) : ViewModel() {

    private val transactions = repository.getTransactions().asLiveData()

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