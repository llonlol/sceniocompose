package com.example.sceniocompose

import android.R.id.tabs
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Curso(val titulo: String, val imagenResId: Int, val ruta:String)
data class TabItem(val title: String, val iconResId: Int)

@Composable
fun CursosScreen1(navController: NavController) {
    val tabItems = listOf(
        TabItem("DIRECCIÓN", R.drawable.icon_direccion),
        TabItem("FOTOGRAFÍA", R.drawable.icon_fotografia),
        TabItem("EFECTOS", R.drawable.icon_efectos)
    )
    var selectedTabIndex by remember { mutableStateOf(0) }

    val items = listOf(
        BottomNavItem("INICIO", R.drawable.icon_inicio, "inicio"),
        BottomNavItem("CURSOS", R.drawable.icon_cursos, "cursos"),
        BottomNavItem("DIARIO", R.drawable.icon_diario, "diario"),
        BottomNavItem("CUENTA", R.drawable.icon_cuenta, "cuenta")
    )
    var selectedItem by rememberSaveable { mutableStateOf(1) }

    val cursosPorCategoria = listOf(
        listOf(
            Curso("DIRECCIÓN DE ACTORES", R.drawable.direccion_actores, "mapa_sin"),
            Curso("DIRECCIÓN EN DESARROLLO", R.drawable.direccion_desarrollo, "mapa_sin"),
            Curso("MANEJO DE CRONOGRAMA", R.drawable.direccion_cronograma, "mapa_sin"),
            Curso("ASISTENCIA DE DIRECCIÓN", R.drawable.direccion_asistencia, "mapa_sin")
        ),
        listOf(
            Curso("PLANOS DE CÁMARA", R.drawable.fotografia_planos,"mapa_planos"),
            Curso("REGLAS DE COMPOSICIÓN", R.drawable.fotografia_composicion,"mapa_sin"),
            Curso("CULTURA VISUAL", R.drawable.fotografia_cultura,"mapa_sin"),
            Curso("DIRECCIÓN DE FOTOGRAFIA", R.drawable.fotografia_direccion,"mapa_sin")
        ),
        listOf(
            Curso("MÁSCARAS Y ROTOSCOPIA", R.drawable.efectos_mascara,"mapa_sin"),
            Curso("PANTALLA VERDE", R.drawable.efectos_pantalla,"mapa_sin"),
            Curso("PLANAR TRACKING", R.drawable.efectos_planar,"mapa_sin"),
            Curso("MOTION GRAPHICS", R.drawable.efectos_motion,"mapa_sin")
        )
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
                    .padding(16.dp)
            ) {
                // Barra superior con fondo, íconos y texto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(199, 185, 166))
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        contentColor = Color(9, 30, 70, 255),
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .padding(horizontal = 8.dp),
                                color = Color(9, 30, 70, 255)
                            )
                        },
                        divider = {}
                    ) {
                        tabItems.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Image(
                                            painter = painterResource(id = tab.iconResId),
                                            contentDescription = tab.title,
                                            modifier = Modifier.size(42.dp)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = tab.title,
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontSize = 17.sp,
                                                color = if (selectedTabIndex == index)
                                                    Color(9, 30, 70, 255)
                                                else
                                                    Color(147, 134, 118, 255)
                                            )
                                        )
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tarjetas de cursos
                val cursos = cursosPorCategoria[selectedTabIndex]
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(cursos) { curso ->
                        Card(
                            onClick = {navController.navigate(curso.ruta)},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(199, 185, 166)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = curso.imagenResId),
                                    contentDescription = curso.titulo,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(130.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color(227, 217, 204, 255),
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = curso.titulo,
                                        fontSize = 20.sp,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = Color(9, 30, 70, 255)
                                        ),
                                        textAlign = TextAlign.Center
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
