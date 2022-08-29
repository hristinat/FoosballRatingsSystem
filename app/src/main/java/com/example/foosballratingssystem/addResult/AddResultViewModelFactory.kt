package com.example.foosballratingssystem.addResult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foosballratingssystem.database.ResultsRepository

class AddResultViewModelFactory(
    private val resultId: Long,
    private val resultsRepository: ResultsRepository
) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddResultViewModel::class.java)) {
            return AddResultViewModel(resultId, resultsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}