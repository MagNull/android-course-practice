package com.sano.ideallist.network

import androidx.annotation.Keep

@Keep
data class MonstersListResponse(
    val count : Int,
    val results : List<MonstersResponse>?
)