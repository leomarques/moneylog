package lmm.moneylog

import lmm.moneylog.di.appModule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() {
        appModule.verify()
    }
}
