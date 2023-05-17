package lmm.moneylog.data

import kotlinx.coroutines.Dispatchers

class CoroutineDispatcherProviderImpl : CoroutineDispatcherProvider {
    override fun provide() = Dispatchers.IO
}
