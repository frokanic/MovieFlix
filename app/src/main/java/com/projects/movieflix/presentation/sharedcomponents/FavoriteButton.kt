package com.projects.movieflix.presentation.sharedcomponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteHeartIcon(
    isFavorite: Boolean = false,
    onFavoriteChange: (Boolean) -> Unit
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = onFavoriteChange,
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else Color(0xFF757575)
        )
    }
}

@Composable
@Preview
fun FavoriteHeartIconPreview() {
    val (isFavorite, setFavorite) = remember { mutableStateOf(false) }

    val onFavoriteChange = { newFavorite: Boolean ->
        setFavorite(newFavorite)
    }

    FavoriteHeartIcon(
        isFavorite = isFavorite,
        onFavoriteChange = onFavoriteChange
    )
}
