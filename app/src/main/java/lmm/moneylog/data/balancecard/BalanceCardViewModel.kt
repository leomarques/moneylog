package lmm.moneylog.data.balancecard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import lmm.moneylog.domain.getbalance.GetBalanceInteractor
import lmm.moneylog.ui.textformatters.formatForRs

class BalanceCardViewModel(interactor: GetBalanceInteractor) : ViewModel() {
    private val balanceData = interactor.execute().asLiveData()

    val balanceCardModel: LiveData<BalanceCardModel> = balanceData.map { balance ->
        BalanceCardModel(
            balance.total.formatForRs(),
            balance.credit.formatForRs(),
            balance.debt.formatForRs()
        )
    }
}
