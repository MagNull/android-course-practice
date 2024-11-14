package com.sano.ideallist.network

import androidx.annotation.Keep

@Keep
data class MonsterInfoResponse(
    val index: String?,
    val name: String?,
    val size: String?,
    val type: String?,
    val alignment: String?,
    val image : String?,
    val special_abilities : List<MonsterAbilityResponse>?
)
