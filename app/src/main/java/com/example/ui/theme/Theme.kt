package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFBBF24), // Glowing gold
    onPrimary = Color(0xFF451A03),
    primaryContainer = Color(0xFF78350F),
    onPrimaryContainer = Color(0xFFFEF3C7),
    secondary = Color(0xFFF59E0B),
    onSecondary = Color(0xFF451A03),
    secondaryContainer = Color(0xFF451A03),
    onSecondaryContainer = Color(0xFFFDE68A),
    tertiary = Color(0xFFFB923C),
    onTertiary = Color(0xFF431407),
    background = Color(0xFF1C130E),
    onBackground = Color(0xFFFDFBF7),
    surface = Color(0xFF271A14),
    onSurface = Color(0xFFFDFBF7),
    surfaceVariant = Color(0xFF38261E),
    onSurfaceVariant = Color(0xFFD7CCC8),
    outline = Color(0xFF5D4037)
)

private val LightColorScheme = lightColorScheme(
    primary = SacredTerracotta,
    onPrimary = Color.White,
    primaryContainer = SacredAmberContainer,
    onPrimaryContainer = SacredTerracottaDark,
    secondary = SacredGold,
    onSecondary = Color.White,
    secondaryContainer = SacredAmberContainer,
    onSecondaryContainer = SacredTextDark,
    tertiary = SacredMarigold,
    onTertiary = SacredTextDark,
    background = TempleCream,
    onBackground = SacredTextDark,
    surface = TempleSurface,
    onSurface = SacredTextDark,
    surfaceVariant = TempleSurfaceVariant,
    onSurfaceVariant = SacredTextMuted,
    outline = SacredBorder
)

@Composable
fun SriKrishnaSaiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
