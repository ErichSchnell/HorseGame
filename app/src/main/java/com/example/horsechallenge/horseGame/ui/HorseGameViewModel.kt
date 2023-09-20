package com.example.horsechallenge.horseGame.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HorseGameViewModel @Inject constructor(
):ViewModel() {

    private val _uiState:StateFlow<GameUiState>
    val uiState: StateFlow<ItemModel> get() = _uiState.asStateFlow()

    private var _isAppPremium = MutableStateFlow<Boolean>(false)
    val isAppPremium: StateFlow<Boolean> = _isAppPremium

    private var _showFinishedGame = MutableLiveData<Boolean>()
    val showFinishedGame: LiveData<Boolean> = _showFinishedGame

    private var _showAlertFree = MutableLiveData<Boolean>()
    val showAlertFree: LiveData<Boolean> = _showAlertFree

    private var _level = MutableLiveData<Int>()
    val level: LiveData<Int> = _level

    private var _moves = MutableLiveData<Int>()
    val moves: LiveData<Int> = _moves

    private var _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private var _lives = MutableLiveData<Int>()
    val lives: LiveData<Int> = _lives

    private var _options = MutableLiveData<Int>()
    val options: LiveData<Int> = _options

    private var _board = mutableStateOf<List<List<ItemModel>>>(emptyList())
    val board: MutableState<List<List<ItemModel>>> = _board

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

    fun toggleShowAlertFree(state:Boolean) {
        _showAlertFree.value = !state
    }

    fun togglePremium(appPremium: Boolean) {
        _isAppPremium.value = !appPremium
    }

    private fun initBoard(){
        val tableAux: MutableList<MutableList<ItemModel>> = mutableListOf()
        for (i in 0 until 8){
            val newRow: MutableList<ItemModel> = mutableListOf()
            for (j in 0 until 8){
                newRow.add(ItemModel(x = i, y = j))
            }
            tableAux.add(newRow)
        }
        tableAux[(0..7).random()][(0..7).random()].selected = true
        _board.value = tableAux
    }
    //private var _lastTaskSelected = TaskModel()
//    fun onAddTaskDialogOpen() {
//        _showAddTaskDialog.value = true
//    }
//    fun onAddTaskDialogClose() {
//        _showAddTaskDialog.value = false
//    }
//    fun onOptionDialogOpen(taskModel: TaskModel) {
//        _showOptionDialog.value = true
//        _lastTaskSelected = taskModel
//    }
//    fun onOptionDialogClose() {
//        _showOptionDialog.value = false
//    }
//    fun onTasksCreaded(nameTask: String) {
//        _showAddTaskDialog.value = false
//        viewModelScope.launch {
//            addTaskUseCase(TaskModel(task = nameTask))
//        }
//    }

//    fun onClickCheckBoxSelected(taskModel: TaskModel) {
//        viewModelScope.launch {
//            editTaskUseCase(taskModel.copy(selected = !taskModel.selected))
//        }
//    }
//    fun getActualizarName(): String = _lastTaskSelected.task
//    fun onEditTaskDialogOpen(taskModel: TaskModel = _lastTaskSelected) {
//        _showEditDialog.value = true
//        _lastTaskSelected = taskModel
//    }
//    fun onEditTaskDialogClose() {
//        _showEditDialog.value = false
//    }
//    fun onUpdateNameTaks(name:String){
//        _showEditDialog.value = false
//        viewModelScope.launch {
//            editTaskUseCase(_lastTaskSelected.copy(task = name))
//        }
//    }

//    fun onClickDeleteTask(taskModel: TaskModel = _lastTaskSelected) {
//        viewModelScope.launch {
//            deleteTaskUseCase(taskModel)
//        }
//    }
}