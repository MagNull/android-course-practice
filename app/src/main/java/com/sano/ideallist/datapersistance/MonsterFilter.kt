package com.sano.ideallist.datapersistance

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sano.ideallist.repository.MonsterData

data class FilterSettings(
    val name: String = "", val alignment: String = "Any", val abilitiesCount: Int = -1
)

fun List<MonsterData>.filterWithSettings(settings: FilterSettings): List<MonsterData> {
    val alignmentFilter =
        if (settings.alignment.lowercase() == "any") "" else settings.alignment.lowercase()
    return this.filter {
        it.name.contains(settings.name) &&
                it.alignment.lowercase().contains(alignmentFilter) &&
                it.abilities.count() >= (settings.abilitiesCount)
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "filters")

val NAME_FILTER_KEY = stringPreferencesKey("filter_name")
val ALIGNMENT_FILTER_KEY = stringPreferencesKey("filter_alignment")
val ABILITIES_FILTER_KEY = intPreferencesKey("abilities_alignment")