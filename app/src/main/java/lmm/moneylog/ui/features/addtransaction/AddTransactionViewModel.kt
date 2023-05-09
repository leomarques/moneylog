package lmm.moneylog.ui.features.addtransaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lmm.moneylog.domain.addtransaction.AddTransactionInteractor
import lmm.moneylog.domain.addtransaction.model.Transaction
import lmm.moneylog.ui.features.addtransaction.domaintime.DomainTimeConverter
import lmm.moneylog.ui.features.addtransaction.domaintime.DomainTimeException

class AddTransactionViewModel(
    private val interactor: AddTransactionInteractor,
    private val domainTimeConverter: DomainTimeConverter,
) : ViewModel() {

    val addTransactionModel = AddTransactionModel().also {
        it.date.value = domainTimeConverter.getNowTime()
    }

    fun saveTransaction(addTransactionModel: AddTransactionModel) {
        try {
            val value = addTransactionModel.value.value.toDouble()
            val date = addTransactionModel.date.value
            val description = addTransactionModel.description.value

            val transaction = Transaction(
                value = value,
                date = domainTimeConverter.toDomainTime(date),
                description = description
            )

            viewModelScope.launch {
                interactor.execute(transaction)
            }
        } catch (e: NumberFormatException) {
            Log.e(
                /* tag = */ "saveTransaction",
                /* msg = */ "NumberFormatException: " + e.localizedMessage
            )
        } catch (e: DomainTimeException) {
            Log.e(
                /* tag = */ "saveTransaction",
                /* msg = */ "ToDomainTimeException: " + e.localizedMessage
            )
        }
    }
}
