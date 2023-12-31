package com.example.horsechallenge.horseGame.ui

import android.os.Build
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import com.example.horsechallenge.R
import com.example.horsechallenge.horseGame.ui.model.SquareModel
import com.example.horsechallenge.model.DimensionsScreen
import com.example.horsechallenge.model.Routes
import com.example.horsechallenge.ui.theme.amaranthFamily

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HorseGameScreen(horseGameViewModel: HorseGameViewModel, navigationController: NavHostController) {

    val context = LocalContext.current
    val horseUiState by horseGameViewModel.uiState.collectAsState()
    val homeConstraints = ConstraintSet{
        val textFreeRef = createRefFor("textFreeRef")
        val constraintRef = createRefFor("constraintRef")
        val boxPublicityRef = createRefFor("boxPublicityRef")


        constrain(textFreeRef){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(constraintRef){
            top.linkTo(textFreeRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(boxPublicityRef.top)
        }

        constrain(boxPublicityRef){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
    }
    val bodyConstraints = ConstraintSet{
        val textTitleRef = createRefFor("textTitleRef")
        val cardLevelRef = createRefFor("cardLevelRef")
        val cardMovesRef = createRefFor("cardMovesRef")
        val cardTimeRef = createRefFor("cardTimeRef")
        val cardLivesRef = createRefFor("cardLivesRef")
        val cardOptionsRef = createRefFor("cardOptionsRef")
        val tableRef= createRefFor("tableRef")
        val finishedGameRef= createRefFor("finishedGameRef")
        val creditsRef= createRefFor("creditsRef")
        val topTitleGuide = createGuidelineFromTop(0.08f)

        val box1Ref= createRefFor("box1Ref")
        val box2Ref= createRefFor("box2Ref")
        val box3Ref= createRefFor("box3Ref")
        val box4Ref= createRefFor("box4Ref")
        val box5Ref= createRefFor("box5Ref")
        val box6Ref= createRefFor("box6Ref")
        val box7Ref= createRefFor("box7Ref")
        val box8Ref= createRefFor("box8Ref")
        val box9Ref= createRefFor("box9Ref")

        createHorizontalChain(cardMovesRef,cardTimeRef,cardLivesRef,cardOptionsRef, chainStyle = ChainStyle.Spread)

        constrain(textTitleRef){
            top.linkTo(topTitleGuide,margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(cardLevelRef.top)
        }

        constrain(cardLevelRef){
            top.linkTo(textTitleRef.bottom, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(cardMovesRef.top)
        }

        constrain(cardMovesRef){
            top.linkTo(cardLevelRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(cardTimeRef.start)
            bottom.linkTo(tableRef.top)
        }
        constrain(cardTimeRef){
            top.linkTo(cardLevelRef.bottom)
            start.linkTo(cardMovesRef.end)
            end.linkTo(cardLivesRef.start)
            bottom.linkTo(tableRef.top)
        }
        constrain(cardLivesRef){
            top.linkTo(cardLevelRef.bottom)
            start.linkTo(cardTimeRef.end)
            end.linkTo(cardOptionsRef.start)
            bottom.linkTo(tableRef.top)
        }
        constrain(cardOptionsRef){
            top.linkTo(cardLevelRef.bottom)
            start.linkTo(cardLivesRef.end)
            end.linkTo(parent.end)
            bottom.linkTo(tableRef.top)
        }


        constrain(tableRef){
            top.linkTo(cardOptionsRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(creditsRef.top)
        }
        constrain(finishedGameRef){
            top.linkTo(tableRef.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(tableRef.bottom)
        }

        constrain(creditsRef){
            top.linkTo(tableRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

//        constrain(box1Ref){ top.linkTo(creditsRef.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box2Ref){ top.linkTo(box1Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box3Ref){ top.linkTo(box2Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box4Ref){ top.linkTo(box3Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box5Ref){ top.linkTo(box4Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box6Ref){ top.linkTo(box5Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box7Ref){ top.linkTo(box6Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box8Ref){ top.linkTo(box7Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
//        constrain(box9Ref){ top.linkTo(box8Ref.bottom) ; start.linkTo(parent.start) ; end.linkTo(parent.end) }
    }

    ConstraintLayout(constraintSet = homeConstraints, modifier = Modifier.fillMaxSize()){
        ConstraintLayout(constraintSet = bodyConstraints, modifier = Modifier
            .verticalScroll(rememberScrollState())
            .layoutId("constraintRef")){
            Title(Modifier.layoutId("textTitleRef"))

            Level(modifier = Modifier.layoutId("cardLevelRef"),
                level = horseUiState.level
            ){ if(horseUiState.isPremium) navigationController.navigate(Routes.SelectLvl.route) }

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
            /*Box (
                Modifier
                    .layoutId("box1Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Yellow)){}
            Box (
                Modifier
                    .layoutId("box2Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Red)){}
            Box (
                Modifier
                    .layoutId("box3Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Black)){}
            Box (
                Modifier
                    .layoutId("box4Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Gray)){}
            Box (
                Modifier
                    .layoutId("box5Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Green)){}
            Box (
                Modifier
                    .layoutId("box6Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Blue)){}
            Box (
                Modifier
                    .layoutId("box7Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Yellow)){}
            Box (
                Modifier
                    .layoutId("box8Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Red)){}
            Box (
                Modifier
                    .layoutId("box9Ref")
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Magenta)){}*/
        }

        AlertFree(Modifier.layoutId("textFreeRef")){
            navigationController.navigate(Routes.PayPremium.route)
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
fun Level(modifier: Modifier, level: Int, onClicked:()->Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            .size(60.dp)
            .clickable { onClicked() },
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
fun Lives(modifier: Modifier, lives: String, isPremium: Boolean = false) {
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
            text = lives,
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
    board: List<List<SquareModel>>,
    onClickItem:(SquareModel) -> Unit
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
    squareModel: SquareModel,
    onClickItem: (SquareModel) -> Unit
){
    val (width, _) = DimensionsScreen.getScreenDimensions(LocalContext.current)
    val sizeBox = width/8

    Box(
        modifier = Modifier
            .width(sizeBox.dp)
            .height(sizeBox.dp)
            .background(squareModel.background)
            .clickable {
                onClickItem(squareModel)
            }
    ){
        if(squareModel.boxState == SELECCIONADO){
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.iv_horse),
                contentDescription = "im_horse"
            )
        }
        if(squareModel.boxState == BONUS){
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.iv_mangekyou_bonus),
                contentDescription = "im_bonus"
            )
        }
        if(squareModel.hability){
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

@Composable
fun Publicidad(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .size(140.dp)
            .background(MaterialTheme.colorScheme.error)
    )
}
