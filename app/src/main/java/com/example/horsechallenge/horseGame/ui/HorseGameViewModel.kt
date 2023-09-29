package com.example.horsechallenge.horseGame.ui

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horsechallenge.horseGame.ui.model.ItemModel
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
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NO_SELECCIONADO = 0
const val SELECCIONADO = 1
const val BONUS = 2
const val MOVES_FOR_BONUS = 3f//3f

@HiltViewModel
class HorseGameViewModel @Inject constructor(
):ViewModel() {

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

    private val _uiState = MutableStateFlow(HorseUiState())
    val uiState: StateFlow<HorseUiState> = _uiState.asStateFlow()

    private var _lastX = 0
    private var _lastY = 0

    private var _moveFirst = true
    private var _nextLevel: Boolean = false

    private var _options:Int = 0
    private var _bonus: Int = 0

    private val _handler = android.os.Handler()
    private var _seconds = 0
    private var _minutes = 0
    private val _runnable = object : Runnable {
        override fun run() {

            if(!_uiState.value.finishedGame && !_moveFirst){
                _seconds++
                if(_seconds%60 == 0){
                    _seconds = 0
                    _minutes++
                    if(_minutes%60 == 0){
                        _minutes = 0
                        finishGame("Timeout :(", true)
                        return
                    }
                }
            }

            _uiState.updateTime(_minutes,_seconds)
            _handler.postDelayed(this, 1000)
        }
    }

    init {
        initGame()
    }

    private fun initGame(){
        _uiState.updateBoard(getBoardMutable())

        _lastX = (0..7).random()
        _lastY = (0..7).random()

        _bonus = 0

        _uiState.updateBoardBoxState(_lastX,_lastY,SELECCIONADO)
        _uiState.updateMoves(63)
        _uiState.updateOptionProgress(0f)
        _uiState.updateFinishedGame(false)
        _uiState.updateBoardAllBackground()
        _uiState.updateTime(_minutes,_seconds)

        checkBoxsAvailable()

    }
    private fun finishGame(msg:String, gameOver:Boolean = false){
        resetTime()

        _uiState.updateFinishedGame(true)
        _uiState.updateMsgGameFinished(msg)
        _uiState.updateScore()

        if(gameOver) _uiState.updateMsgShareGame("Hoy no se pudo...")
        else _uiState.updateMsgShareGame("Soy un crack! las cosas como son...")

        _nextLevel = !gameOver

    }
    private fun checkFinishedGame(){
        if (_uiState.value.movesRemaining > 0){
            if(_options == 0 && _bonus == 0) {
                finishGame("Game Over",true)
            } else if(_options == 0){
                setHabilityBoxesFree(true)
            }
        } else  {
            finishGame("You're Winner !", false)
        }
    }

    fun nextLevel() {
        if(_nextLevel){
            _uiState.updateLevel(_uiState.value.level + 1)
        } else {
            _uiState.updateLives(_uiState.value.lives - 1)
            if (_uiState.value.lives == 0){
                _uiState.updateLevel(1)
                _uiState.updateLives(5)
            }
        }
        initGame()
    }

    fun onSelectedItem(itemModel: ItemModel) {
        if(isBoxAvailable(itemModel.x, itemModel.y) || isBonusUsed(itemModel.x,itemModel.y)){

            initTime()

            setHabilityBoxesFree(false)

            saveLastCoordSelected(itemModel.x,itemModel.y)

            if(isBoxBonus()) _bonus++

            _uiState.updateBoardBoxState(itemModel.x, itemModel.y, SELECCIONADO)
            _uiState.updateMoves(_uiState.value.movesRemaining.dec())
            _uiState.updateBoardAllBackground()

            addBoxBonus()
            checkBoxsAvailable()
        }
    }

    private fun initTime(){
        if (_moveFirst){
            _handler.post(_runnable)
            _moveFirst = false
        }
    }
    private fun resetTime(){
        _handler.removeCallbacks(_runnable)
        _moveFirst = true
        _seconds = 0
        _minutes = 0
    }

    private fun isBonusUsed(x: Int, y: Int): Boolean{
        if(_options == 0){
            if (_uiState.value.board[x][y].boxState == BONUS || _uiState.value.board[x][y].boxState == NO_SELECCIONADO){
                _bonus--
                return true
            }
        }
        return false
    }
    private fun isBoxBonus(): Boolean{
        return _uiState.value.board[_lastX][_lastY].boxState == BONUS
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

        val countAvailable = coodAvailable.count()
        if (countAvailable > 0){
            val boxRandom = (1..countAvailable).random()-1
            _uiState.updateBoardBoxState(
                coodAvailable[boxRandom][0],
                coodAvailable[boxRandom][1],
                BONUS
            )
        }
    }

    private fun isBoxAvailable(x: Int, y: Int): Boolean{

        val difX:Int = x - _lastX
        val difY:Int = y - _lastY

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
        _options = 0

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
        val difX:Int = _lastX + x
        val difY:Int = _lastY + y

        if(difX >= 0 && difY >= 0 && difX <= 7 && difY <= 7){
            if (_uiState.value.board[difX][difY].boxState == NO_SELECCIONADO
                || _uiState.value.board[difX][difY].boxState == BONUS) {
                _options++
                _uiState.updateMovesAvailable()
                _uiState.updateBoardHability(difX, difY, true)
            }
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
            if(box.x == _lastX && box.y == _lastY){
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


    fun shareGame(contexto: Context){
        viewModelScope.launch{

            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,_uiState.value.msgShareGame)
                type = "text/plain"
                putExtra(Intent.EXTRA_TITLE,"Juega HorseChallenge!")
            }
            val shareIntent = Intent.createChooser(intent,null)
            contexto.startActivity(shareIntent)
        }
    }

    private fun saveLastCoordSelected(x: Int, y: Int){
        _lastX = x
        _lastY = y
    }
    fun togglePremium() {
        _uiState.updateIsPremium(!_uiState.value.isPremium)
        _uiState.updateBoardAllBackground()
        checkBoxsAvailable()
    }

    private fun getMovesAvailable():String{
        return if(_bonus!=0){
            "$_options + $_bonus"
        }else{
            "$_options"
        }
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
            it.copy(movesRemaining = moves)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateTime(minutes:Int, seconds:Int){
        val formato = "%02d"
        this.update {
            it.copy(time = "${formato.format(minutes)}:${formato.format(seconds)}")
        }}
    private fun  MutableStateFlow<HorseUiState>.updateLives(lives: Int){
        this.update {
            it.copy(lives = lives)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateMovesAvailable(){
        this.update {
            it.copy(movesAvailable = getMovesAvailable())
        }
    }
    private fun  MutableStateFlow<HorseUiState>.updateOptionProgress(optionProgress: Float){
        this.update {
            it.copy(optionProgress = optionProgress)
        }
    }
    private fun  MutableStateFlow<HorseUiState>.updateScore(){
        this.update {
            it.copy(score = 64-_uiState.value.movesRemaining)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateFinishedGame(finishedGame: Boolean){
        this.update {
            it.copy(finishedGame = finishedGame)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateMsgGameFinished(msgGameFinished: String){
        this.update {
            it.copy(msgGameFinished = msgGameFinished)
        }}
    private fun  MutableStateFlow<HorseUiState>.updateMsgShareGame(msgShareGame: String){
        val movesMade = 64-_uiState.value.movesRemaining

        val invitacion = "Tu podrias hacerlo mejor ?\n"
        val score = "*Mi Puntaje:* $movesMade/64\n"
        val tiempo = "*Mi Tiempo:* ${_uiState.value.time}\n"
        val enlace = "\n*Descarga el Juego !*\nhttps://drive.google.com/file/d/1f-5l6Kud72D9r3JlfVSTAJGx4yFJ_I3s/view?usp=sharing"

        val msg = "$msgShareGame\n $invitacion $score $tiempo $enlace"

        this.update {
            it.copy(msgShareGame = msg)
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