package com.sano.ideallist.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MonstersAPI {

    @GET("monsters")
    suspend fun getMonstersList(): MonstersListResponse?

    @GET("monsters/{name}")
    suspend fun getMonsterInfo(@Path("name") name: String): MonsterInfoResponse
}