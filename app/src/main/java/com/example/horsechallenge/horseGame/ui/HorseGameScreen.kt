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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horsechallenge.R
import com.example.horsechallenge.horseGame.ui.model.ItemModel
import com.example.horsechallenge.ui.theme.amaranthFamily

@Composable
fun HorseGameScreen(horseGameViewModel: HorseGameViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
        AdvertenciaFree()
        Body(Modifier.weight(1f))
        Publicidad()

    }
}

@Composable
fun AdvertenciaFree() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.error)
            .padding(vertical = 8.dp)
            .clickable {

            }, horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Tap here to remove ads, get more levels and unlimited lives",
            fontFamily = amaranthFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.errorContainer
        )
    }
}

@Preview
@Composable
fun Body(modifier: Modifier) {//modifier: Modifier
    val scrollState = rememberScrollState()
    Column(modifier = modifier.verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally){
        Title()
        Spacer(modifier = Modifier.size(30.dp))

        Level()
        Spacer(modifier = Modifier.size(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)){
            Moves(Modifier.weight(1f))
            Time(Modifier.weight(1f))
            Lives(Modifier.weight(1f))
            Options(Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.size(30.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Tablero()
            FinishedGame(Modifier.align(Alignment.Center))
        }
        Spacer(modifier = Modifier.size(15.dp))

        Credits()
    }

}
@Composable
fun Title() {
    Text(
        modifier = Modifier.padding(top = 34.dp),
        text = "Horse Challenge",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary
    )
}
@Composable
fun Level() {
    Card(
        modifier = Modifier
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
            text = "1",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Moves(modifier: Modifier) {
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
            text = "00",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Time(modifier: Modifier) {
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
            text = "00:00",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Lives(modifier: Modifier) {
    val state = false
    val cardColor = if(state){
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
            text = "0",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
@Composable
fun Options(modifier: Modifier) {
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
            text = "0",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Tablero() {
    val table: MutableList<MutableList<ItemModel>> = mutableListOf()

    for (i in 0 until 8){
        val newRow: MutableList<ItemModel> = mutableListOf()
        for (j in 0 until 8){
            newRow.add(ItemModel(x = i, y = j))
        }
        table.add(newRow)
    }

    Column() {
        table.forEach { fila ->
            Row(modifier = Modifier.fillMaxWidth()) {
                fila.forEach { ite ->
                    ItemTablero(ite)
                }
            }
        }
    }
}
@Composable
fun ItemTablero(itemModel: ItemModel){
    val (width, _) = getScreenDimensions(LocalContext.current)
    val sizeBox = width/8

    val background = if ((itemModel.x+itemModel.y)%2 == 0){
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.secondary
    }

    Box(
        modifier = Modifier
            .width(sizeBox.dp)
            .height(sizeBox.dp)
            .background(background)
            .clickable {
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
fun FinishedGame(modifier: Modifier) {
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
fun Credits(){
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
fun Publicidad() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(170.dp)
            .background(Color.Green)
    ){

    }
}
