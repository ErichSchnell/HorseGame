package com.example.horsechallenge.horseGame.ui

import com.example.horsechallenge.horseGame.ui.model.ItemModel

sealed interface GameUiState {
    object Loading:GameUiState
    data class Error(val throwable: Throwable):GameUiState
    data class Success(val board: List<List<ItemModel>>):GameUiState
}