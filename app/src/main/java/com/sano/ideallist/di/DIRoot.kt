package com.sano.ideallist.di

import com.sano.ideallist.repository.MonstersRepository
import com.sano.ideallist.viewModel.MonstersListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {
    single<MonstersRepository> { MonstersRepository(get()) }
    viewModel { MonstersListViewModel(get()) }
}