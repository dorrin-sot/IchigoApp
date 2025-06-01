package com.example.ichigoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.ichigoapp.R

val CaveatFontFamily = FontFamily(
  Font(R.font.caveat_regular),
  Font(R.font.caveat_medium),
  Font(R.font.caveat_bold),
  Font(R.font.caveat_semi_bold),
)

val AppTypography = Typography(
  displayLarge = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 57.sp,
    lineHeight = 64.sp,
    letterSpacing = (-0.25).sp
  ),
  displayMedium = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 45.sp,
    lineHeight = 52.sp,
    letterSpacing = 0.sp
  ),
  displaySmall = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 36.sp,
    lineHeight = 44.sp,
    letterSpacing = 0.sp
  ),

  headlineLarge = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp
  ),
  headlineMedium = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 28.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp
  ),
  headlineSmall = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp
  ),

  titleLarge = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
  ),
  titleMedium = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp
  ),
  titleSmall = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp
  ),

  bodyLarge = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
  ),
  bodyMedium = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.25.sp
  ),
  bodySmall = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.4.sp
  ),

  labelLarge = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp
  ),
  labelMedium = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
  ),
  labelSmall = TextStyle(
    fontFamily = CaveatFontFamily,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
  )
)
