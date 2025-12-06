package lmm.moneylog.data.demo

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DemoModeHelper : KoinComponent {
    private val demoModeManager: DemoModeManager by inject()
    private val demoRepositoriesManager: DemoRepositoriesManager by inject()

    fun isDemoMode(): Boolean = demoModeManager.isDemoMode()

    fun enableDemoMode() {
        if (!demoModeManager.isDemoMode()) {
            demoModeManager.setDemoMode(true)
            demoRepositoriesManager.initializeWithDemoData()
        }
    }

    fun disableDemoMode() {
        if (demoModeManager.isDemoMode()) {
            demoRepositoriesManager.clearAllData()
            demoModeManager.setDemoMode(false)
        }
    }

    fun toggleDemoMode() {
        if (isDemoMode()) {
            disableDemoMode()
        } else {
            enableDemoMode()
        }
    }

    fun resetDemoData() {
        if (demoModeManager.isDemoMode()) {
            demoRepositoriesManager.clearAllData()
            demoRepositoriesManager.initializeWithDemoData()
        }
    }
}
