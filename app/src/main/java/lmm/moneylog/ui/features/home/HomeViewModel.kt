package lmm.moneylog.ui.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import lmm.moneylog.domain.getbalance.GetBalanceInteractor

class HomeViewModel(interactor: GetBalanceInteractor) : ViewModel() {
    private val balanceData = interactor.execute().asLiveData()

    val balanceCardModel: LiveData<BalanceCardModel> = balanceData.map { balance ->
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
