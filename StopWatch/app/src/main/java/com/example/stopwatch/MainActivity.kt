package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.stopwatch.ui.theme.StopWatchTheme
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

class MainViewModel: ViewModel(){
    private var time = 0
    private var timerTask: Timer? = null

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    private val _sec = mutableStateOf(0)
    val sec: State<Int> = _sec

    private val _mill = mutableStateOf(0)
    val mill: State<Int> = _mill

    private val _labTimes = mutableStateOf(mutableListOf<String>())
    val labTimes: State<List<String>> = _labTimes

    private var lap = 1

    fun start(){
        _isRunning.value = true

        timerTask = kotlin.concurrent.timer(period = 10){
            time++
            _sec.value = time/100
            _mill.value = time % 100
        }

    }

    fun pause(){
        _isRunning.value = false

        timerTask?.cancel()

    }

    fun reset(){
        timerTask?.cancel()

        time = 0
        _isRunning.value = false
        _sec.value = 0
        _mill.value = 0

        _labTimes.value.clear()
        lap = 1
    }

    fun recordLabTime(){
        _labTimes.value.add(0, "$lap LAP : ${sec.value}.${mill.value}")
        lap++
    }

}

@Composable
fun MainScreen(
    sec:Int,
    mill:Int,
    isRunning: Boolean,
    lapTimes: List<String>,
    onReset: () -> Unit,
){

}
