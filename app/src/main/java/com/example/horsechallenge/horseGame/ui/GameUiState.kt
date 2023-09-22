package com.example.horsechallenge.horseGame.ui

import androidx.compose.runtime.MutableState
import com.example.horsechallenge.horseGame.ui.model.ItemModel

data class HorseUiState(
    var isPremium: Boolean = false,

    var level:Int = 1,
    var moves:Int = 64,
    var time:String = "00:00",
    var lives:Int = 5,
    var options:Int = 0,
    var optionProgress:Float = 0.0f,
    var bonus: Int = 0,

    var isGameOver: Boolean = false,
    var gameFinishedTitle: String = "",
    var Score: Int = 0,

    var board: MutableList<MutableList<ItemModel>> = mutableListOf(),
    var boxrefreshScreen: Boolean = false,
)