package com.example.horsechallenge

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass.Companion.calculateFromSize
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.horsechallenge.horseGame.ui.HorseGameScreen
import com.example.horsechallenge.horseGame.ui.HorseGameViewModel
import com.example.horsechallenge.ui.theme.HorseChallengeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val mediaProjectionManager: MediaProjectionManager by lazy {
//        getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
//    }
//    private var mediaProjection: MediaProjection? = null

    private val horseGameViewModel:HorseGameViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HorseChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val windowSizeClass = calculateWindowSizeClass(activity = this)
//                    val wdowFromSize = calculateFromSize(DpSize(650.dp,910.dp))
//
//                    Column {
//                        Text(text = wdowFromSize.toString())
//                        Text(text = windowSizeClass.widthSizeClass.toString())
//                        Text(text = windowSizeClass.heightSizeClass.toString())
//                    }
                    
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                    HorseGameScreen(horseGameViewModel)
                }
            }
        }
    }
}