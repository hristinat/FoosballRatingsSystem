package com.example.foosballratingssystem.standings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foosballratingssystem.database.ResultsRepository

class StandingsViewModelFactory(private val resultsRepository: ResultsRepository) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StandingsViewModel::class.java)) {
            return StandingsViewModel(resultsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}