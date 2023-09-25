package com.example.horsechallenge.horseGame.ui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import com.example.horsechallenge.ui.theme.md_theme_box_habilted
import com.example.horsechallenge.ui.theme.md_theme_box_selected
import com.example.horsechallenge.ui.theme.md_theme_box_selected_bf
import com.example.horsechallenge.ui.theme.md_theme_light_onSecondary
import com.example.horsechallenge.ui.theme.md_theme_light_secondary
import com.example.horsechallenge.ui.theme.md_theme_light_tertiary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

val NO_SELECCIONADO = 0
val SELECCIONADO = 1
val BONUS = 2
val HABILITADO = 3

val MOVES_FOR_BONUS = 2f

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

        _uiState.updateBoard(getBoardMutable())

        lastX.value = (0..7).random()
        lastY.value = (0..7).random()

        _uiState.updateBoardBoxState(lastX.value,lastY.value,SELECCIONADO)
        _uiState.updateMoves(63)
        _uiState.updateTime("00:00")
        _uiState.updateOptionProgress(0.0f)
        _uiState.updateBonus(0)
        _uiState.updateFinishedGame(false)
        _uiState.updateBoardAllBackground()

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
                top.linkTo(cardMovesRef.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            constrain(finishedGameRef){
                top.linkTo(tableRef.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(tableRef.bottom)
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
        _uiState.updateIsPremium(!_uiState.value.isPremium)
        _uiState.updateBoardAllBackground()
        checkBoxsAvailable()
    }

    fun onSelectedItem(itemModel: ItemModel) {
        if(isBoxAvailable(itemModel.x, itemModel.y) || isBonusUsed(itemModel.x,itemModel.y)){
            setHabilityBoxesFree(false)

            saveLastCoordSelected(itemModel.x,itemModel.y)

            if(isBoxBonus()) _uiState.updateBonus(_uiState.value.bonus.inc())

            _uiState.updateBoardBoxState(itemModel.x, itemModel.y, SELECCIONADO)
            _uiState.updateMoves(_uiState.value.moves.dec())
            _uiState.updateBoardAllBackground()

            addBoxBonus()
            checkBoxsAvailable()
        }
    }
    private fun isBonusUsed(x: Int, y: Int): Boolean{
        if(_uiState.value.options == 0){
            if (_uiState.value.board[x][y].boxState == BONUS
                || _uiState.value.board[x][y].boxState == NO_SELECCIONADO){
                _uiState.updateBonus(_uiState.value.bonus.dec())
                return true
            }
        }
        return false
    }
    private fun isBoxBonus(): Boolean{
        return _uiState.value.board[lastX.value][lastY.value].boxState == BONUS
    }
    private fun addBoxBonus() {
        val incremento:Float = 1 / MOVES_FOR_BONUS

        if(_uiState.value.optionProgress >= 1.0f){
            _uiState.updateOptionProgress(0f)
            agregarBoxBonus()
        } else {
            _uiState.updateOptionProgress(_uiState.value.optionProgress + incremento)
        }
    }

    private fun agregarBoxBonus() {
        val coodAvailable:MutableList<MutableList<Int>> = mutableListOf()

        _uiState.value.board.forEach{ row ->
            row.forEach { item ->
                if(item.boxState == NO_SELECCIONADO){
                    coodAvailable.add(mutableListOf(item.x,item.y))
                }
            }
        }

        val countAvailable = coodAvailable.size
        if (countAvailable > 0){
            val boxRandom = (1..countAvailable).random()
            _uiState.updateBoardBoxState(
                coodAvailable[boxRandom][0],
                coodAvailable[boxRandom][1],
                BONUS
            )
        }
    }

//    private fun cleanBoxAvailable() {
//        cleanBox(-2,-1)
//        cleanBox(-2,1)
//        cleanBox(2,-1)
//        cleanBox(2,1)
//        cleanBox(-1,2)
//        cleanBox(-1,-2)
//        cleanBox(1,2)
//        cleanBox(1,-2)
//    }
//    private fun cleanBox(x: Int, y: Int) {
//        val difX:Int = lastX.value + x
//        val difY:Int = lastY.value + y
//
//        if(difX >= 0 && difY >= 0 && difX <= 7 && difY <= 7){
//            if (_uiState.value.board[difX][difY].boxState == NO_SELECCIONADO
//                || _uiState.value.board[difX][difY].boxState == BONUS) {
//                _uiState.updateBoardHability(difX, difY, false)
//            }
//        }
//    }

    private fun saveLastCoordSelected(x: Int, y: Int){
        lastX.value = x
        lastY.value = y
    }

    private fun isBoxAvailable(x: Int, y: Int): Boolean{

        val difX:Int = x - lastX.value
        val difY:Int = y - lastY.value

        if(_uiState.value.board[x][y].boxState == SELECCIONADO) return false

        if ( difX == -2 && difY == -1 )   return true
        if ( difX == -2 && difY == 1  )   return true
        if ( difX == 2  && difY == -1 )   return true
        if ( difX == 2  && difY == 1  )   return true
        if ( difX == -1 && difY == -2 )   return true
        if ( difX == -1 && difY == 2  )   return true
        if ( difX == 1  && difY == -2 )   return true
        if ( difX == 1  && difY == 2  )   return true
        return false
    }
    private fun checkBoxsAvailable(){
        _uiState.updateOptions(0)

        checkMove(-2,-1)
        checkMove(-2,1)
        checkMove(2,-1)
        checkMove(2,1)
        checkMove(-1,2)
        checkMove(-1,-2)
        checkMove(1,2)
        checkMove(1,-2)

        checkFinishedGame()
    }
    private fun checkMove(x: Int, y: Int) {
        val difX:Int = lastX.value + x
        val difY:Int = lastY.value + y

        if(difX >= 0 && difY >= 0 && difX <= 7 && difY <= 7){
            if (_uiState.value.board[difX][difY].boxState == NO_SELECCIONADO
                || _uiState.value.board[difX][difY].boxState == BONUS) {
                _uiState.updateOptions(_uiState.value.options.inc())
                _uiState.updateBoardHability(difX, difY, true)
            }
        }
    }
    private fun checkFinishedGame(){
        if (_uiState.value.moves > 0){
            if(_uiState.value.options == 0 && _uiState.value.bonus == 0) {
                _uiState.updatemessegeGameFinished("Game Over")
                _uiState.updateIsGameOver(isGameOver = true)
                _uiState.updateFinishedGame(true)
            } else if(_uiState.value.options == 0){
                setHabilityBoxesFree(true)
            }
        } else  {
            _uiState.updatemessegeGameFinished("You're Winner !")
            _uiState.updateScore(_uiState.value.score + 10)
            _uiState.updateFinishedGame(true)
        }
    }

    private fun setHabilityBoxesFree(state: Boolean) {
        _uiState.value.board.forEach { row ->
            row.forEach {
                if(it.boxState == NO_SELECCIONADO || it.boxState == BONUS){
                    _uiState.updateBoardHability(it.x,it.y,state)
                }
            }
        }
    }

    private fun getBoxColor(box: ItemModel): Color{
        return if(box.boxState == SELECCIONADO){
            if(box.x == lastX.value && box.y == lastY.value){
                md_theme_box_selected
            } else {
                md_theme_box_selected_bf
            }
        } else getInitBoxColor(box.x,box.y)
    }
    private fun getInitBoxColor(x: Int, y: Int): Color{
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
                    newRow.add(ItemModel(
                        x = i, y = j,
                        background = getInitBoxColor(i,j)
                    ))
            }
            boardAuxState.add(newRow)
        }

        return boardAuxState
    }


    /*
     * ---- FUNCIONES DE ORDEN SUPERIOR SOBRE _uiState ----
     */
    private fun  MutableStateFlow<HorseUiState>.updateIsPremium(isPremium: Boolean){
        this.update {
            it.copy(isPremium = isPremium)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateLevel(level: Int){
        this.update {
            it.copy(level = level)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateMoves(moves: Int){
        this.update {
            it.copy(moves = moves)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateTime(time: String){
        this.update {
            it.copy(time = time)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateLives(lives: Int){
        this.update {
            it.copy(lives = lives)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateOptions(options: Int){
        this.update {
            it.copy(options = options)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateOptionProgress(optionProgress: Float){
        this.update {
            it.copy(optionProgress = optionProgress)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateBonus(bonus: Int){
        this.update {
            it.copy(bonus = bonus)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateScore(score: Int){
        this.update {
            it.copy(score = score)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateIsGameOver(isGameOver: Boolean){
        this.update {
            it.copy(isGameOver = isGameOver)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateFinishedGame(finishedGame: Boolean){
        this.update {
            it.copy(finishedGame = finishedGame)
        }}
    private fun  MutableStateFlow<HorseUiState>.updatemessegeGameFinished(messegeGameFinished: String){
        this.update {
            it.copy(msgGameFinished = messegeGameFinished)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateBoard(board: MutableList<MutableList<ItemModel>>){
        this.update {
            it.copy(board = board)
        }
    }
    private fun  MutableStateFlow<HorseUiState>.updateBoardBoxState(x: Int, y: Int, boxState: Int){
        this.value.board[x][y] = this.value.board[x][y].copy(
            boxState = boxState
        )
    }
    private fun  MutableStateFlow<HorseUiState>.updateBoardHability(x: Int, y: Int, hability: Boolean){
        this.value.board[x][y] = this.value.board[x][y].copy(
            hability = hability
        )
    }
    private fun  MutableStateFlow<HorseUiState>.updateBoardBackground(x: Int, y: Int, background: Color){
        this.value.board[x][y] = this.value.board[x][y].copy(
            background = background
        )
    }
    private fun  MutableStateFlow<HorseUiState>.updateBoardAllBackground(){
        this.value.board.forEach{ row ->
            row.forEach{ box ->
                if(this.value.board[box.x][box.y].background != getBoxColor(box)){
                    this.value.board[box.x][box.y] = this.value.board[box.x][box.y].copy(
                        background = getBoxColor(box)
                    )
                }
            }
        }
    }
}