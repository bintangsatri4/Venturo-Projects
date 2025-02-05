package com.venturo.venturoreport

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.venturo.venturoreport.R

@Composable
fun MainNetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                placeholder(R.drawable.grid_guides_24px)
                error(R.drawable.grid_guides_24px)
            }
        ),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}