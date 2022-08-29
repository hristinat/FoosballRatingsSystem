package com.example.foosballratingssystem.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Results(
    var playerOne: String,
    var playerTwo: String,
    var scorePlayerOne: Int,
    var scorePlayerTwo: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)