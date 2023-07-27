package com.example.horsechallenge.horseGame.ui

import android.widget.Space
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
import com.example.horsechallenge.horseGame.ui.model.boxTeblero

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
            text = "Horse Challenge",
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
        Spacer(modifier = Modifier.size(16.dp))
        Tablero()
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Move the horse over board to complete all cells.", fontSize = 12.sp)
        Text(text = "Game created by Erich Ezequiel Schnell.", fontSize = 12.sp)
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
        ) {
            var colorAux:Boolean = false
            for(i in 0..7){
                colorAux = !colorAux
                Row{
                    for(j in 0..7){
                        if (colorAux){
                            CuadradoDelTablero(
                                Modifier
                                    .weight(1f)
                                    .size(50.dp)
                                    .background(Color.LightGray))
                        } else {
                            CuadradoDelTablero(
                                Modifier
                                    .weight(1f)
                                    .size(50.dp)
                                    .background(Color.DarkGray))
                        }
                        colorAux = !colorAux
                    }
                }
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color(0xB0FFFFFF))
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "LEVEL: 1", fontSize = 64.sp)
            Spacer(modifier = Modifier.size(12.dp))
            Text(text = "Lives: 1", fontSize = 32.sp)
        }
    }
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
