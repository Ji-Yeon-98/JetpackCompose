package com.example.basic_viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.basic_viewmodel.ui.theme.Basic_ViewModelTheme

class MainActivity : ComponentActivity() {

//    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //remember : 기존에 있던 데이터 기억
//            val data = remember{
//                mutableStateOf("Hello")
//            }

            //상태가 변경이 되면 Compose가 다시 빌드, Composition
            //val data = mutableStateOf("Hello")

            val viewModel = viewModel<MainViewModel>()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(viewModel.data.value, fontSize = 30.sp)
                Button(onClick = {
                    viewModel.changeValue()
                }) {
                    Text("변경")
                }
            }
        }
    }
}

//액티비티와 라이프사이클 동일하게 가져가는 습성, remember 가져가지 않아도 된다.
class MainViewModel : ViewModel(){

    //보통 데이터 외부 공개 X, 접근 안되도록 막음
    private val _data = mutableStateOf("Hello")
    //읽기 전용, main에서 수정할 수 없음
    val data: State<String> = _data

    fun changeValue(){
        _data.value = "World"
    }

}