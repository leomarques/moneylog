package lmm.moneylog.misc

import android.app.Application
import lmm.moneylog.data.misc.dataModule
import lmm.moneylog.graphs.di.graphsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                appModule,
                dataModule,
                graphsModule
            )
        }
    }
}
