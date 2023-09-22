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
        val tableAux = getBoardMutable(true)

        lastX.value = (0..7).random()
        lastY.value = (0..7).random()

        tableAux[lastX.value][lastY.value].boxState = 1
        tableAux[lastX.value][lastY.value].background = Color(0xFF0DFCFC)

        update_uiState(board = tableAux)
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
        update_uiState(isPremium = !_uiState.value.isPremium)
        updateBackgroundBoard()
    }
    fun updateBackgroundBoard(){

    }

    fun onSelectedItem(itemModel: ItemModel) {
        if(isBoxAvailable(itemModel.x, itemModel.y)){

            cleanBoxAvailable()
            val boardMutable = getBoardMutable()
            boardMutable[lastX.value][lastY.value].background = Color(0xFFB6B6B6)
            boardMutable[itemModel.x][itemModel.y].boxState = 1
            boardMutable[itemModel.x][itemModel.y].background = Color(0xFF0DFCFC)

            saveLastCoordSelected(itemModel.x,itemModel.y)
            update_uiState(
                board = boardMutable,
                //moves = _uiState.value.moves.dec()
            )
            checkBoxsAvailable()

            toggleBoxRefreshScreen()
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
                val boardMutable = getBoardMutable()
                boardMutable[dif_x][dif_y].background = getBoxColor(dif_x,dif_y)

                update_uiState(board = boardMutable)
            }
        }
    }

    fun saveLastCoordSelected(x: Int, y: Int){
        lastX.value = x
        lastY.value = y
    }

    fun isBoxAvailable(x: Int, y: Int): Boolean{

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
    fun checkBoxsAvailable(){
        update_uiState(options = 0)

        checkMove(-2,-1)
        checkMove(-2,1)
        checkMove(2,-1)
        checkMove(2,1)
        checkMove(-1,2)
        checkMove(-1,-2)
        checkMove(1,2)
        checkMove(1,-2)

        if(_uiState.value.options == 0) update_uiState(isGameOver = true)
        else  update_uiState(isGameOver = false)
    }
    private fun checkMove(x: Int, y: Int) {
        val dif_x:Int = lastX.value + x
        val dif_y:Int = lastY.value + y

        if(dif_x >= 0 && dif_y >= 0 && dif_x <= 7 && dif_y <= 7){
            if (_uiState.value.board[dif_x][dif_y].boxState == 0) {
                val boardMutable = getBoardMutable()
                boardMutable[dif_x][dif_y].background = Color(0xFFCBF897)
                update_uiState(board = boardMutable, options = _uiState.value.options.inc())
            }
        }
    }

    fun toggleBoxRefreshScreen(){
        _uiState.update { box ->
            box.copy(boxrefreshScreen = !_uiState.value.boxrefreshScreen)
        }
    }

    fun getBoxColor(x: Int,y: Int): Color{
        return if((x+y)%2 != 0){
            md_theme_light_onSecondary
        } else if (_uiState.value.isPremium){
            md_theme_light_tertiary
        } else {
            md_theme_light_secondary
        }
    }
    fun getBoardMutable(reset: Boolean = false): MutableList<MutableList<ItemModel>>{
        val boardAuxState: MutableList<MutableList<ItemModel>> = mutableListOf()

        if (_uiState.value.board.isEmpty() || reset){
            for (i in 0 until 8){
                val newRow: MutableList<ItemModel> = mutableListOf()
                for (j in 0 until 8){
                        newRow.add(ItemModel(x = i, y = j, background = getBoxColor(i,j)))
                }
                boardAuxState.add(newRow)
            }
        } else {
            for (filaOriginal in _uiState.value.board) {
                val filaMutable: MutableList<ItemModel> = filaOriginal.toMutableList()
                boardAuxState.add(filaMutable)
            }
        }

        return boardAuxState
    }
    fun update_uiState(
        isPremium: Boolean = _uiState.value.isPremium,
        level:Int = _uiState.value.level,
        moves:Int = _uiState.value.moves,
        time:String = _uiState.value.time,
        lives:Int = _uiState.value.lives,
        options:Int = _uiState.value.options,
        optionProgress:Float = _uiState.value.optionProgress,
        isGameOver: Boolean = _uiState.value.isGameOver,
        boxrefreshScreen: Boolean = _uiState.value.boxrefreshScreen,
        board: MutableList<MutableList<ItemModel>> = mutableListOf(),
    ){
        if (board.isEmpty()){
            getBoardMutable().forEach{
                board.add(it)
            }
        }
        _uiState.update { boardAux ->
            boardAux.copy(
                isPremium = isPremium,
                level = level,
                moves = moves,
                time = time,
                lives = lives,
                options = options,
                optionProgress = optionProgress,
                isGameOver = isGameOver,
                boxrefreshScreen = boxrefreshScreen,
                board = board
            )
        }
    }
}
