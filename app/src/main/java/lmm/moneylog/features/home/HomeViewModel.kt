package lmm.moneylog.features.home

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
            formatValue(-balance.debt)
        )
    }

    private fun formatValue(value: Double) = "R\$%.2f".format(value)
}
