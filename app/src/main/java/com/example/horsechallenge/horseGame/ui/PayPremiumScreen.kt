package com.example.horsechallenge.horseGame.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.horsechallenge.R
import com.example.horsechallenge.model.Routes
import com.example.horsechallenge.ui.theme.md_theme_background_payPremium

@Composable
fun PayPremiumScreen(horseGameViewModel: HorseGameViewModel, navigationController: NavHostController) {
    val constraints = horseGameViewModel.paySceenConstraints()

    ConstraintLayout(
        constraintSet = constraints, modifier = Modifier
            .fillMaxSize()
            .background(md_theme_background_payPremium)
    ) {
        PayTitle(Modifier.layoutId("titleRef"))

        NoMoreAds(Modifier.layoutId("noAdsRef"))
        UnlimitedLives(Modifier.layoutId("unlimitedLivesRef"))
        KeepLevel(Modifier.layoutId("keepLvlRef"))

        PayButton(Modifier.layoutId("payRef")){
            horseGameViewModel.togglePremium()
            navigationController.popBackStack()
        }

    }
}

@Composable
fun PayTitle(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.pay_title),
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun NoMoreAds(modifier: Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.iv_no_more_ads),
            contentDescription = "noMoreAds"
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.no_more_ads),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun UnlimitedLives(modifier: Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.iv_unlimited_lives),
            contentDescription = "noMoreAds"
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.unlimited_lives),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
@Composable
fun KeepLevel(modifier: Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.iv_keep_level),
            contentDescription = "noMoreAds"
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = R.string.keep_level),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun PayButton(modifier: Modifier, isclickedPay:() -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        onClick = {
            isclickedPay()
        }
    ) {
        Text(
            text = stringResource(id = R.string.pay_btn),
            style = MaterialTheme.typography.titleMedium
        )
    }
}