package com.example.sceniocompose

import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sceniocompose.ui.theme.SceniocomposeTheme
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            SceniocomposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {
                        composable("signup") { SignUpScreen(navController) }
                        composable("login") { LoginScreen(navController) }
                        composable("inicio") { HomeScreen(navController) }
                        composable("cuenta") { AccountScreen(navController) }
                        composable("ajustes") { ConfigScreen(navController) }
                        composable("retodiario") { RetoDiarioScreen(navController) }
                        composable("retodiario_texto") { CrearHistoriaScreen(navController) }
                        composable("diario") { DiarioScreen(navController) }
                        composable("cursos") { CursosScreen1(navController) }
                        composable("error") { ErrorScreen(navController) }
                        composable("final") { FinalScreen(navController) }
                        composable("mapa_planos") { MapaPlanosScreen(navController) }
                        composable("mapa_sin") { MapaSinScreen(navController) }
                        composable("planos1") { Planos1Screen(navController) }
                        composable("planos1_exp1") { Planos1Exp1Screen(navController) }
                        composable("planos1_exp2") { Planos1Exp2Screen(navController) }
                        composable("planos1_prg1") { Planos1Prg1Screen(navController) }
                        composable("planos1_prg2") { Planos1Prg2Screen(navController) }
                        composable("planos1_prg3") { Planos1Prg3Screen(navController) }
                        composable("planos2") { Planos2Screen(navController) }
                        composable("planos2_exp2") { Planos2Exp2Screen(navController) }
                        composable("planos2_prg1") { Planos2Prg1Screen(navController) }
                        composable("planos2_prg2") { Planos2Prg2Screen(navController) }
                        composable("planos2_prg3") { Planos2Prg3Screen(navController) }
                        composable("planos3") { Planos3Screen(navController) }
                        composable("planos3_exp2") { Planos3Exp2Screen(navController) }
                        composable("planos3_prg1") { Planos3Prg1Screen(navController) }
                        composable("planos3_prg2") { Planos3Prg2Screen(navController) }
                        composable("planos3_prg3") { Planos3Prg3Screen(navController) }

                    }
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginSuccess by remember { mutableStateOf<Boolean?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val auth = FirebaseAuth.getInstance()

    var emailFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SCENIO",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 100.sp,
                    color = Color(9, 30, 70, 255)
                )
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "INICIA SESIÓN",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 30.sp,
                    color = Color(9, 30, 70, 255)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            val backgroundColor = Color(227, 217, 204, 255)
            val borderColor = Color(199, 185, 166)
            val textColor = Color(169, 155, 139, 255)

            // EMAIL FIELD
            TextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    if (!emailFocused && email.isEmpty()) {
                        Text("CORREO", color = textColor, fontSize = 30.sp)
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(7.dp, borderColor, shape = RoundedCornerShape(30.dp))
                    .height(70.dp)
                    .onFocusChanged { emailFocused = it.isFocused },
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    color = Color(60, 50, 40)
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = backgroundColor,
                    unfocusedContainerColor = backgroundColor,
                    focusedTextColor = Color(60, 50, 40),
                    unfocusedTextColor = Color(60, 50, 40),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // PASSWORD FIELD
            TextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    if (!passwordFocused && password.isEmpty()) {
                        Text("CONTRASEÑA", color = textColor, fontSize = 30.sp)
                    }
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(7.dp, borderColor, shape = RoundedCornerShape(30.dp))
                    .height(70.dp)
                    .onFocusChanged { passwordFocused = it.isFocused },
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp,
                    color = Color(60, 50, 40)
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = backgroundColor,
                    unfocusedContainerColor = backgroundColor,
                    focusedTextColor = Color(60, 50, 40),
                    unfocusedTextColor = Color(60, 50, 40),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                loginSuccess = true
                                errorMessage = null
                                navController.navigate("inicio") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                loginSuccess = false
                                errorMessage = task.exception?.message
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(9, 30, 70, 255)
                )
            ) {
                Text(
                    "ACCIÓN!",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¿NO TIENES CUENTA?",
                fontSize = 20.sp,
                color = Color(9, 30, 70, 255),
                modifier = Modifier
                    .clickable { navController.navigate("signup") }
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(45.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            loginSuccess?.let { success ->
                Text(
                    text = if (success) "Login Successful!" else errorMessage ?: "Login Failed",
                    color = if (success) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}




