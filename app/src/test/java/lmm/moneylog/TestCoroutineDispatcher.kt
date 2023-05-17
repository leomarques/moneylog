package lmm.moneylog

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import lmm.moneylog.data.CoroutineDispatcherProvider

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineDispatcher : CoroutineDispatcherProvider {
    override fun provide() = UnconfinedTestDispatcher()
}
