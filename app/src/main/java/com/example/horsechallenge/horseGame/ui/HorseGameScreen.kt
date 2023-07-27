package com.example.horsechallenge.horseGame.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HorseGameScreen() {//horseGameViewModel: HorseGameViewModel
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
            .background(Color.Red)
            .padding(vertical = 8.dp)
            .clickable {
                //TODO
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Tap here to remove ads, get more levels and unlimited lives",
            color = Color.White
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
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )
        CardInformativa(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .size(80.dp),
            title = "Level", CardColor = Color.Black, dato = "1"
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)){
            CardInformativa(modifier = Modifier.weight(1f), title = "Moves", CardColor = Color(0xFFBB755F), dato = "63")
            CardInformativa(modifier = Modifier.weight(1f), title = "Time", CardColor = Color(0xFFBB755F), dato = "00:12")
            CardInformativa(modifier = Modifier.weight(1f), title = "Lives", CardColor = Color(0xFFD60404), dato = "1")
            CardInformativa(modifier = Modifier.weight(1f), title = "Options", CardColor = Color(0xFFBB755F), dato = "6")
        }
        Tablero()
    }

}
@Composable
fun CardInformativa(modifier: Modifier = Modifier,title: String, CardColor: Color, dato: String) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .size(50.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = title,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally),
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
    Box{
        Column(
            Modifier
                .fillMaxWidth()
                .size(100.dp)) {

            Row(Modifier.weight(8f)) {
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.LightGray))
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.DarkGray))
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.LightGray))
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.DarkGray))
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.LightGray))
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.DarkGray))
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.LightGray))
                CuadradoDelTablero(
                    Modifier
                        .weight(1f)
                        .size(40.dp)
                        .background(Color.DarkGray))
            }
        }
//        Column(Modifier.fillMaxWidth().size(50.dp).background(Color.Yellow).align(Alignment.Center)) {
//
//        }
    }
    Text(text = "Move the horse over board to complete all cells.", fontSize = 12.sp)
    Text(text = "Game created by Jose Javier Villena.", fontSize = 12.sp)
}
@Composable
fun CuadradoDelTablero(modifier: Modifier) {
    Box(
        modifier = modifier
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
