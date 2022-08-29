package com.example.foosballratingssystem.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foosballratingssystem.database.Results
import com.example.foosballratingssystem.database.ResultsRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class ResultsViewModel(private val resultsRepository: ResultsRepository) : ViewModel() {

    private val _results = MutableLiveData<List<Results>>()
    val results: LiveData<List<Results>>
        get() = _results

    private val _navigateToAddResult = MutableLiveData<Boolean>()
    val navigateToAddResult: LiveData<Boolean>
        get() = _navigateToAddResult

    fun onNavigatedToAddResult() {
        _navigateToAddResult.value = false
    }

    fun onFabClick() {
        _navigateToAddResult.value = true
    }

    fun getResults() {
        resultsRepository.getAll()
            .subscribe(object : SingleObserver<List<Results>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(values: List<Results>) {
                    _results.value = values
                }

                override fun onError(e: Throwable) {
                }
            })
    }
}