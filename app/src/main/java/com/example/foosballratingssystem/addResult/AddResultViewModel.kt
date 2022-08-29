package com.example.foosballratingssystem.addResult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foosballratingssystem.database.Results
import com.example.foosballratingssystem.database.ResultsRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver

class AddResultViewModel(
    val resultId: Long,
    private val resultsRepository: ResultsRepository
) :
    ViewModel() {

    val playerOne = MutableLiveData<String>()
    val scorePlayerOne = MutableLiveData<String>()
    val playerTwo = MutableLiveData<String>()
    val scorePlayerTwo = MutableLiveData<String>()

    private val _navigateToMainFragment = MutableLiveData<Boolean>()
    val navigateToMainFragment: LiveData<Boolean>
        get() = _navigateToMainFragment

    private val _showEnterValidDataToastMessage = MutableLiveData<Boolean>()
    val showEnterValidDataToastMessage: LiveData<Boolean>
        get() = _showEnterValidDataToastMessage

    init {
        getResult()
    }

    fun onAddResultClicked() {
        if (isDataValid()) {
            resultsRepository.insertOrUpdate(
                Results(
                    playerOne.value!!,
                    playerTwo.value!!,
                    scorePlayerOne.value!!.toInt(),
                    scorePlayerTwo.value!!.toInt(),
                    resultId
                )
            ).subscribe(
                object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        _navigateToMainFragment.value = true
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        } else {
            _showEnterValidDataToastMessage.value = true
        }
    }

    fun onNavigatedToMainFragment() {
        _navigateToMainFragment.value = false
    }

    private fun getResult() {
        if (resultId != 0L) {
            resultsRepository.getById(resultId).subscribe(
                object : SingleObserver<Results> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onSuccess(result: Results) {
                        playerOne.value = result.playerOne
                        playerTwo.value = result.playerTwo
                        scorePlayerOne.value = result.scorePlayerOne.toString()
                        scorePlayerTwo.value = result.scorePlayerTwo.toString()
                    }

                    override fun onError(e: Throwable) {
                    }

                })
        }
    }

    private fun isDataValid() =
        (!playerOne.value.isNullOrBlank() && !playerTwo.value.isNullOrBlank() && !scorePlayerOne.value.isNullOrBlank()
                && !scorePlayerTwo.value.isNullOrBlank()
                && playerOne.value != playerTwo.value && scorePlayerOne.value != scorePlayerTwo.value)
}