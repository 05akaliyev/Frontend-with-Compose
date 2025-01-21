package com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.AppViewModel
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.Nurse
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.R

@Composable
fun RegisterpageForm(navController: NavController, viewModel: AppViewModel){
    val viewModel: AppViewModel = viewModel
    var nurses:ArrayList<Nurse> = viewModel.uiState.collectAsState().value.nurses

    var registerUserInput by remember {
        mutableStateOf<String>(value = "")

    }
    var registerPasswordInput by remember {
        mutableStateOf<String>(value = "")

    }
    var passwordIsVisible by remember {
        mutableStateOf<Boolean>(value = false)
    }

    var registerResult by remember {
        mutableStateOf<Boolean?>(value = null)
    }


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        RegisterUserInput(
            registerUserInput = registerUserInput,
            onRegisterUserChange = {registerUserInput = it}

            )

        RegisterPasswordInput(
            registerPasswordInput = registerPasswordInput,
            onRegisterPasswordChange = {registerPasswordInput = it},
            passwordIsVisible = passwordIsVisible
        )

        Button(onClick = {
//Nurse no crea un id, se crea en la base de datos
            if (registerUserInput.isNotBlank() && registerPasswordInput.isNotBlank()) {
                val nurse = Nurse(
                    user = registerUserInput, password = registerPasswordInput

                )
                viewModel.addNurse(nurse)
                navController.navigate("GetAll")
                registerResult = true
            } else {
                registerResult = false
            }


            //registerResult = nurses.any{it.user == registerUserInput && it.password == registerPasswordInput}



        }, modifier = Modifier, colors = ButtonDefaults.buttonColors(

            containerColor = Color.Blue
        )) {

            Text(text = "Register")

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("Home") }) {
            Text("Home")
        }


    }



}

@Composable
fun RegisterUserInput(registerUserInput : String, labelId : String = "User",
    onRegisterUserChange: (String) -> Unit)
{
    Text(text = "Register", fontSize = 28.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(16.dp))
    Image(painter = painterResource(id = R.drawable.registroperfil),
        contentDescription = "registerProfile")
    Spacer(modifier = Modifier.height(16.dp))


    OutlinedTextField(value = registerUserInput, onValueChange = {
        onRegisterUserChange(it)
    }, label = {

        Text(text = labelId)

    })
}

@Composable
fun RegisterPasswordInput(registerPasswordInput : String, labelId : String = "Password",
    onRegisterPasswordChange: (String) -> Unit,
    passwordIsVisible : Boolean)
{

    var visualTransformation = if(passwordIsVisible){
        VisualTransformation.None
    } else {

        PasswordVisualTransformation()

    }

    OutlinedTextField(
        value = registerPasswordInput,
        onValueChange = {
            onRegisterPasswordChange(it)

        },
        label = {

            Text(text = "Password")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = visualTransformation
    )


    Spacer(modifier = Modifier.height(16.dp))

}