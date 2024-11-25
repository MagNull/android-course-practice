package com.sano.ideallist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sano.ideallist.datapersistance.ABILITIES_FILTER_KEY
import com.sano.ideallist.datapersistance.ALIGNMENT_FILTER_KEY
import com.sano.ideallist.datapersistance.FilterSettings
import com.sano.ideallist.datapersistance.LocalDataPersistence
import com.sano.ideallist.datapersistance.NAME_FILTER_KEY
import com.sano.ideallist.repository.MonsterData
import com.sano.ideallist.repository.MonstersListAppDatabase
import com.sano.ideallist.repository.MonstersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonstersListViewModel(
    private val repository: MonstersRepository,
    private val localDataPersistence: LocalDataPersistence,
    private val favouriteMonstersDao: MonstersListAppDatabase
) : ViewModel() {
    private val _monstersList = MutableStateFlow(listOf<MonsterData>())

    val monstersList = _monstersList.asStateFlow()

    val monstersBitmaps = repository.monstersBitmapMap.asLiveData()

    val filterSettings = localDataPersistence.filterSettingsStorage.data.map { prefs ->
        FilterSettings(
            name = prefs[NAME_FILTER_KEY] ?: "",
            alignment = prefs[ALIGNMENT_FILTER_KEY] ?: "None",
            abilitiesCount = prefs[ABILITIES_FILTER_KEY] ?: -1
        )
    }.asLiveData()

    val favouriteMonsters = favouriteMonstersDao.favouriteMonsters().getAll()


    init {
        loadMonsters()
    }

    private fun loadMonsters() {
        viewModelScope.launch(Dispatchers.IO) {
            _monstersList.update {
                repository.getMonsters()
            }
        }
    }

    fun saveFilterSettings(newSettings: FilterSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            localDataPersistence.saveFilterSettings(newSettings)
        }
    }

    fun insertFavouriteMonster(monsterData: MonsterData) =
        viewModelScope.launch(Dispatchers.IO) {
            favouriteMonstersDao.favouriteMonsters().insertFavouriteMonsters(monsterData)
        }

    fun deleteFavouriteMonster(monsterData: MonsterData) =
        viewModelScope.launch(Dispatchers.IO) {
            favouriteMonstersDao.favouriteMonsters().deleteFavouriteMonster(monsterData)
        }
}