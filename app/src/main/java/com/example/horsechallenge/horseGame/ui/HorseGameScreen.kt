package com.example.horsechallenge.horseGame.ui

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.horsechallenge.R
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import com.example.horsechallenge.ui.theme.amaranthFamily

@Composable
fun HorseGameScreen(horseGameViewModel: HorseGameViewModel) {
    val horseUiState by horseGameViewModel.uiState.collectAsState()

    val board by horseGameViewModel.board

    val constraints = horseGameViewModel.homeConstraints()

    ConstraintLayout(constraints){
        AlertFree(Modifier.layoutId("textFreeRef")){}

        Title(Modifier.layoutId("textTitleRef"))

        Level(modifier = Modifier.layoutId("cardLevelRef"),
            level = horseUiState.level
        )

        Moves(modifier = Modifier.layoutId("cardMovesRef"),
            moves = horseUiState.moves
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
            options = horseUiState.options,
            progress = horseUiState.optionProgress
        )

        Board(modifier = Modifier.layoutId("tableRef"), board = board, isAppPremium = false)

        if(horseUiState.isGameOver) {
            FinishedGame(modifier = Modifier.layoutId("finishedGameRef"),
                level = horseUiState.level,
                lives = horseUiState.lives,
                onClickNextLevel = {},
                onClickShareGame = {}
            )
        }

        Credits(modifier = Modifier.layoutId("creditsRef"))

        Publicidad(modifier = Modifier.layoutId("boxPublicityRef"), show = true)
    }


    /*Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Bottom
    ){
        AlertFree(
            show = showAlertFree,
        ) {
            horseGameViewModel.toggleShowAlertFree(showAlertFree)
            horseGameViewModel.togglePremium(isAppPremium)
        }

        Body(
            Modifier.weight(1f),
            level = level,
            moves = moves,
            time = time,
            lives = lives,
            options = options,
            table = table,
            isAppPremium = isAppPremium,
            showFinishedGame = showFinishedGame
        )
        Publicidad(
            show = showAlertFree
        )

    }*/
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


    /*
    //if(show){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
                .padding(vertical = 8.dp)
                .clickable {
                    onClickAlert()
                }, horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Tap here to remove ads, get more levels and unlimited lives",
                fontFamily = amaranthFamily,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.errorContainer
            )
        }
    //}*/
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
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = time,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
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
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = lives.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Options(modifier: Modifier, options: Int, progress:Float) {
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
            style = MaterialTheme.typography.bodySmall
        )
        LinearProgressIndicator(progress = progress)
        Text(
            text = options.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Board(modifier: Modifier, board: List<List<ItemModel>>, isAppPremium: Boolean) {
    Box(modifier = modifier){
        Column {
            board.forEach { fila ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    fila.forEach { item ->
                        ItemTablero(item,isAppPremium)
                    }
                }
            }
        }
    }
}
@Composable
fun ItemTablero(itemModel: ItemModel, isAppPremium: Boolean){//, onClickItem:()->Unit
    val (width, _) = getScreenDimensions(LocalContext.current)
    val sizeBox = width/8

    itemModel.background = if ((itemModel.x+itemModel.y)%2 == 0){
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        if (isAppPremium){
            MaterialTheme.colorScheme.tertiary
        } else{
            MaterialTheme.colorScheme.secondary
        }

    }

    Box(
        modifier = Modifier
            .width(sizeBox.dp)
            .height(sizeBox.dp)
            .background(itemModel.background)
            .clickable {
                //onClickItem()
                if (itemModel.enable) {
                    itemModel.selected = !itemModel.selected
                }
            }
    ){
        if(itemModel.selected){
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.horse_image),
                contentDescription = "im_horse"
            )
        }
    }
}


@Composable
fun FinishedGame(
    modifier: Modifier,
    level:Int, lives:Int,
    onClickNextLevel:() -> Unit,
    onClickShareGame:() -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.onSurfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.game_over_level, level),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = stringResource(R.string.game_over_lives, lives),
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
fun Publicidad(modifier: Modifier, show: Boolean) {
    if(show){
        Box(
            modifier = modifier
                .fillMaxWidth()
                .size(170.dp)
                .background(MaterialTheme.colorScheme.error)
        )
    }
}
