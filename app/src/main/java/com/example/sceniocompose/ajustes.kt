package com.example.sceniocompose

//imports
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.sceniocompose.BottomNavItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore


@Composable
fun ConfigScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()//confirma la cuenta del ususario
    val user = auth.currentUser
    val context = LocalContext.current //mantiene la informacion del ususario
    //para la barra de navegacion de abajo
    var selectedItem by remember { mutableStateOf(1) }

    var username by remember { mutableStateOf("Cargando...") }//muestra cargando mientras encuentra el dato del username
    var newUsername by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var usernameFocused by remember { mutableStateOf(false) }

    //colores usados en la app
    val backgroundColor = Color(227, 217, 204, 255)
    val borderColor = Color(199, 185, 166)
    val textColor = Color(169, 155, 139, 255)
    val blueColor = Color(9, 30, 70, 255)

    //consigue el dato del username y tambien lo actualiza por el nuevo
    LaunchedEffect(user?.uid) {
        user?.uid?.let { uid ->
            Firebase.firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    username = document.getString("username") ?: "No definido"
                    newUsername = username
                }
                .addOnFailureListener {
                    username = "Error al cargar"
                }
        }
    }

    //mantiene los elementos en pantalla dentro de una caja para poder ponerle el fondo
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "MI PERFIL",
                    fontSize = 60.sp,
                    color = blueColor
                )

                Spacer(modifier = Modifier.height(6.dp))

                Image(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .border(7.dp, blueColor, CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "CAMBIA TU NOMBRE:",
                    fontSize = 20.sp,
                    color = blueColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = newUsername,
                    onValueChange = { newUsername = it },
                    label = {
                        if (!usernameFocused && newUsername.isEmpty()) {
                            Text("NOMBRE DE USUARIO", color = textColor, fontSize = 30.sp)//recibe el nombre nuevo
                        }
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(7.dp, borderColor, shape = RoundedCornerShape(30.dp))
                        .height(70.dp)
                        .onFocusChanged { usernameFocused = it.isFocused },
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
                        unfocusedIndicatorColor = Color.Transparent//los focused e unfocused son para que cambie el color de la letra si se está escribiendo en el textfield o no
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        user?.uid?.let { uid ->
                            loading = true
                            Firebase.firestore.collection("users").document(uid)
                                .update("username", newUsername)
                                .addOnSuccessListener {
                                    username = newUsername
                                    Toast.makeText(context, "Nombre actualizado", Toast.LENGTH_SHORT).show()
                                    loading = false
                                }//muestra un mensaje en caso de que haya problemas actualizando la base de datos
                                .addOnFailureListener {
                                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                                    loading = false
                                }
                        }
                        navController.navigate("cuenta")
                    },
                    enabled = newUsername.isNotBlank() && newUsername != username,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    )
                ) {
                    Text("GUARDAR CAMBIOS", fontSize = 30.sp, color = Color.White)
                }

                if (loading) {
                    Spacer(modifier = Modifier.height(8.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}
