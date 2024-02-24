package com.projects.movieflix.presentation.extension

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.verticalGradientScrim(
    color: Color,
    startYPercentage: Float = 0.35f,
    endYPercentage: Float = 0f
) = drawBehind {
    val startY = size.height * startYPercentage
    val endY = size.height * endYPercentage
    drawRect(
        Brush.verticalGradient(
            colors = listOf(color.copy(alpha = 0.8f), color.copy(alpha = 0f)),
            startY = startY,
            endY = endY
        )
    )
}