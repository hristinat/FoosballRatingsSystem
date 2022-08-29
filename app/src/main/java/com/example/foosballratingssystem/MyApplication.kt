package com.example.foosballratingssystem

import android.app.Application
import com.example.foosballratingssystem.di.ApplicationComponent
import com.example.foosballratingssystem.di.DaggerApplicationComponent
import com.example.foosballratingssystem.di.StorageModule

class MyApplication : Application() {
    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder().storageModule(StorageModule(this))
            .build()
    }
}