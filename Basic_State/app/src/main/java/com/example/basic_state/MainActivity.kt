package com.example.basic_state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basic_state.ui.theme.Basic_StateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    //안에 있는 값을 변경, 한번 할당되고 나서 수정하지 않음 -> val
    val text1: MutableState<String> = remember {
        mutableStateOf("Hello World")
    }

    //세번째 text의 효과를 그대로 가져감
    //text2 자체가 변경 -> var
    //by는 setter와 getter를 재정의 해놓음
    var text2: String by remember {
        mutableStateOf("Hello World")
    }

    //text -> 수정 되지 않는 string
    //text : 안에 있는 값을 취함, setText : setter(데이터를 계속 넣어줌)에 있는 값을 꺼내놓고 사용
    var (text: String, setText: (String) -> Unit) = remember {
        mutableStateOf("Hello World")
    }

    //State로 변경
    val text3:State<String> = viewModel.liveData.observeAsState("Hello World")

    Column(){
        Text("Hello World")
        Button(onClick = {
            text1.value = "변경"
            println(text1.value)
            text2 = "변경"
            print(text2)
//            text = "변경"
            setText("변경")

            //변수 이름이 value, 읽기 전용이기때문에 변경 X -> viewModel안에 변경하는 함수 만들기
//            viewModel.value.value = "변경"
            viewModel.changeValue("변경")

        }) {
            Text("클릭")
        }
        TextField(value = text, onValueChange = setText)
    }

}

class MainViewModel: ViewModel(){
    //쓰기, 읽기 가능
    private val _value : MutableState<String> = mutableStateOf("Hello World")
    //읽기만 가능
    val value: State<String> = _value

    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    fun changeValue(value:String){
        _value.value = value
    }
}