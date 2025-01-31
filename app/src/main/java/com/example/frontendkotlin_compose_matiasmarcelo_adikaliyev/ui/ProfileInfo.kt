// ProfileInfo.kt
package com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.AppViewModel
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.Nurse
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.R
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.RemoteMessageUiState

@Composable
fun ProfileInfo(navController: NavController, viewModel: AppViewModel, id: Int) {

    // Check the current remoteMessageUiState to find the logged-in nurse

    // If the user is logged in successfully, we expect exactly one nurse in the state
//    val nurse = (remoteMessageUiState as? RemoteMessageUiState.Success)
//        ?.remoteMessage
//        ?.nurses
//        ?.firstOrNull()  // or get(0) if you're sure there's always one

    LaunchedEffect(Unit) {
        viewModel.getRemoteMessage(id)
    }

    val remoteMessageUiState = viewModel.remoteMessageUiState


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        when (remoteMessageUiState) {
            is RemoteMessageUiState.Cargant -> {
                // Mostrar mensaje de carga mientras obtenemos los datos
                Text(text = "Cargando...", fontSize = 18.sp)
            }
            is RemoteMessageUiState.Success -> {
                // Como en el login o register se devuelve un solo Nurse, hay 1 en la lista
                val nurse = remoteMessageUiState.remoteMessage.nurses.firstOrNull()
                if (nurse != null) {
                    NurseItem(nurse)
                } else {
                    Text(text = "No Nurse data found.")
                }
            }
            is RemoteMessageUiState.Error -> {
                // Mostrar mensaje de error si ocurre
                Text(text = "Users loading...", fontSize = 18.sp, color = androidx.compose.ui.graphics.Color.Red)
            }
        }

//        if (nurse != null) {
//            Text(text = "User Profile", fontSize = 28.sp)
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text(text = "ID: ${nurse.id}")
//            Text(text = "User: ${nurse.user}")
//            Text(text = "Password: ${nurse.password}")
//            Text(text = "First Name: ${nurse.first_name}")
//            Text(text = "Last Name: ${nurse.last_name}")
//            Text(text = "Phone Number: ${nurse.phone_number}")
//
//        } else {
//            Text(text = "No Nurse data found or not logged in.")
//        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.navigate("Home")
            viewModel.logout()
        }) {
            Text("Back to Home")
        }
    }
}

@Composable
fun NurseItem(nurse: Nurse) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User Profile", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "ID: ${nurse.id}")
        Text(text = "User: ${nurse.user}")
        Text(text = "Password: ${nurse.password}")
        Text(text = "First Name: ${nurse.first_name}")
        Text(text = "Last Name: ${nurse.last_name}")
        Text(text = "Phone Number: ${nurse.phone_number}")
        Image(painter = painterResource(id = R.drawable.profileimage),
            contentDescription = "Profile Image")
    }
}