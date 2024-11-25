package com.sano.ideallist.datapersistance

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

class LocalDataPersistence(val filterSettingsStorage: DataStore<Preferences>) {

    suspend fun saveFilterSettings(settings: FilterSettings) {
        filterSettingsStorage.edit { filters ->
            filters[NAME_FILTER_KEY] = settings.name
            filters[ALIGNMENT_FILTER_KEY] = settings.alignment
            filters[ABILITIES_FILTER_KEY] = settings.abilitiesCount
        }
    }
}