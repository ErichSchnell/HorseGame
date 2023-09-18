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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.horsechallenge.R
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import com.example.horsechallenge.ui.theme.amaranthFamily

fun homeConstraints(): ConstraintSet{
    return ConstraintSet{
        val textFreeRef = createRefFor("textFreeRef")
        val textTitleRef = createRefFor("textTitleRef")
        val cardLevelRef = createRefFor("cardLevelRef")
        val cardMovesRef = createRefFor("cardMovesRef")
        val cardTimeRef = createRefFor("cardTimeRef")
        val cardLivesRef = createRefFor("cardLivesRef")
        val cardOptionsRef = createRefFor("cardOptionsRef")
        val boxPublicityRef = createRefFor("boxPublicityRef")
        val topTitleGuide = createGuidelineFromTop(0.1f)

        constrain(textFreeRef){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(textTitleRef){
            top.linkTo(topTitleGuide)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
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
        constrain(boxPublicityRef){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

    }
}

@Composable
fun HorseGameScreen(horseGameViewModel: HorseGameViewModel) {

    val isAppPremium:Boolean by horseGameViewModel.isAppPremium.observeAsState(false)

    val showAlertFree:Boolean by horseGameViewModel.showAlertFree.observeAsState(!isAppPremium)
    val showFinishedGame:Boolean by horseGameViewModel.showFinishedGame.observeAsState(false)

    val level:Int by horseGameViewModel.level.observeAsState(1)
    val moves:Int by horseGameViewModel.moves.observeAsState(60)
    val time:String by horseGameViewModel.time.observeAsState("00:00")
    val lives:Int by horseGameViewModel.lives.observeAsState(5)
    val options:Int by horseGameViewModel.options.observeAsState(0)

    val table by horseGameViewModel.table


//    BoxWithConstraints {
        val constraints = homeConstraints()
        ConstraintLayout(constraints){
            AlertFree(Modifier.layoutId("textFreeRef")){}
            Title(Modifier.layoutId("textTitleRef"))
            Level(modifier = Modifier.layoutId("cardLevelRef"), level = 0)
            Moves(modifier = Modifier.layoutId("cardMovesRef"), moves = 0)
            Time(modifier = Modifier.layoutId("cardTimeRef"), time = "00:00")
            Lives(modifier = Modifier.layoutId("cardLivesRef"), lives = 5)
            Options(modifier = Modifier.layoutId("cardOptionsRef"), options = 0)
            Publicidad(modifier = Modifier.layoutId("boxPublicityRef"), show = true)
        }
//    }


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
    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error),
        text = "Tap here to remove ads, get more levels and unlimited lives",
        fontFamily = amaranthFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.errorContainer
    )


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

//@Composable
//fun Body(
//    modifier: Modifier,
//    level: Int,
//    moves: Int,
//    time: String,
//    lives: Int,
//    options: Int,
//    table: List<List<ItemModel>>,
//    showFinishedGame: Boolean,
//    isAppPremium: Boolean
//) {
//    val scrollState = rememberScrollState()
//    Column(modifier = modifier.verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally){
//        Title(Modifier.layoutId("textTitleRef"))
//        Spacer(modifier = Modifier.size(30.dp))
//
//        Level(level)
//        Spacer(modifier = Modifier.size(10.dp))
//
//        Row(modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 32.dp)){
//            Moves(moves, Modifier.weight(1f))
//            Time(time, Modifier.weight(1f))
//            Lives(Modifier.weight(1f), lives, isAppPremium)
//            Options(options, Modifier.weight(1f))
//        }
//        Spacer(modifier = Modifier.size(30.dp))
//
//        Box(modifier = Modifier.fillMaxWidth()) {
//            Tablero(table, isAppPremium)
//            FinishedGame(Modifier.align(Alignment.Center),showFinishedGame)
//        }
//        Spacer(modifier = Modifier.size(15.dp))
//
//        Credits()
//    }
//
//}

@Composable
fun Title(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "Horse Challenge",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}
@Composable
fun Level(modifier: Modifier, level: Int) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .size(50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Level",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = level.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Moves(moves: Int, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Moves",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = moves.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Time(time: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Time",
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
fun Lives(modifier: Modifier, lives: Int, isAppPremium: Boolean = false) {
    //premiumColor()
    val cardColor = if (isAppPremium) {
        MaterialTheme.colorScheme.tertiary
    } else {
        MaterialTheme.colorScheme.error
    }

    Card(
        modifier = modifier
            .padding(4.dp)
            .size(50.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Lives",
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
fun Options(options: Int, modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Options",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        LinearProgressIndicator(progress = 0.5f)
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
fun Tablero(table: List<List<ItemModel>>, isAppPremium: Boolean) {
    Column {
        table.forEach { fila ->
            Row(modifier = Modifier.fillMaxWidth()) {
                fila.forEach { ite ->
                    ItemTablero(ite,isAppPremium)
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
fun FinishedGame(modifier: Modifier, showFinishedGame: Boolean) {
    if(showFinishedGame){
        Column(modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurfaceVariant)
            .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Level: 1",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Lives: 1",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                NextLevel()
                ShareGame()
            }
        }
    }
}
@Composable
fun NextLevel(){
    Button(
        modifier = Modifier
            .padding(20.dp),
        onClick = {

        }
    ) {
        Text(
            text = "Next Level",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.background
        )
    }
}
@Composable
fun ShareGame(){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ){
        Icon(modifier = Modifier.padding(10.dp),imageVector = Icons.Default.Share, contentDescription = "ic_Share", tint = MaterialTheme.colorScheme.background)
    }
}

@Composable
fun Credits(modifier: Modifier){
    Text(text = "Move the horse over board to complete all cells.", fontSize = 12.sp, fontFamily = amaranthFamily, fontWeight = FontWeight.Normal)
    Text(text = "Game created by Erich Ezequiel Schnell.", fontSize = 12.sp, fontFamily = amaranthFamily, fontWeight = FontWeight.Normal)
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
