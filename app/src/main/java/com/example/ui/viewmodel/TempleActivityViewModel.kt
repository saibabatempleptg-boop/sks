package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.TempleActivityRepository
import com.example.data.local.TempleDatabase
import com.example.data.local.entity.AbhishekSchedules
import com.example.data.local.entity.DarshanTimings
import com.example.data.local.entity.Homams
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TempleActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TempleActivityRepository = TempleActivityRepository(
        TempleDatabase.getInstance(application)
    )

    val darshanTimings: StateFlow<List<DarshanTimings>> = repository.darshanTimings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val abhishekSchedules: StateFlow<List<AbhishekSchedules>> = repository.abhishekSchedules
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val homams: StateFlow<List<Homams>> = repository.homams
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }
}
