package lmm.moneylog.ui.features.gettransactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import lmm.moneylog.domain.gettransactions.GetTransactionsInteractor
import lmm.moneylog.domain.models.Transaction
import lmm.moneylog.ui.textformatters.formatDate
import lmm.moneylog.ui.textformatters.formatForRs

const val getTransactionsIncome = "income"
const val getTransactionsOutcome = "outcome"
const val getTransactionsAll = "all"

class GetTransactionsViewModel(private val interactor: GetTransactionsInteractor) : ViewModel() {

    fun convertToModel(typeOfValue: String?): LiveData<GetTransactionsModel> =
        when (typeOfValue) {
            getTransactionsIncome -> {
                val transactions = interactor.getIncomeTransactions().asLiveData()
                convertToModel(transactions)
            }

            getTransactionsOutcome -> {
                val transactions = interactor.getOutcomeTransactions().asLiveData()
                convertToModel(transactions)
            }

            else -> {
                val transactions = interactor.getAllTransactions().asLiveData()
                convertToModel(transactions)
            }
        }

    private fun convertToModel(listLiveData: LiveData<List<Transaction>>) =
        listLiveData.map { transactions ->
            GetTransactionsModel(
                transactions.map { transaction ->
                    with(transaction) {
                        TransactionModel(
                            if (value < 0.0) {
                                (-value).formatForRs()
                            } else {
                                value.formatForRs()
                            },
                            value > 0,
                            description,
                            date.formatDate()
                        )
                    }
                }
            )
        }
}
