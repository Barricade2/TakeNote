package com.tete.takenote.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

private val DarkColorPalette = darkColors()

private val LightColorPalette = lightColors()

@Composable
fun TakeNoteTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors =if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview
@Composable
fun ComposablePreview() {
    TakeNoteTheme {
        Scaffold(
            topBar = { TopAppBar { Text(text = "Test") } }
        ){ Text(text="Test") }
    }
}