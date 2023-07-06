package lmm.moneylog.data.coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineDispatcher : CoroutineDispatcherProvider {
    override fun provide() = UnconfinedTestDispatcher()
}
