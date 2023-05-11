package lmm.moneylog.ui.features.gettransactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import lmm.moneylog.domain.gettransactions.GetTransactionsInteractor
import lmm.moneylog.ui.textformatters.formatDate
import lmm.moneylog.ui.textformatters.formatForRs

class GetTransactionsViewModel(interactor: GetTransactionsInteractor) : ViewModel() {
    private val transactions = interactor.execute().asLiveData()

    val transactionsModel: LiveData<GetTransactionsModel> = transactions.map { transactions ->
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
