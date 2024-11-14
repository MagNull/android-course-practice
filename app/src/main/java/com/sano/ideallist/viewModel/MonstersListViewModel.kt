package com.sano.ideallist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sano.ideallist.repository.MonsterData
import com.sano.ideallist.repository.MonstersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonstersListViewModel(
    private val repository: MonstersRepository
) : ViewModel() {
    private val mutableState = MutableStateFlow(listOf<MonsterData>())
    val viewState = mutableState.asStateFlow()

    init {
        loadMonsters()
    }

    private fun loadMonsters() {
        viewModelScope.launch {
            mutableState.update {
                repository.getMonsters()
            }
        }
    }
}