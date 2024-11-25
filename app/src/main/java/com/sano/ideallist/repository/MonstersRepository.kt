package com.sano.ideallist.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.drawable.toIcon
import com.sano.ideallist.network.MonstersAPI
import com.sano.ideallist.network.MonstersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.HttpException

class MonstersRepository(
    private val monstersApi: MonstersAPI
) {
    private val _monstersBitmapMap: Map<String, Bitmap?> = mapOf()
    val monstersBitmapMap = MutableStateFlow(_monstersBitmapMap)

    suspend fun getMonsters(): List<MonsterData> {
        val result = mutableListOf<MonsterData>()
        val responseList: List<MonstersResponse> = try {
            monstersApi.getMonstersList()?.results ?: emptyList()
        } catch (e: Exception) {
            Log.e("Retrofit", e.message ?: "Unknown network error")
            emptyList()
        }

        for (monsterResponse: MonstersResponse in responseList.take(10)) {
            val monsterInfoResponse = monstersApi.getMonsterInfo(monsterResponse.index ?: "aboleth")
            with(monsterInfoResponse) {
                result.add(
                    MonsterData(
                        imageSrc = image.orEmpty(),
                        name = name.orEmpty(),
                        type = type.orEmpty(),
                        alignment = alignment.orEmpty(),
                        size = size.orEmpty(),
                        abilities = special_abilities?.map {
                            MonsterAbilityData(
                                name = it.name.orEmpty(),
                                desc = it.desc.orEmpty()
                            )
                        } ?: emptyList()
                    )
                )

            }
        }

        return result
    }

}