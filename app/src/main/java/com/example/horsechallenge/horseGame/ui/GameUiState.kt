package com.example.horsechallenge.horseGame.ui

import com.example.horsechallenge.horseGame.ui.model.ItemModel

data class HorseUiState(
    val isPremium: Boolean = false,

    val level:Int = 1,
    val moves:Int = 60,
    val time:String = "00:00",
    val lives:Int = 5,
    val options:Int = 0,
    val optionProgress:Float = 0.0f,

    val isGameOver: Boolean = false,

    val board: List<List<ItemModel>> = emptyList(),

    val boxrefreshScreen: Boolean = false,
)