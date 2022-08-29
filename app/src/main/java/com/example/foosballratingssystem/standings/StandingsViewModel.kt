package com.example.foosballratingssystem.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foosballratingssystem.database.Results
import com.example.foosballratingssystem.database.ResultsRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class StandingsViewModel(private val resultsRepository: ResultsRepository) : ViewModel() {

    private val _standingsGroupByGameWon = MutableLiveData<List<Standings>>()
    val standingsGroupByGameWon: LiveData<List<Standings>>
        get() = _standingsGroupByGameWon

    private val _standingsGroupByGamePlayed = MutableLiveData<List<Standings>>()
    val standingsGroupByGamePlayed: LiveData<List<Standings>>
        get() = _standingsGroupByGamePlayed

    fun getResults() {
        resultsRepository.getAll()
            .subscribe(object : SingleObserver<List<Results>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(results: List<Results>) {
                    val personGamesPlayedMap = mutableMapOf<String, Int?>()
                    val personGamesWonMap = mutableMapOf<String, Int?>()

                    for (result in results) {
                        transformResultToMaps(result, personGamesPlayedMap, personGamesWonMap)
                    }

                    val standingsPlayed =
                        mapToSortedStandingsList(personGamesPlayedMap)
                    val standingsWon =
                        mapToSortedStandingsList(personGamesWonMap)

                    _standingsGroupByGamePlayed.value = standingsPlayed
                    _standingsGroupByGameWon.value = standingsWon
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    private fun transformResultToMaps(
        result: Results,
        personGamesPlayedMap: MutableMap<String, Int?>,
        personGamesWonMap: MutableMap<String, Int?>
    ) {
        addToMap(personGamesPlayedMap, result.playerOne)
        addToMap(personGamesPlayedMap, result.playerTwo)

        if (result.scorePlayerOne > result.scorePlayerTwo) {
            addToMap(personGamesWonMap, result.playerOne)
            personGamesWonMap.putIfAbsent(result.playerTwo, 0)
        } else {
            addToMap(personGamesWonMap, result.playerTwo)
            personGamesWonMap.putIfAbsent(result.playerOne, 0)
        }
    }

    private fun mapToSortedStandingsList(
        personGamesPlayedMap: MutableMap<String, Int?>
    ): List<Standings> {
        var standingsPlayedRatingNumber = 0L
        return personGamesPlayedMap.toList().sortedByDescending { (_, value) -> value }
            .map {
                standingsPlayedRatingNumber++
                Standings(standingsPlayedRatingNumber, it.first, it.second)
            }
    }

    private fun addToMap(
        map: MutableMap<String, Int?>,
        key: String
    ) {
        if (map.containsKey(key)) {
            map[key] =
                map[key]?.plus(1)
        } else {
            map[key] = 1
        }
    }
}