package com.example.sceniocompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import com.example.sceniocompose.R


val MiFuente = FontFamily(
    Font(R.font.fuente)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = MiFuente,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(fontFamily = MiFuente),
    headlineMedium = TextStyle(fontFamily = MiFuente),
    titleLarge = TextStyle(fontFamily = MiFuente),
    bodyMedium = TextStyle(fontFamily = MiFuente),
    labelSmall = TextStyle(fontFamily = MiFuente),
    labelLarge = TextStyle(
        fontFamily = MiFuente,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)