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


@Composable
fun ShowAllNurses(navController: NavController, viewModel: AppViewModel){
//
//getAll
    val viewModel:AppViewModel = viewModel
    var nurses:ArrayList<Nurse> = viewModel.uiState.collectAsState().value.nurses

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "List of all Nurses:", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.75f) // Ajusta el tamaño de la lista al 50% de la pantalla

        ){
            items(nurses){ nurse ->
                NurseItem(nurse = nurse)

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("FindByName") }) {
            Text("Find Nurse by Name")
        }
        Button(onClick = {
            viewModel.logout()
            navController.navigate("Home") }) {
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
            contentDescription = "profileImage1")


    }
}