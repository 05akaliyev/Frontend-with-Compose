package com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev

import LoginPageForm
import ShowAllNurses
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui.ProfileInfo
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui.RegisterpageForm
import com.example.frontendkotlin_compose_matiasmarcelo_adikaliyev.ui.theme.FrontendKotlinCompose_MatiasMarcelo_AdiKaliyevTheme
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage
import findNurses
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

//

// Lista estática de enfermeras
class Nurse(var id:Int = 0, var user:String, var password:String, var first_name:String, var last_name:String, var phone_number:Int)

data class InfoUiState(val nurses: ArrayList<Nurse> = ArrayList<Nurse>())

var currentUserId: Int? = null
class AppViewModel: ViewModel(){
    private val _uiState = MutableStateFlow(
        InfoUiState(
//            nurses = arrayListOf(
//                Nurse(1,"Alberto", "password1"),
//                Nurse(2,"Maria", "password2"),
//                Nurse(3,"Juan", "password3")
//            )
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
    var remoteMessageUiState: RemoteMessageUiState
            by mutableStateOf(RemoteMessageUiState.Cargant)
    private set
    // Crea la conexión de Retrofit
    val connexio = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getRemoteMessage(id: Int){
        viewModelScope.launch {
            remoteMessageUiState=RemoteMessageUiState.Cargant
            try{


                // Crea la instancia de la interfaz RemoteMessageInterface
                val endPoint = connexio.create(RemoteMessageInterface ::class.java)
                //Hace llamada a la API
                val resposta = endPoint.getRemoteMessage(id) //Pasa id 1 como ejemplo
                // Aquí imprimes el resultado en el log
                Log.d("APIResponse", "Respuesta: ${resposta.user}")
                //Debido a que Success espera un infoUiState. Se envuelve resposta para que solo devuelva una lista con el nurse enviado (1)
                val infoState = InfoUiState(nurses = arrayListOf(resposta))

                remoteMessageUiState=RemoteMessageUiState.Success(infoState)
            } catch (e: Exception) {
                Log.d("exemple", "RESPOSTA ERROR ${e.message} ${e.printStackTrace()}")
                remoteMessageUiState= RemoteMessageUiState.Error
            }
        }
    }


    fun postRemoteMessage(user: String, password: String){
        viewModelScope.launch {
            remoteMessageUiState=RemoteMessageUiState.Cargant
            try{
                val endPoint = connexio.create(RemoteMessageInterface::class.java)
                val resposta = endPoint.postRemoteMessage(user, password)
                Log.d("exemple", "RESPOSTA ${resposta.id}")
                // GUARDAS el ID
                currentUserId = resposta.id
                val infoState = InfoUiState(nurses = arrayListOf(resposta))
                remoteMessageUiState=RemoteMessageUiState.Success(infoState)
            } catch (e: Exception) {
                Log.d("exemple", "RESPOSTA ERROR ${e.message} ${e.printStackTrace()}")
                remoteMessageUiState= RemoteMessageUiState.Error
            }
        }
    }

    fun postRemoteMessageRegister(user: String, password: String, phone_number: Int, first_name: String, last_name: String){

        viewModelScope.launch {
            remoteMessageUiState=RemoteMessageUiState.Cargant
            try{
                val endPoint = connexio.create(RemoteMessageInterface::class.java)
                val resposta = endPoint.postRemoteMessageRegister(user, password, phone_number, first_name, last_name)

                Log.d("exemple", "RESPOSTA ${resposta.id}")
                // GUARDAS el ID
                currentUserId = resposta.id
                val infoState = InfoUiState(nurses = arrayListOf(resposta))
                remoteMessageUiState=RemoteMessageUiState.Success(infoState)

            } catch (e: Exception) {
                Log.d("exemple", "RESPOSTA ERROR ${e.message} ${e.printStackTrace()}")
                remoteMessageUiState= RemoteMessageUiState.Error

            }
        }

    }

    fun fetchAllNurses() {
        viewModelScope.launch {
            try {
                val endPoint = connexio.create(RemoteMessageInterface::class.java)
                val response = endPoint.getAllNurses()
                val infoState = InfoUiState(nurses = ArrayList(response))
                remoteMessageUiState = RemoteMessageUiState.Success(infoState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




        fun logout(){
        remoteMessageUiState = RemoteMessageUiState.Error

    }



}
sealed interface RemoteMessageUiState {
    data class Success(
        val remoteMessage: InfoUiState) : RemoteMessageUiState

    object Error : RemoteMessageUiState
    object Cargant : RemoteMessageUiState
}

interface RemoteMessageInterface {
    @Headers("Accept: application/json","Content-Type: application/json")
    @GET("nurses/{id}")
    suspend fun getRemoteMessage(@Path("id")id:Int): Nurse
    @FormUrlEncoded
    @POST("nurses/login")
    suspend fun postRemoteMessage(
        @Field("user") user: String,
        @Field("password") password: String):Nurse

    @FormUrlEncoded
    @POST("nurses/")
    suspend fun postRemoteMessageRegister(
        @Field("user") user : String,
        @Field("password") password: String,
        @Field("phone_number") phone_number:Int,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String
    ):Nurse


    @GET("nurses/index")
    suspend fun getAllNurses(): List<Nurse>
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

        // NEW: Profile route
        composable("ProfileInfo") {
            currentUserId?.let { it1 ->
                ProfileInfo(
                    navController, viewModel,
                    id = it1,
                )
            }

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
