package com.example.sceniocompose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun Planos3Prg3Screen(navController: NavController) {
    val selectedItem = rememberSaveable { mutableStateOf(1) }
    val progress = 6 // indica el progreso actual

    val items = listOf(
        BottomNavItem("INICIO", R.drawable.icon_inicio, "inicio"),
        BottomNavItem("CURSOS", R.drawable.icon_cursos, "cursos"),
        BottomNavItem("DIARIO", R.drawable.icon_diario, "diario"),
        BottomNavItem("CUENTA", R.drawable.icon_cuenta, "cuenta")
    )

    val backgroundColor = Color(227, 217, 204)
    val borderColor = Color(199, 185, 166)
    val blueColor = Color(9, 30, 70)
    val textColor = Color(169, 155, 139)
    val circleInactiveColor = Color(60, 50, 40)

    var sliderValue by remember { mutableFloatStateOf(0f) }

    val isSliderAtTop = sliderValue > 0.1f && sliderValue <0.2f//valor en el que el boton se activa con el slider

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
                                    color = if (selectedItem.value == index) blueColor else textColor
                                )
                            )
                        },
                        selected = selectedItem.value == index,
                        onClick = {
                            selectedItem.value = index
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

                // progreso
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


                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(borderColor)
                        .padding(vertical = 16.dp, horizontal = 20.dp),
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        text = "¿Que tan cerca deberia estar la cámara para tener un primer plano?",
                        fontSize = 30.sp,
                        color = blueColor
                    )
                }

                // despliega la imagen y el slider juntos
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.width(40.dp))

                    Image(
                        painter = painterResource(id = R.drawable.persona),
                        contentDescription = "persona",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(230.dp)
                            .width(130.dp)
                    )

                    Column(
                        modifier = Modifier.height(200.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Slider(
                            value = sliderValue,
                            onValueChange = { sliderValue = it },
                            modifier = Modifier
                                .height(200.dp)
                                .rotate(0f),
                            valueRange = 0f..1f,
                            colors = SliderDefaults.colors(
                                thumbColor = blueColor,
                                activeTrackColor = blueColor,
                                inactiveTrackColor = Color.Gray
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        val db = FirebaseFirestore.getInstance()
                        val userId = FirebaseAuth.getInstance().currentUser?.uid

                        if (userId != null) {
                            db.collection("users")
                                .document(userId)
                                .update("xp", FieldValue.increment(1))//suma 1 al nivel de la cuenta
                                .addOnSuccessListener {
                                    navController.navigate("final")
                                }
                                .addOnFailureListener { e ->
                                    Log.e("Firestore", "Error al actualizar nivel", e)
                                }
                        }
                    },
                    enabled = isSliderAtTop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSliderAtTop) blueColor else Color.Gray
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