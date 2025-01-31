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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.AppViewModel
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.Nurse
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.R
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.RemoteMessageUiState


@Composable
fun ShowAllNurses(navController: NavController, viewModel: AppViewModel){
//
//getAll

    LaunchedEffect(Unit) {
        viewModel.fetchAllNurses()
    }
    val viewModel:AppViewModel = viewModel

    // Obtener el estado del viewModel
  //  val uiState = viewModel.uiState.collectAsState().value
   // var nurses = viewModel.uiState.collectAsState().value.nurses
    val remoteMessageUiState = viewModel.remoteMessageUiState
    // Determinar qué mostrar dependiendo del estado de carga
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "List of all Nurses:", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        when (remoteMessageUiState) {
            is RemoteMessageUiState.Cargant -> {
                // Mostrar mensaje de carga mientras obtenemos los datos
                Text(text = "Cargando...", fontSize = 18.sp)
            }
            is RemoteMessageUiState.Success -> {
                // Mostrar la lista de enfermeras si los datos se han cargado correctamente
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.75f) // Ajusta el tamaño de la lista
                ) {
                    items(remoteMessageUiState.remoteMessage.nurses) { nurse ->
                        NurseItem(nurse = nurse)
                    }
                }
            }
            is RemoteMessageUiState.Error -> {
                // Mostrar mensaje de error si ocurre
                Text(text = "Users loading...", fontSize = 18.sp, color = androidx.compose.ui.graphics.Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("FindByName") }) {
            Text("Find Nurse by Name")
        }

        // NEW: Profile button
        Button(onClick = {
            navController.navigate("ProfileInfo")
        }) {
            Text("User Profile")
        }

        Button(onClick = {
            viewModel.logout()
            navController.navigate("Home")
        }) {
            Text("Home")
        }
    }
}

@Composable
fun NurseItem(nurse: Nurse) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nurse: ${nurse.user}", fontSize = 18.sp)
        Image(painter = painterResource(id = R.drawable.profileimage),
            contentDescription = "Profile Image")
    }
}