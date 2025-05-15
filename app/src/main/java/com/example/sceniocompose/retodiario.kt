package com.example.sceniocompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import com.example.sceniocompose.BottomNavItem

@Composable
fun RetoDiarioScreen(navController: NavController) {



    var selectedItem by rememberSaveable { mutableStateOf(2) } // Diario es el índice 2

    val items = listOf(
        BottomNavItem("INICIO", R.drawable.icon_inicio, "inicio"),
        BottomNavItem("CURSOS", R.drawable.icon_cursos, "cursos"),
        BottomNavItem("DIARIO", R.drawable.icon_diario, "diario"),
        BottomNavItem("CUENTA", R.drawable.icon_cuenta, "cuenta")
    )

    val backgroundColor = Color(227, 217, 204, 255)
    val borderColor = Color(199, 185, 166)
    val blueColor = Color(9, 30, 70, 255)
    val textColor = Color(169, 155, 139, 255)

    Scaffold(
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
                                    color = if (selectedItem == index) blueColor else textColor
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
                Spacer(modifier = Modifier.height(32.dp))

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
                        horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                        Text(
                            text = "RETO DIARIO",
                            fontSize = 60.sp,
                            color = blueColor
                        )

                        Spacer(modifier = Modifier.height(1.dp))

                        Image(
                            painter = painterResource(id = R.drawable.retodiario1),
                            contentDescription = "Imagen del reto diario",
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(200.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Crea una historia de terror con solo 2 líneas, asegúrate de poner un giro interesante al final.",
                            fontSize = 20.sp,
                            color = blueColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        navController.navigate("retodiario_texto")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    )
                ) {
                    Text(
                        text = "HAGÁMOSLO",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
