package com.example.sceniocompose
//imports
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.sceniocompose.BottomNavItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun AccountScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser//confirma la cuenta del ususario
    val context = LocalContext.current
//colores que se usan en la app
    val backgroundColor = Color(227, 217, 204, 255)
    val borderColor = Color(199, 185, 166)
    val textColor = Color(169, 155, 139, 255)
    val blueColor = Color(9, 30, 70, 255)
    //lista de elementos de la barra de navegacion
    val items = listOf(
        BottomNavItem("INICIO", R.drawable.icon_inicio, "inicio"),
        BottomNavItem("CURSOS", R.drawable.icon_cursos, "cursos"),
        BottomNavItem("DIARIO", R.drawable.icon_diario, "diario"),
        BottomNavItem("CUENTA", R.drawable.icon_cuenta, "cuenta")
    )
    //variable que contiene el elemento actual de la barra de navegacion
    var selectedItem by rememberSaveable { mutableStateOf(3) }
    //carga el nombre de usuario de la cuenta
    var username by remember { mutableStateOf("Cargando...") }//muestra cargando mientras encuentra el dato del username
    LaunchedEffect(user?.uid) {
        user?.uid?.let { uid ->
            Firebase.firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    username = document.getString("username") ?: "No definido"
                }
                .addOnFailureListener {
                    username = "Error al cargar"
                }
        }
    }
    //carga la cantidad de puntos de xp del ususario y el nombre de ususario
    var userXP by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(user?.uid) {
        user?.uid?.let { uid ->
            Firebase.firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    username = document.getString("username") ?: "No definido"
                    userXP = document.getLong("xp")?.toInt()
                }
                .addOnFailureListener {
                    username = "Error al cargar"
                    userXP = null
                }
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = 0.dp
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(//despliega la barra de navegacion al fondo
                        icon = {
                            Image(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.label,
                                modifier = Modifier.size(70.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontSize = 21.sp,
                                style = MaterialTheme.typography.labelLarge.copy( //usa el tipo de fuente personalizada
                                    fontSize = 21.sp,
                                    color = if (selectedItem == index) Color(9, 30, 70, 255) else Color(169, 155, 139, 255)
                                )
                            )
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Unspecified, //no aplica color sobre la imagen
                            unselectedIconColor = Color.Unspecified,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ){ padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

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


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .border(8.dp, borderColor, shape = RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 15.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        // muestra la foto de perfil predeterminada
                        Image(
                            painter = painterResource(id = R.drawable.perfil),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .border(7.dp, blueColor, CircleShape)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        //muestra el nombre de usuario
                        Text(
                            text = "NOMBRE DE USUARIO:",
                            fontSize = 20.sp,
                            color = blueColor
                        )
                        Text(
                            text = username,
                            fontSize = 26.sp,
                            color = blueColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        //muestra el correo con el que se registro el usuario
                        Text(
                            text = "CORREO:",
                            fontSize = 20.sp,
                            color = blueColor
                        )
                        Text(
                            text = user?.email ?: "No disponible",
                            fontSize = 26.sp,
                            color = blueColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        //muestra el nivel del ususario
                        Text(
                            text = "NIVEL:",
                            fontSize = 20.sp,
                            color = blueColor
                        )
                        Text(
                            text = userXP?.toString() ?: "No disponible",
                            fontSize = 26.sp,
                            color = blueColor
                        )
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))
                //lleva a la opcion de cambiar nombre
                Button(
                    onClick = { navController.navigate("ajustes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    )
                ) {
                    Text(
                        text = "AJUSTES",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

