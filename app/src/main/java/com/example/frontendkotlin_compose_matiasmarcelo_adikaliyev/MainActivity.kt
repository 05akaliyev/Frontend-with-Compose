package com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui.theme.FrontendKotlinCompose_MatiasMarcelo_AdiKaliyevTheme

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

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonFindByNamePage()
        ButtonGetAllPage()
        ButtonLoginPage()


    }

}

@Composable
fun ButtonFindByNamePage() {

    Button(onClick = {}) {
        Text("Login")
    }

}

@Composable
fun ButtonGetAllPage() {

    Button(onClick = {}) {
        Text("Get All")
    }
}

@Composable
fun ButtonLoginPage() {

    Button(onClick = {}) {
        Text("Find By Name")
    }

}
