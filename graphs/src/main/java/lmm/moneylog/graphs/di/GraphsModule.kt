package lmm.moneylog.graphs.di

import lmm.moneylog.data.misc.dataModule
import lmm.moneylog.graphs.viewmodel.GraphsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

/**
 * Koin dependency injection module for the graphs feature module
 * Provides ViewModels for graphs screen components
 */
val graphsModule =
    module {
        loadKoinModules(dataModule)

        viewModelOf(::GraphsViewModel)
    }
