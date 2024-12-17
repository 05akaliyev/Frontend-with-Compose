package com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev

import LoginPageForm
import ShowAllNurses
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui.RegisterpageForm
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui.theme.FrontendKotlinCompose_MatiasMarcelo_AdiKaliyevTheme
import findNurses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//

// Lista estática de enfermeras
class Nurse(var user:String, var password:String)


data class InfoUiState(val nurses: ArrayList<Nurse> = ArrayList<Nurse>())

class AppViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(
        InfoUiState(
            nurses = arrayListOf(
                Nurse("Alberto", "password1"),
                Nurse("Maria", "password2"),
                Nurse("Juan", "password3")
            )
        )
    )
    val uiState:StateFlow<InfoUiState> get() = _uiState.asStateFlow()

    //Hacer addNurse metodo llamar en registro

//    fun getNurse():Nurse{
//        return _uiState.value.nurses
//    }
      fun addNurse(newNurse:Nurse){
          _uiState.value = _uiState.value.copy(
              nurses = _uiState.value.nurses.apply {
                  add(newNurse)
              }
          )
      }


}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrontendKotlinCompose_MatiasMarcelo_AdiKaliyevTheme {
                InitialPage()
            }
        }
    }
}

@Composable
fun InitialPage(){

    val viewModel : AppViewModel = AppViewModel()
    val navController = rememberNavController()


    //    var loginClicked by remember {
    //        mutableStateOf<Boolean>(false) }
    //    var getAllClicked by remember {
    //        mutableStateOf<Boolean>(false) }
    //    var findByNameClicked by remember {
    //        mutableStateOf<Boolean>(false) }

    //Cambiar Luego Por Pantalla Home (startDestination = Home)
    NavHost(navController = navController, startDestination = "Home"){
        composable("Register"){
            RegisterpageForm(navController, viewModel)
        }
        composable("Login") {
            LoginPageForm(navController,viewModel)
        }
        composable("GetAll") {
            ShowAllNurses(navController, viewModel)

        }
        composable("FindByName"){
            findNurses(navController,viewModel)

        }

        composable("Home") {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement  = Arrangement.Center
            ){


                Text(text = "Home", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Image(painter = painterResource(id = R.drawable.homepage),
                    contentDescription = "homePageImage",modifier = Modifier.size(130.dp))
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("Register") }) {
                    Text("Register")
                }
                Button(onClick = { navController.navigate("Login") }) {
                    Text("Login")
                }
//                Button(onClick = { navController.navigate("GetAll")}) {
//                    Text("Get All")
//                }
//                Button(onClick = { navController.navigate("FindByName")}) {
//                    Text("FindByName")
//                }
        }









//            if (!loginClicked && !getAllClicked && !findByNameClicked) {
//                ButtonLoginPage(onLoginClick = { loginClicked = true })
//                ButtonGetAllPage(onGetAllClick = { getAllClicked = true })
//                ButtonFindByNamePage(onFindByNameClick = { findByNameClicked = true })
//            }
//            if (loginClicked) {
//                LoginPageForm()
//            }
//            if (getAllClicked) {
//                ShowAllNurses()
//            }
//            if (findByNameClicked) {
//                findNurses()
//            }


        }

    }

}
//@Composable
//fun ButtonFindByNamePage(onFindByNameClick: () -> Unit) {
//
//}
//
//@Composable
//fun ButtonGetAllPage(onGetAllClick: () -> Unit) {
//
//}
//
//@Composable
//fun ButtonLoginPage(onLoginClick: () -> Unit) {
//
//
//
//}
