package com.example.foosballratingssystem.di

import android.content.Context
import com.example.foosballratingssystem.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(val context: Context) {

    @Singleton
    @Provides
    fun provideAppDatabase(): AppDatabase = AppDatabase.getInstance(context)
}