package com.example.sceniocompose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearHistoriaScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val context = LocalContext.current

    var historia by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var historiaFocused by remember { mutableStateOf(false) }

    // Colores personalizados
    val backgroundColor = Color(227, 217, 204, 255)
    val borderColor = Color(199, 185, 166)
    val textColor = Color(169, 155, 139, 255)
    val blueColor = Color(9, 30, 70, 255)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "RETO DIARIO",
                    fontSize = 60.sp,
                    color = blueColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = historia,
                    onValueChange = { historia = it },
                    label = {
                        if (!historiaFocused && historia.isEmpty()) {
                            Text("ESCRIBE TU HISTORIA AQUÍ...", color = textColor, fontSize = 20.sp)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(7.dp, borderColor, shape = RoundedCornerShape(30.dp))
                        .onFocusChanged { historiaFocused = it.isFocused },
                    maxLines = 10,
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
                        val uid = user?.uid
                        if (uid != null) {
                            loading = true
                            Firebase.firestore.collection("users")
                                .document(uid)
                                .collection("historias")
                                .add(mapOf("texto" to historia, "timestamp" to FieldValue.serverTimestamp()))
                                .addOnSuccessListener {
                                    mensaje = "¡Entrada guardada exitosamente!"
                                    historia = ""
                                    loading = false
                                    navController.navigate("parati")
                                }
                                .addOnFailureListener {
                                    mensaje = "Error al guardar. Intenta de nuevo."
                                    loading = false
                                }

                            navController.navigate("inicio")
                        } else {
                            mensaje = "Usuario no autenticado"
                        }
                    },
                    enabled = historia.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    )
                ) {
                    Text("AGREGAR ENTRADA", fontSize = 30.sp, color = Color.White)
                }

                if (loading) {
                    Spacer(modifier = Modifier.height(8.dp))
                    CircularProgressIndicator()
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (mensaje.isNotEmpty()) {
                    Text(
                        text = mensaje,
                        fontSize = 18.sp,
                        color = blueColor
                    )
                }
            }
        }
    }
}
