package com.example.horsechallenge.horseGame.ui

import com.example.horsechallenge.horseGame.ui.model.SquareModel

data class HorseUiState(
    val isPremium: Boolean = false,

    val level:Int = 1,
    val movesRemaining:Int = 63,
    val time:String = "00:00",
    val lives:Int = 5,

    val optionProgress:Float = 0.0f,
    val movesAvailable:String = "0",

    val msgGameFinished: String = "",
    val score: Int = 0,
    val finishedGame: Boolean = false,

    val msgShareGame:String = "",

    val board: MutableList<MutableList<SquareModel>> = mutableListOf(),
)