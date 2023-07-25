package com.example.horsechallenge.horseGame.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.clipScrollableContainer
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.horsechallenge.util.*

val color = color_horse()
val fuente = fontAmaranth()

@Preview
@Composable
fun HorseGameScreen() {//horseGameViewModel: HorseGameViewModel
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color.app_color_light)
    ){
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
            .background(color.contrast_data)
            .padding(vertical = 8.dp)
            .clickable {
                //TODO
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Tap here to remove ads, get more levels and unlimited lives",
            color = color.app_color_light,
            fontFamily = fuente.amaranthFamily,
            fontWeight = FontWeight.Bold,
        )
    }
}
@Composable
fun Body(modifier: Modifier) {
    Column(
        modifier = modifier
            .clipScrollableContainer(orientation = Orientation.Vertical),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Text(
            modifier = Modifier.padding(top = 22.dp),
            text = "Horse Chanllege",
            color = color.app_color_regular,
            fontFamily = fuente.amaranthFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )
        CardInformativa(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .size(80.dp),
            title = "Level", CardColor = Color.Black, dato = "1"
        )
        Row(Modifier.padding(horizontal = 16.dp)){
            CardInformativa(modifier = Modifier.weight(1f), title = "Moves", CardColor = color.app_color_regular, dato = "63")
            CardInformativa(modifier = Modifier.weight(1f), title = "Time", CardColor = color.app_color_regular, dato = "00:12")
            CardInformativa(modifier = Modifier.weight(1f), title = "Lives", CardColor = color.contrast_data, dato = "1")
            CardInformativa(modifier = Modifier.weight(1f), title = "Options", CardColor = color.app_color_regular, dato = "6")
        }
        Tablero()
    }

}
@Composable
fun CardInformativa(modifier: Modifier = Modifier,title: String, CardColor: Color, dato: String) {
    Card(
        modifier = modifier.padding(4.dp).size(50.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 4.dp).align(Alignment.CenterHorizontally),
            color = Color.White
        )
        Text(
            text = dato,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.White
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun Tablero() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(400.dp)
            .background(Color.Gray)
    ){

    }
}

@Composable
fun Publicidad() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp)
            .background(Color.Green)
    ){

    }
}
