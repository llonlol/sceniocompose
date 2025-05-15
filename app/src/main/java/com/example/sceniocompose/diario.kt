package com.example.sceniocompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.sceniocompose.BottomNavItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiarioScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val stories = remember { mutableStateListOf<Triple<String, Timestamp, String>>() }

    val backgroundColor = Color(227, 217, 204, 255)
    val borderColor = Color(199, 185, 166)
    val textColor = Color(169, 155, 139, 255)
    val blueColor = Color(9, 30, 70, 255)

    LaunchedEffect(user?.uid) {
        user?.uid?.let { uid ->
            Firebase.firestore.collection("users")
                .document(uid)
                .collection("historias")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    stories.clear()
                    for (document in result) {
                        val texto = document.getString("texto") ?: ""
                        val fecha = document.getTimestamp("timestamp") ?: Timestamp.now()
                        val id = document.id
                        stories.add(Triple(texto, fecha, id))
                    }
                }
        }
    }

    var selectedItem by rememberSaveable { mutableStateOf(2) } // Diario es el índice 2

    val items = listOf(
        BottomNavItem("INICIO", R.drawable.icon_inicio, "inicio"),
        BottomNavItem("CURSOS", R.drawable.icon_cursos, "cursos"),
        BottomNavItem("DIARIO", R.drawable.icon_diario, "diario"),
        BottomNavItem("CUENTA", R.drawable.icon_cuenta, "cuenta")
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                NavigationBar(
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
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
                                    style = MaterialTheme.typography.labelLarge.copy(
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
                                selectedIconColor = Color.Unspecified,
                                unselectedIconColor = Color.Unspecified,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DIARIO",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 60.sp,
                        color = Color(9, 30, 70, 255)
                    )
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(stories) { (texto, fecha, docId) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .border(8.dp, borderColor, shape = RoundedCornerShape(16.dp)),
                            colors = CardDefaults.cardColors(containerColor = backgroundColor),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 15.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Fecha: ${fecha.toDate()}",
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 20.sp),
                                        color = Color(9, 30, 70, 255)
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = texto,
                                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                                        color = Color(9, 30, 70, 255)
                                    )


                                }
                                IconButton(onClick = {
                                    user?.uid?.let { uid ->
                                        Firebase.firestore.collection("users")
                                            .document(uid)
                                            .collection("historias")
                                            .document(docId)
                                            .delete()
                                            .addOnSuccessListener {
                                                stories.removeIf { it.third == docId }
                                            }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar historia",
                                        tint = Color(9, 30, 70, 255)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

