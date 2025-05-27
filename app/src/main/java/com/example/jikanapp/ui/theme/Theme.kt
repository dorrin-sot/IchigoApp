package com.example.jikanapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
  primary = PrimaryColor,
  secondary = SecondaryColor,
  background = BackgroundColor,
  surface = SurfaceColor,
  onPrimary = OnPrimary,
  onSecondary = OnSecondary,
  onBackground = OnBackground,
  onSurface = OnSurface,
)

private val DarkColors = darkColorScheme(
  primary = PrimaryColor,
  secondary = SecondaryColor,
  background = Color(0xFF121212),
  surface = Color(0xFF121212),
  onPrimary = OnPrimary,
  onSecondary = OnSecondary,
  onBackground = Color.White,
  onSurface = Color.White,
)

@Composable
fun AppTheme(
  darkTheme: Boolean = false,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColors else LightColors,
    typography = AppTypography,
    content = content
  )
}
