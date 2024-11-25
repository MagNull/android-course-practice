package com.sano.ideallist.repository

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Immutable
data class MonsterAbilityData(
    val name: String,
    val desc: String
)

class MonsterAbilityDataConverter {
    @TypeConverter
    fun abilitiesDataToString(abilities: List<MonsterAbilityData>): String =
        Gson().toJson(abilities)

    @TypeConverter
    fun stringToAbilities(json: String): List<MonsterAbilityData> {
        val type = object : TypeToken<List<MonsterAbilityData>>() {}.type
        return Gson().fromJson(json, type)
    }
}

@Entity
@Immutable
data class MonsterData(
    @PrimaryKey @ColumnInfo(name = "name") val name: String = "None",
    @ColumnInfo(name = "image_source") val imageSrc: String = "None",
    @ColumnInfo(name = "size") val size: String = "None",
    @ColumnInfo(name = "type") val type: String = "None",
    @ColumnInfo(name = "alignment") val alignment: String = "None",
    @ColumnInfo(name = "abilities") val abilities: List<MonsterAbilityData> = emptyList(),
)