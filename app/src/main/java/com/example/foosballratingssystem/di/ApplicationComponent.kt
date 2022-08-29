package com.example.foosballratingssystem.di

import com.example.foosballratingssystem.addResult.AddResultFragment
import com.example.foosballratingssystem.results.ResultsFragment
import com.example.foosballratingssystem.standings.StandingsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class])
interface ApplicationComponent {

    fun inject(addResultFragment: AddResultFragment)

    fun inject(resultFragment: ResultsFragment)

    fun inject(standings: StandingsFragment)
}