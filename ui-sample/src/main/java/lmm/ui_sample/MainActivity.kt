package lmm.ui_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import lmm.moneylog.home.layout.HomeLayout
import lmm.moneylog.home.mocks.HomePreviewData
import lmm.moneylog.ui.components.misc.MainUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainUI {
                HomeLayout(
                    data = HomePreviewData.sampleHomeScreenData(),
                    callbacks = HomePreviewData.sampleHomeLayoutCallbacks()
                )
            }
        }
    }
}
