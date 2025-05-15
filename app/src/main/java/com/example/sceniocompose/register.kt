package com.example.sceniocompose

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sceniocompose.ui.theme.SceniocomposeTheme
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign



@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    var emailFocused by remember { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }
    var usernameFocused by remember { mutableStateOf(false) }

    val backgroundColor = Color(227, 217, 204, 255)
    val borderColor = Color(199, 185, 166)
    val textColor = Color(169, 155, 139, 255)

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

            Text(
                text = "REGÍSTRATE",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 30.sp,
                    color = Color(9, 30, 70, 255)
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // NOMBRE DE USUARIO
            TextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    if (!usernameFocused && username.isEmpty()) {
                        Text("NOMBRE DE USUARIO", color = textColor, fontSize = 30.sp)
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(7.dp, borderColor, shape = RoundedCornerShape(30.dp))
                    .height(70.dp)
                    .onFocusChanged { usernameFocused = it.isFocused },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = backgroundColor,
                    unfocusedContainerColor = backgroundColor,
                    focusedTextColor = Color(199, 185, 166),
                    unfocusedTextColor = Color(199, 185, 166),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // EMAIL
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

            // CONTRASEÑA
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
                modifier = Modifier
                    .fillMaxWidth()
                    .border(7.dp, borderColor, shape = RoundedCornerShape(30.dp))
                    .height(70.dp)
                    .onFocusChanged { passwordFocused = it.isFocused },
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
                    loading = true
                    auth.createUserWithEmailAndPassword(email.trim(), password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val uid = auth.currentUser?.uid
                                val user = hashMapOf(
                                    "username" to username,
                                    "email" to email,
                                    "xp" to 1,
                                    "nvl_planos" to 1
                                )

                                uid?.let {
                                    firestore.collection("users").document(it)
                                        .set(user)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                            navController.navigate("login")
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Error al guardar usuario", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                            loading = false
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                enabled = username.isNotBlank() && email.isNotBlank() && password.length >= 6,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(9, 30, 70, 255)
                )
            ) {
                Text(
                    "REGISTRARSE",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¿YA TIENES CUENTA?",
                fontSize = 20.sp,
                color = Color(9, 30, 70, 255),
                modifier = Modifier
                    .clickable { navController.navigate("login") }
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(35.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            if (loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}
