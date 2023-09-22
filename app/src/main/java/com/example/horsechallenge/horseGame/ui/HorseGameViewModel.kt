package com.example.horsechallenge.horseGame.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HorseGameViewModel @Inject constructor(
):ViewModel() {
    private val _uiState = MutableStateFlow(HorseUiState())
    val uiState: StateFlow<HorseUiState> = _uiState.asStateFlow()

    private val lastX = mutableStateOf(0)
    private val lastY = mutableStateOf(0)

    init {
        initBoard()
    }
    fun homeConstraints(): ConstraintSet {
        return ConstraintSet{
            val textFreeRef = createRefFor("textFreeRef")
            val textTitleRef = createRefFor("textTitleRef")
            val cardLevelRef = createRefFor("cardLevelRef")
            val cardMovesRef = createRefFor("cardMovesRef")
            val cardTimeRef = createRefFor("cardTimeRef")
            val cardLivesRef = createRefFor("cardLivesRef")
            val cardOptionsRef = createRefFor("cardOptionsRef")
            val tableRef= createRefFor("tableRef")
            val finishedGameRef= createRefFor("finishedGameRef")
            val creditsRef= createRefFor("creditsRef")
            val boxPublicityRef = createRefFor("boxPublicityRef")
            val topTitleGuide = createGuidelineFromTop(0.08f)

            constrain(textFreeRef){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(textTitleRef){
                top.linkTo(topTitleGuide)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width
            }

            constrain(cardLevelRef){
                top.linkTo(textTitleRef.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(cardMovesRef){
                top.linkTo(cardLevelRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(cardTimeRef.start)
            }
            constrain(cardTimeRef){
                top.linkTo(cardLevelRef.bottom)
                start.linkTo(cardMovesRef.end)
                end.linkTo(cardLivesRef.start)
            }
            constrain(cardLivesRef){
                top.linkTo(cardLevelRef.bottom)
                start.linkTo(cardTimeRef.end)
                end.linkTo(cardOptionsRef.start)
            }
            constrain(cardOptionsRef){
                top.linkTo(cardLevelRef.bottom)
                start.linkTo(cardLivesRef.end)
                end.linkTo(parent.end)
            }
            createHorizontalChain(cardMovesRef,cardTimeRef,cardLivesRef,cardOptionsRef, chainStyle = ChainStyle.Spread)

            constrain(tableRef){
                top.linkTo(cardMovesRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(boxPublicityRef.top)
            }
            constrain(finishedGameRef){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            constrain(creditsRef){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(tableRef.bottom)
            }

            constrain(boxPublicityRef){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        }
    }

    fun togglePremium() {
        _uiState.update { horseGame ->
            horseGame.copy(isPremium = !_uiState.value.isPremium)
        }
        //updateBoard()
    }

    fun initBoard(){
        val tableAux: MutableList<MutableList<ItemModel>> = mutableListOf()

        for (i in 0 until 8){
            val newRow: MutableList<ItemModel> = mutableListOf()
            for (j in 0 until 8){

                val colorBox = if ((i+j)%2 == 0){
                    Color(0xFFE9C866)
                } else {
                    if (_uiState.value.isPremium){
                        Color(0xFF1C6F20)
                    } else{
                        Color(0xFFFF9800)
                    }
                }
                newRow.add(ItemModel(x = i, y = j, background = colorBox))
            }
            tableAux.add(newRow)
        }

        updateLastCoord((0..7).random(),(0..7).random())
        tableAux[lastX.value][lastY.value] = tableAux[lastX.value][lastY.value].copy(
            selected = true,
            enable = false,
            background = Color(0xFF0DFCFC)
        )
        refreshBoxEnable(lastX.value,lastY.value)

        _uiState.value = HorseUiState(board = tableAux)
    }

    fun onSelectedItem(itemModel: ItemModel) {
        if(itemModel.enable){

            val boardAuxState: MutableList<MutableList<ItemModel>> = mutableListOf()
            for (filaOriginal in _uiState.value.board) {
                val filaMutable: MutableList<ItemModel> = filaOriginal.toMutableList()
                boardAuxState.add(filaMutable)
            }
            boardAuxState[lastX.value][lastY.value].background = Color(0xFFB6B6B6)

            boardAuxState[itemModel.x][itemModel.y] = boardAuxState[itemModel.x][itemModel.y].copy(
                selected = !itemModel.selected,
                enable = !itemModel.enable,
                background = Color(0xFF0DFCFC)
            )

            updateLastCoord(itemModel.x,itemModel.y)
            _uiState.update { boardAux ->
                boardAux.copy(board = boardAuxState)
            }
        }
        toggleBoxRefreshScreen()
    }

    fun toggleBoxRefreshScreen(){
        _uiState.update { box ->
            box.copy(boxrefreshScreen = !_uiState.value.boxrefreshScreen)
        }
    }

    fun updateLastCoord(x: Int, y: Int){
        lastX.value = x
        lastY.value = y
    }

    fun refreshBoxEnable(x: Int, y: Int){
        val difX = x
        val difY = y


    }
}
