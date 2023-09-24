package lmm.moneylog.data.coroutine

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {
    fun provide(): CoroutineDispatcher
}
