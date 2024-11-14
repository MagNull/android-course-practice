package com.sano.ideallist.di

import com.sano.ideallist.network.MonstersAPI
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideRetrofit() }
    single { provideNetworkApi(get()) }
}

fun provideRetrofit(): Retrofit {

    return Retrofit.Builder()
        .baseUrl("https://www.dnd5eapi.co/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

}

fun provideNetworkApi(retrofit: Retrofit): MonstersAPI =
    retrofit.create(MonstersAPI::class.java)