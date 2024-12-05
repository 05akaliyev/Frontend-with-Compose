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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.nurses
//Login
//
@Composable
fun LoginPageForm(navController: NavController){

    var loginInput by remember{
        mutableStateOf<String>(value = "")
    }

    var loginResult by remember {
        mutableStateOf<Boolean?>(null)
    }

    var passwordInput by remember {
        mutableStateOf<String>(value = "")
    }

    var whatShow by remember {

        mutableStateOf<String>(value = "Result")
    }

    var passwordIsVisible by remember{
        mutableStateOf<Boolean>(value = false)
    }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginUserInput(
            loginInput = loginInput,
            onLoginChange = { loginInput = it }

        )

        LoginPasswordInput(

            passwordInput = passwordInput,
            onPasswordChange = {passwordInput = it},
            passwordIsVisible = passwordIsVisible,
            whatShow = whatShow


        )

        Button(onClick = {

            loginResult = nurses.any{it.user == loginInput && it.password == passwordInput}

        }, modifier = Modifier, colors = ButtonDefaults.buttonColors(

            containerColor = Color.Blue
        )) {

            Text(text = "Login")

        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("Home") }) {
            Text("Home")
        }



        if (loginResult != null){
            ResultView(success = loginResult == true)
        }
    }

}


@Composable
fun LoginUserInput(
    loginInput: String,
    onLoginChange: (String) -> Unit, // Lambda para manejar el cambio
    labelId : String = "User"
)
{

    Text(text = "Login", fontSize = 28.sp, fontWeight = FontWeight.Bold)

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = loginInput, onValueChange = {
        onLoginChange(it)
    }, label = {

        Text(text = labelId)
    })
    Spacer(modifier = Modifier.height(16.dp))



}

@Composable
fun LoginPasswordInput(
    passwordInput: String,
    onPasswordChange: (String) -> Unit, // Lambda para manejar el cambio
    labelId: String = "Password",
    whatShow: String,
    passwordIsVisible: Boolean
){

    var visualTransformation = if(passwordIsVisible){
        VisualTransformation.None
    } else {

        PasswordVisualTransformation()

    }

    OutlinedTextField(
        value = passwordInput,
        onValueChange = {
            onPasswordChange(it)

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

@Composable
fun ResultView(success : Boolean) {


    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(success){
            Text(text = "True")
        }else{
            Text(text = "False")
        }
    }
}
