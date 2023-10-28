package com.example.horsechallenge.horseGame.ui

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.horsechallenge.R
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import com.example.horsechallenge.model.Routes
import com.example.horsechallenge.ui.theme.amaranthFamily

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HorseGameScreen(horseGameViewModel: HorseGameViewModel, navigationController: NavHostController) {

    val context = LocalContext.current
    val horseUiState by horseGameViewModel.uiState.collectAsState()
    val homeConstraints = horseGameViewModel.homeConstraints()
    val bodyConstraints = horseGameViewModel.bodyConstraints()

    ConstraintLayout(constraintSet = homeConstraints, modifier = Modifier.fillMaxSize()){
        ConstraintLayout(constraintSet = bodyConstraints, modifier = Modifier
            .verticalScroll(rememberScrollState())
            .layoutId("constraintRef")){
            Title(Modifier.layoutId("textTitleRef"))

            Level(modifier = Modifier.layoutId("cardLevelRef"),
                level = horseUiState.level
            )

            Moves(modifier = Modifier.layoutId("cardMovesRef"),
                moves = horseUiState.movesRemaining
            )
            Time(modifier = Modifier.layoutId("cardTimeRef"),
                time = horseUiState.time
            )
            Lives(
                modifier = Modifier.layoutId("cardLivesRef"),
                lives = horseUiState.lives,
                isPremium = horseUiState.isPremium
            )
            Options(modifier = Modifier.layoutId("cardOptionsRef"),
                options = horseUiState.movesAvailable ,
                progress = horseUiState.optionProgress
            )

            Board(
                modifier = Modifier.layoutId("tableRef"),
                board = horseUiState.board,
                onClickItem = { horseGameViewModel.onSelectedItem(it)}
            )

            if(horseUiState.finishedGame) {
                FinishedGame(modifier = Modifier.layoutId("finishedGameRef"),
                    title = horseUiState.msgGameFinished,
                    score = horseUiState.score,
                    onClickNextLevel = {horseGameViewModel.nextLevel()}
                ) {horseGameViewModel.shareGame(context)}
            }

            Credits(modifier = Modifier.layoutId("creditsRef"))
            Box (Modifier.layoutId("box1Ref").fillMaxWidth().height(50.dp).background(Color.Yellow)){}
            Box (Modifier.layoutId("box2Ref").fillMaxWidth().height(50.dp).background(Color.Red)){}
            Box (Modifier.layoutId("box3Ref").fillMaxWidth().height(50.dp).background(Color.Black)){}
            Box (Modifier.layoutId("box4Ref").fillMaxWidth().height(50.dp).background(Color.Gray)){}
            Box (Modifier.layoutId("box5Ref").fillMaxWidth().height(50.dp).background(Color.Green)){}
            Box (Modifier.layoutId("box6Ref").fillMaxWidth().height(50.dp).background(Color.Blue)){}
            Box (Modifier.layoutId("box7Ref").fillMaxWidth().height(50.dp).background(Color.Yellow)){}
            Box (Modifier.layoutId("box8Ref").fillMaxWidth().height(50.dp).background(Color.Red)){}
            Box (Modifier.layoutId("box9Ref").fillMaxWidth().height(50.dp).background(Color.Magenta)){}
        }

        AlertFree(Modifier.layoutId("textFreeRef")){
            navigationController.navigate(Routes.PayPremium.route)
            //horseGameViewModel.togglePremium()
        }

        if(!horseUiState.isPremium) {
            Publicidad(modifier = Modifier.layoutId("boxPublicityRef"))
        }
    }
}

@Composable
fun AlertFree(modifier: Modifier, onClickAlert: () -> Unit) {
    Box(modifier = modifier
        .background(MaterialTheme.colorScheme.error)
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .clickable {
            onClickAlert()
        },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(id = R.string.alert_free),
            fontFamily = amaranthFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.errorContainer
        )
    }
}

@Composable
fun Title(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.title_game),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}
@Composable
fun Level(modifier: Modifier, level: Int) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            .size(60.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.level),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = level.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Moves(moves: Int, modifier: Modifier) {
    Card(
        modifier = modifier
            .width(80.dp)
            .size(60.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.moves),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = moves.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Time(modifier: Modifier, time: String) {
    Card(
        modifier = modifier
            .width(80.dp)
            .size(60.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.time),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = time,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Lives(modifier: Modifier, lives: Int, isPremium: Boolean = false) {
    //premiumColor()
    val cardColor = if (isPremium) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.error
    }

    Card(
        modifier = modifier
            .width(80.dp)
            .size(60.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.lives),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = lives.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Options(modifier: Modifier, options: String, progress:Float) {
    Card(
        modifier = modifier
            .width(80.dp)
            .size(60.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.options),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        LinearProgressIndicator(progress = progress)
        Text(
            text = options,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Board(
    modifier: Modifier,
    board: List<List<ItemModel>>,
    onClickItem:(ItemModel) -> Unit
) {
     Column(modifier = modifier.padding(vertical = 16.dp)) {
        board.forEach { fila ->
            Row(modifier = Modifier.fillMaxWidth()) {
                fila.forEach { item ->
                    ItemTablero(
                        item,
                        onClickItem
                    )
                }
            }
        }
    }
}
@Composable
fun ItemTablero(
    itemModel: ItemModel,
    onClickItem: (ItemModel) -> Unit
){
    val (width, _) = getScreenDimensions(LocalContext.current)
    val sizeBox = width/8

    Box(
        modifier = Modifier
            .width(sizeBox.dp)
            .height(sizeBox.dp)
            .background(itemModel.background)
            .clickable {
                onClickItem(itemModel)
            }
    ){
        if(itemModel.boxState == SELECCIONADO){
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.iv_horse),
                contentDescription = "im_horse"
            )
        }
        if(itemModel.boxState == BONUS){
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.iv_mangekyou_bonus),
                contentDescription = "im_bonus"
            )
        }
        if(itemModel.hability){
            Image(
                modifier = Modifier
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.iv_box_hability),
                contentDescription = "im_bonus"
            )
        }
    }
}


@Composable
fun FinishedGame(
    modifier: Modifier,
    title: String, score:Int,
    onClickNextLevel:() -> Unit,
    onClickShareGame:() -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.onSurfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = stringResource(R.string.score, score),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            NextLevel(onClickNextLevel)
            ShareGame(onClickShareGame)
        }
    }
}
@Composable
fun NextLevel(onClickNextLevel: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(20.dp),
        onClick = {onClickNextLevel()}
    ) {
        Text(
            text = stringResource(id = R.string.next_level),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.background
        )
    }
}
@Composable
fun ShareGame(onClickShareGame: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ){
        Icon(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClickShareGame() },
            imageVector = Icons.Default.Share,
            contentDescription = "ic_Share",
            tint = MaterialTheme.colorScheme.background
        )
    }
}

@Composable
fun Credits(modifier: Modifier){
    Column(modifier = modifier.padding(top = 8.dp)) {
        Text(text = stringResource(id = R.string.instructions), fontSize = 12.sp, fontFamily = amaranthFamily, fontWeight = FontWeight.Normal)
        Text(text = stringResource(id = R.string.credits), fontSize = 12.sp, fontFamily = amaranthFamily, fontWeight = FontWeight.Normal)
    }
}

fun getScreenDimensions(context: Context): Pair<Float, Float> {
    val displayMetrics = DisplayMetrics()
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)

    val widthDp = displayMetrics.widthPixels / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    val heightDp = displayMetrics.heightPixels / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

    return Pair(widthDp, heightDp)
}

@Composable
fun Publicidad(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .size(140.dp)
            .background(MaterialTheme.colorScheme.error)
    )
}
