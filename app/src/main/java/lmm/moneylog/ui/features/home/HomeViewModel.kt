package lmm.moneylog.ui.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import lmm.moneylog.domain.getbalance.GetBalanceInteractor

class HomeViewModel(interactor: GetBalanceInteractor) : ViewModel() {
    private val balanceData = interactor.execute().asLiveData()

    val balanceCardModel: LiveData<BalanceCardModel> = Transformations.map(balanceData) { balance ->
        BalanceCardModel(
            formatValue(balance.total),
            formatValue(balance.credit),
            formatValue(
                if (balance.debt < 0) {
                    -balance.debt
                } else {
                    0.0
                }
            )
        )
    }

    private fun formatValue(value: Double) = "R\$%.2f".format(value)
}
