package com.example.basic_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basic_navigation.ui.theme.Basic_NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            //화면 표시할 내용, startDestination : 맨 처음 화면
            NavHost(navController = navController, startDestination = "first"){
                composable("first"){
                    FirstScreen(navController)
                }
                composable("second"){
                    SecondScreen(navController)
                }
                //backStackEntry를 통해 composable로 넘어오는 value 받을 수 있음
                composable("third/{value}"){ backStackEntry ->
                    ThirdScreen(
                        navController,
                        backStackEntry.arguments?.getString("value") ?: "" //argument로 번들을 얻음 -> 번들이 null이 아니면 getString으로 value 값 가져오기
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController){
    val (value, setValue) = remember{
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "첫화면")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("second")
        }) {
            Text("두 번째!")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = value, onValueChange = setValue)
        Button(onClick = {
            if(value.isNotEmpty()){
                navController.navigate("third/$value")
            }
        }) {
            Text("세 번째!")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "두번째 화면")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigateUp()
            //navController.popBackStack()
        }) {
            Text("뒤로 가기!")
        }
    }
}

@Composable
fun ThirdScreen(navController: NavController, value: String){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "세번째 화면")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = value)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigateUp()
        }) {
            Text("뒤로 가기!")
        }
    }
}