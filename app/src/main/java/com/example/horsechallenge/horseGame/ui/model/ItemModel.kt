package com.example.horsechallenge.horseGame.ui.model

import androidx.compose.ui.graphics.Color

data class ItemModel(
    val x:Int,
    val y:Int,
    var enable:Boolean = true,
    var selected:Boolean = false,
    var background: Color = Color.White,
)
