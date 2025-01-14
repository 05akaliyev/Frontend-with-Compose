import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.AppViewModel
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.Nurse

@Composable
fun findNurses(navController: NavController, viewModel: AppViewModel) {
    val nurses = viewModel.uiState.collectAsState().value.nurses
    var searchQuery by remember { mutableStateOf("") }
    var filteredNurses by remember { mutableStateOf(listOf<Nurse>()) }

    // Update filteist dynamically
    LaunchedEffect(searchQuery) {
        filteredNurses = if (searchQuery.isNotBlank()) {
            nurses.filter { it.user.contains(searchQuery, ignoreCase = true) }
        } else emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search Nurses by Name", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter Nurse's Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Display Results
        if (filteredNurses.isEmpty() && searchQuery.isNotBlank()) {
            Text("No results found.", fontSize = 16.sp)
        } else {
            LazyColumn(modifier = Modifier.fillMaxHeight(0.7f)) {
                items(filteredNurses) { nurse ->
                    Text(
                        text = "Nurse: ${nurse.user}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Buttons
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { navController.navigate("GetAll") }) {
                Text("List All Nurses")
            }
            Button(onClick = {
                viewModel.logout()
                navController.navigate("Home") }) {
                Text("Home")
            }
        }
    }
}
