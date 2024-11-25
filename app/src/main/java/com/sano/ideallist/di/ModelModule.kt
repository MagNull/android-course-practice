package com.sano.ideallist.di

import androidx.room.Room
import com.sano.ideallist.datapersistance.LocalDataPersistence
import com.sano.ideallist.datapersistance.dataStore
import com.sano.ideallist.repository.MonstersListAppDatabase
import com.sano.ideallist.repository.MonstersRepository
import com.sano.ideallist.viewModel.MonstersListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val modelModule = module {
    single<MonstersRepository> { MonstersRepository(get()) }
    single<LocalDataPersistence> { LocalDataPersistence(androidContext().dataStore) }
    single<MonstersListAppDatabase> {
        Room.databaseBuilder(
            get(),
            MonstersListAppDatabase::class.java,
            "monsters-database"
        ).build()
    }
    viewModel { MonstersListViewModel(get(), get(), get()) }
}