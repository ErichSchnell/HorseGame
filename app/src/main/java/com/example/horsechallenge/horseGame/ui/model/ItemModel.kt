package com.example.horsechallenge.horseGame.ui.model

import androidx.compose.ui.graphics.Color
import com.example.horsechallenge.ui.theme.md_theme_light_onSecondary

data class ItemModel(
    val x:Int,
    val y:Int,
    var boxState:Int = 0,
    var background: Color = md_theme_light_onSecondary,
)
