package com.example.sceniocompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Planos3Exp2Screen(navController: NavController) {
    val progress = 3
    var selectedItem by rememberSaveable { mutableStateOf(1) } // Diario es el índice 2

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
    val circleInactiveColor = Color(60, 50, 40)

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
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(borderColor)
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(6) { index ->
                            val color = if (index < progress) blueColor else circleInactiveColor
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

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

                        Image(
                            painter = painterResource(id = R.drawable.planos3_exp2a),
                            contentDescription = "Imagen de explicacion",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Image(
                            painter = painterResource(id = R.drawable.planos3_exp2b),
                            contentDescription = "Imagen de explicacion",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Image(
                            painter = painterResource(id = R.drawable.planos3_exp2c),
                            contentDescription = "Imagen de explicacion",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Estos NO son primeros planos.",
                            fontSize = 20.sp,
                            color = blueColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        navController.navigate("planos3_prg1")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    )
                ) {
                    Text(
                        text = "ENTIENDO!",
                        fontSize = 30.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}