package com.example.horsechallenge.horseGame.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import com.example.horsechallenge.ui.theme.md_theme_light_onSecondary
import com.example.horsechallenge.ui.theme.md_theme_light_secondary
import com.example.horsechallenge.ui.theme.md_theme_light_tertiary
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
    fun initBoard(){

        _uiState.value.board = getBoardMutable()

        lastX.value = (0..7).random()
        lastY.value = (0..7).random()

        _uiState.value.board[lastX.value][lastY.value].boxState = 1
        _uiState.value.board[lastX.value][lastY.value].background = Color(0xFF0DFCFC)
        _uiState.value.moves = 64
        _uiState.value.time = "00:00"
        _uiState.value.bonus = 0

        checkBoxsAvailable()
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
        _uiState.value.isPremium = !_uiState.value.isPremium
    }

    fun onSelectedItem(itemModel: ItemModel) {
        if(isBoxAvailable(itemModel.x, itemModel.y)){

            cleanBoxAvailable()

            _uiState.value.board[lastX.value][lastY.value].background = Color(0xFFB6B6B6)
            _uiState.value.board[itemModel.x][itemModel.y].boxState = 1
            _uiState.value.board[itemModel.x][itemModel.y].background = Color(0xFF0DFCFC)
            _uiState.value.moves -= _uiState.value.moves

            saveLastCoordSelected(itemModel.x,itemModel.y)

            checkBoxsAvailable()

            _uiState.update { it ->
                it.copy(
                    boxrefreshScreen = !_uiState.value.boxrefreshScreen
                )
            }
//            _uiState.value.boxrefreshScreen = !_uiState.value.boxrefreshScreen
        }
    }

    private fun cleanBoxAvailable() {
        cleanBox(-2,-1)
        cleanBox(-2,1)
        cleanBox(2,-1)
        cleanBox(2,1)
        cleanBox(-1,2)
        cleanBox(-1,-2)
        cleanBox(1,2)
        cleanBox(1,-2)
    }
    private fun cleanBox(x: Int, y: Int) {
        val dif_x:Int = lastX.value + x
        val dif_y:Int = lastY.value + y

        if(dif_x >= 0 && dif_y >= 0 && dif_x <= 7 && dif_y <= 7){
            if (_uiState.value.board[dif_x][dif_y].boxState == 0) {
                _uiState.value.board[dif_x][dif_y].background = getBoxColor(dif_x,dif_y)
            }
        }
    }

    private fun saveLastCoordSelected(x: Int, y: Int){
        lastX.value = x
        lastY.value = y
    }

    private fun isBoxAvailable(x: Int, y: Int): Boolean{

        val dif_x:Int = x - lastX.value
        val dif_y:Int = y - lastY.value

        if(_uiState.value.board[x][y].boxState == 1) return false

        if ( dif_x == -2 && dif_y == -1 )   return true
        if ( dif_x == -2 && dif_y == 1  )   return true
        if ( dif_x == 2  && dif_y == -1 )   return true
        if ( dif_x == 2  && dif_y == 1  )   return true
        if ( dif_x == -1 && dif_y == -2 )   return true
        if ( dif_x == -1 && dif_y == 2  )   return true
        if ( dif_x == 1  && dif_y == -2 )   return true
        if ( dif_x == 1  && dif_y == 2  )   return true

        return false
    }
    private fun checkBoxsAvailable(){

        _uiState.value.options = 0

        checkMove(-2,-1)
        checkMove(-2,1)
        checkMove(2,-1)
        checkMove(2,1)
        checkMove(-1,2)
        checkMove(-1,-2)
        checkMove(1,2)
        checkMove(1,-2)

        if(_uiState.value.moves > 0){
            if(_uiState.value.options == 0 && _uiState.value.bonus == 0){
                _uiState.value.isGameOver = true
            }
        } else {
            _uiState.value.isGameOver = false
        }
    }
    private fun checkMove(x: Int, y: Int) {
        val difX:Int = lastX.value + x
        val difY:Int = lastY.value + y

        if(difX >= 0 && difY >= 0 && difX <= 7 && difY <= 7){
            if (_uiState.value.board[difX][difY].boxState == 0) {
                _uiState.value.board[difX][difY].background = Color(0xFFCBF897)
                _uiState.value.options += 1
            }
        }
    }

    private fun getBoxColor(x: Int, y: Int): Color{
        return if((x+y)%2 != 0){
            md_theme_light_onSecondary
        } else if (_uiState.value.isPremium){
            md_theme_light_tertiary
        } else {
            md_theme_light_secondary
        }
    }
    private fun getBoardMutable(): MutableList<MutableList<ItemModel>>{
        val boardAuxState: MutableList<MutableList<ItemModel>> = mutableListOf()

        for (i in 0 until 8){
            val newRow: MutableList<ItemModel> = mutableListOf()
            for (j in 0 until 8){
                    newRow.add(ItemModel(x = i, y = j, background = getBoxColor(i,j)))
            }
            boardAuxState.add(newRow)
        }

        return boardAuxState
    }
}
