package lmm.moneylog.ui.features.transaction.gettransactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import lmm.moneylog.R
import lmm.moneylog.data.transaction.Transaction
import lmm.moneylog.data.transaction.repositories.GetTransactionsRepository
import lmm.moneylog.ui.textformatters.formatDate
import lmm.moneylog.ui.textformatters.formatForRs

const val getTransactionsIncome = "income"
const val getTransactionsOutcome = "outcome"
const val getTransactionsAll = "all"

class GetTransactionsViewModel(private val getTransactionsRepository: GetTransactionsRepository) :
    ViewModel() {

    fun getTransactionsModel(typeOfValue: String?): LiveData<GetTransactionsModel> =
        when (typeOfValue) {
            getTransactionsIncome -> {
                val transactions = getTransactionsRepository.getIncomeTransactions().asLiveData()
                convertToModel(transactions, R.string.gettransactions_topbar_income)
            }

            getTransactionsOutcome -> {
                val transactions = getTransactionsRepository.getOutcomeTransactions().asLiveData()
                convertToModel(transactions, R.string.gettransactions_topbar_outcome)
            }

            else -> {
                val transactions = getTransactionsRepository.getAllTransactions().asLiveData()
                convertToModel(transactions, R.string.gettransactions_topbar_all)
            }
        }

    private fun convertToModel(listLiveData: LiveData<List<Transaction>>, titleResourceId: Int) =
        listLiveData.map { transactions ->
            GetTransactionsModel(
                transactions = transactions.sortedBy { it.date }.map { transaction ->
                    with(transaction) {
                        TransactionModel(
                            value = if (value < 0.0) {
                                (-value).formatForRs()
                            } else {
                                value.formatForRs()
                            },
                            isIncome = value > 0,
                            description = description,
                            date = date.formatDate(),
                            id = id
                        )
                    }
                },
                titleResourceId = titleResourceId
            )
        }
}