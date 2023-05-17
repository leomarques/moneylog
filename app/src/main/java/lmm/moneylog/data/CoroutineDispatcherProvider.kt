package lmm.moneylog.data

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun provide(): CoroutineDispatcher
}
