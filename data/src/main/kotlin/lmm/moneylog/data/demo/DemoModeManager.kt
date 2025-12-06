package lmm.moneylog.data.demo

import android.content.SharedPreferences

class DemoModeManager(
    private val sharedPreferences: SharedPreferences
) {
    fun isDemoMode(): Boolean = sharedPreferences.getBoolean(DEMO_MODE_KEY, false)

    fun setDemoMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(DEMO_MODE_KEY, enabled).apply()
    }

    companion object {
        private const val DEMO_MODE_KEY = "demo_mode_enabled"
    }
}
