package com.example.foosballratingssystem.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foosballratingssystem.database.ResultsRepository

class ResultsViewModelFactory(private val resultsRepository: ResultsRepository) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
            return ResultsViewModel(resultsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}