package com.example.horsechallenge.horseGame.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import com.example.horsechallenge.R
import com.example.horsechallenge.ui.theme.md_theme_background_box_select_lvl
import com.example.horsechallenge.ui.theme.md_theme_box_select_lvl

@Composable
fun SelectLevelScreen(horseGameViewModel: HorseGameViewModel, navigationController: NavHostController){
    val selectConstraitRef = SelectLvlConstraint()
    ConstraintLayout(constraintSet = selectConstraitRef, modifier = Modifier.fillMaxSize()){
        SelectLvlTitle(Modifier.layoutId("titleRef"))
        Levels(Modifier.layoutId("bodyRef")){ lvl ->
            lvl
        }
        BtnReturn(Modifier.layoutId("returnRef")){
            navigationController.popBackStack()
        }
    }
}
@Composable
fun SelectLvlConstraint(): ConstraintSet {
    return ConstraintSet{

        val titleRef = createRefFor("titleRef")
        val bodyRef = createRefFor("bodyRef")
        val returnRef = createRefFor("returnRef")


        constrain(titleRef){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(bodyRef.top)
        }
        constrain(bodyRef){
            top.linkTo(titleRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(returnRef.top)
        }
        constrain(returnRef){
            top.linkTo(bodyRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
        createVerticalChain(titleRef, bodyRef, returnRef, chainStyle = ChainStyle.Spread)
    }
}

@Composable
fun SelectLvlTitle(modifier: Modifier){
    Text(
        text = stringResource(id = R.string.select_lvl_title),
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        fontSize = 54.sp
    )
}
@Composable
fun Levels(modifier: Modifier, onClicked:(Int)->Unit){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp),
        colors = CardDefaults.cardColors( containerColor = md_theme_background_box_select_lvl ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Column (
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            var cont = 1
            for (i in 0 .. 1){
//                Spacer(modifier = Modifier.weight(.25f))
                Row(modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)){

                    for (i in 0 .. 4){
                        Card (
                            modifier = Modifier
                                .weight(0.2f)
                                .fillMaxHeight()
                                .padding(8.dp)
                                .clickable { onClicked(cont) },
                            colors = CardDefaults.cardColors( containerColor = md_theme_box_select_lvl ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ){
                            Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                Text(
                                    text = "$cont",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Black
                                )
                            }
                        }
                        cont++
                    }
                }
            }
//            Spacer(modifier = Modifier.weight(.25f))
        }
    }
}

@Composable
fun  BtnReturn(modifier: Modifier, onClicked:()->Unit){
    Button(
        modifier = modifier,
        onClick = { onClicked() },
        colors =  ButtonDefaults.buttonColors(containerColor = md_theme_background_box_select_lvl)
    ) {
        Text(
            text = stringResource(id = R.string.select_lvl_return),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
    }
}