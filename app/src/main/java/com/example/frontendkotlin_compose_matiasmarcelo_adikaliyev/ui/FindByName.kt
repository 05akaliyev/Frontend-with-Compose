import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.AppViewModel
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.Nurse

//Find Nurse by Name
//
@Composable
fun findNurses(navController: NavController, viewModel: AppViewModel) {
    val viewModel:AppViewModel = viewModel
    var nurses:ArrayList<Nurse> = viewModel.uiState.collectAsState().value.nurses
    var searchQuery by remember { mutableStateOf("") }
    val filteredNurses = nurses.filter { it.user.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Find Nurse by Name", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn{
            items(filteredNurses.take(3)) { nurse ->
                Text(text = nurse.user, fontSize = 18.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("GetAll") }) {
            Text("List All Nurses")
        }
        Button(onClick = { navController.navigate("Home") }) {
            Text("Home")
        }

    }

}
