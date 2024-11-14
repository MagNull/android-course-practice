package com.sano.ideallist.repository

import com.sano.ideallist.network.MonstersAPI
import com.sano.ideallist.network.MonstersResponse

class MonstersRepository(private val monstersApi: MonstersAPI) {
    suspend fun getMonsters(): List<MonsterData> {
        val responseList = monstersApi.getMonstersList().results ?: emptyList()
        val result = mutableListOf<MonsterData>()

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