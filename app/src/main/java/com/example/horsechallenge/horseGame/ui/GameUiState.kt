package com.example.horsechallenge.horseGame.ui

import com.example.horsechallenge.horseGame.ui.model.ItemModel

data class HorseUiState(
    val isPremium: Boolean = false,

    val level:Int = 1,
    val moves:Int = 64,
    val time:String = "00:00",
    val lives:Int = 5,
    val options:Int = 0,
    val optionProgress:Float = 0.0f,
    val bonus: Int = 0,

    val msgGameFinished: String = "",
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val finishedGame: Boolean = false,

    val board: MutableList<MutableList<ItemModel>> = mutableListOf(),
)