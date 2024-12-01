package com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev

import LoginPageForm
import ShowAllNurses
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui.theme.FrontendKotlinCompose_MatiasMarcelo_AdiKaliyevTheme
import findNurses

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

// Lista estática de enfermeras
class Nurse(var user:String, var password:String)

val nurses = ArrayList<Nurse>().apply {
    add(Nurse("Alberto", "password1"))
    add(Nurse("Maria", "password2"))
    add(Nurse("Juan", "password3"))
}

@Composable
fun InitialPage(){
    var loginClicked by remember {
        mutableStateOf<Boolean>(false) }
    var getAllClicked by remember {
        mutableStateOf<Boolean>(false) }
    var findByNameClicked by remember {
        mutableStateOf<Boolean>(false) }
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!loginClicked && !getAllClicked && !findByNameClicked) {
            ButtonLoginPage(onLoginClick = { loginClicked = true })
            ButtonGetAllPage(onGetAllClick = { getAllClicked = true })
            ButtonFindByNamePage(onFindByNameClick = { findByNameClicked = true })
        }
        if (loginClicked) {
            LoginPageForm()
        }
        if (getAllClicked) {
            ShowAllNurses()
        }
        if (findByNameClicked) {
            findNurses()
        }
    }
}
@Composable
fun ButtonFindByNamePage(onFindByNameClick: () -> Unit) {
    Button(onClick = {onFindByNameClick()}) {
        Text("FindByName")
    }
}

@Composable
fun ButtonGetAllPage(onGetAllClick: () -> Unit) {
    Button(onClick = { onGetAllClick()}) {
        Text("Get All")
    }
}

@Composable
fun ButtonLoginPage(onLoginClick: () -> Unit) {
    Button(onClick = { onLoginClick() }) {
        Text("Login")
    }


}
