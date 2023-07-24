package com.example.horsechallenge.horseGame.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HorseGameScreen() {//horseGameViewModel: HorseGameViewModel
    Box(modifier = Modifier.fillMaxSize()){
        Encabezado()
    }
}


@Composable
fun Encabezado() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Horse Chanllege",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Gray,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )
        CardInformativa(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            title = "Level",
            CardColor = Color.Black,
            dato = "1"
        )
        Row{
            CardInformativa(
                modifier = Modifier.padding(4.dp),
                title = "Moves",
                CardColor = Color.Gray,
                dato = "63"
            )
            CardInformativa(
                modifier = Modifier.padding(4.dp),
                title = "Time",
                CardColor = Color.Gray,
                dato = "00:12"
            )
            CardInformativa(
                modifier = Modifier.padding(4.dp),
                title = "Lives",
                CardColor = Color.Red,
                dato = "1"
            )
            CardInformativa(
                modifier = Modifier.padding(4.dp),
                title = "Options",
                CardColor = Color.Gray,
                dato = "6"
            )
        }

    }
}

@Composable
fun CardInformativa(modifier: Modifier, title: String, CardColor: Color, dato: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.White
        )
        Text(
            text = dato,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.White
        )
    }
}

@Composable
fun Tablero() {
    TODO("Not yet implemented")
}

@Composable
fun Publicidad() {
    TODO("Not yet implemented")
}
