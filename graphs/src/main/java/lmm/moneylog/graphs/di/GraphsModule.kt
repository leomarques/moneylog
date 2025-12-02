package lmm.moneylog.graphs.di

import lmm.moneylog.data.misc.dataModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Koin dependency injection module for the graphs feature module
 * Provides ViewModels for graphs screen components
 */
val graphsModule =
    module {
        loadKoinModules(dataModule)

        // ViewModels will be added here as we develop the graphs feature
    }
