package com.example.sceniocompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sceniocompose.ui.theme.SceniocomposeTheme

@Composable
fun HomeScreen(navController: NavController) {
    val items = listOf(
        BottomNavItem("INICIO", R.drawable.icon_inicio, "inicio"),
        BottomNavItem("CURSOS", R.drawable.icon_cursos, "cursos"),
        BottomNavItem("DIARIO", R.drawable.icon_diario, "diario"),
        BottomNavItem("CUENTA", R.drawable.icon_cuenta, "cuenta")
    )

    var selectedItem by rememberSaveable { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo), // Usa la misma imagen de fondo
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PARA TI...",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 60.sp,
                        color = Color(9, 30, 70, 255)
                    )
                )

                Text(
                    text = "RETO DIARIO",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 35.sp,
                        color = Color(9, 30, 70, 255)
                    )
                )

                Card(
                    onClick = {
                        navController.navigate("retodiario")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(199, 185, 166)),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 15.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.retodiario),
                        contentDescription = "Imagen del reto diario",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .border(12.dp, Color(199, 185, 166), shape = RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = "CURSO RECOMENDADO",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 35.sp,
                        color = Color(9, 30, 70, 255)
                    )
                )

                Card(
                    onClick = {
                        navController.navigate("mapa_planos")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(199, 185, 166)),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 15.dp)
                ) {
                    Text(
                    text = "PLANOS DE CÁMARA",
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 35.sp,
                        color = Color(9, 30, 70, 255)
                    )
                )
                    Image(
                        painter = painterResource(id = R.drawable.fotografia_planos),
                        contentDescription = "Imagen del curso",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .border(12.dp, Color(199, 185, 166), shape = RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

data class BottomNavItem(val label: String, val iconRes: Int, val route: String)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SceniocomposeTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}