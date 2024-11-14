package com.sano.ideallist.repository

import androidx.compose.runtime.Immutable

@Immutable
data class MonsterAbilityData(
    val name: String,
    val desc: String
)

@Immutable
data class MonsterData(
    val imageSrc: String,
    val name: String,
    val size: String,
    val type: String,
    val alignment: String,
    val abilities: List<MonsterAbilityData> = emptyList(),
)