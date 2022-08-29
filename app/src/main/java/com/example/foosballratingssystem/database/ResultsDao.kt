package com.example.foosballratingssystem.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ResultsDao {
    @Query("SELECT * FROM results")
    fun getAll(): Single<List<Results>>

    @Query("SELECT * FROM results WHERE id = :id")
    fun getById(id: Long): Single<Results>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(results: Results): Completable
}