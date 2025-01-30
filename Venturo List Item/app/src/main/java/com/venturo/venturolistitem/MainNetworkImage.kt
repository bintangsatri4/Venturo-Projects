package com.venturo.venturolistitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.venturo.venturolistitem.ui.theme.Grey400
import com.venturo.venturolistitem.ui.theme.White

// data load state
sealed class ImageLoadState {
    data object Loading : ImageLoadState()
    data object Error : ImageLoadState()
    data object Success : ImageLoadState()
    data object Empty : ImageLoadState()
}

@Composable
fun MainNetworkImage(
    modifier: Modifier = Modifier,
    imageUrl: String? = "",
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null,
) {
    var imageState by remember { mutableStateOf<ImageLoadState>(ImageLoadState.Loading) }

    if (imageState == ImageLoadState.Empty || imageState == ImageLoadState.Error) {
        PlaceholderImage(modifier = modifier)
    } else {
        AsyncImage(
            modifier = modifier.clip(RoundedCornerShape(10.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter,
            contentDescription = contentDescription ?: imageUrl,
            onState = { state ->
                when (state) {
                    is AsyncImagePainter.State.Loading -> imageState = ImageLoadState.Loading
                    is AsyncImagePainter.State.Error -> imageState = ImageLoadState.Error
                    is AsyncImagePainter.State.Success -> imageState = ImageLoadState.Success
                    is AsyncImagePainter.State.Empty -> imageState = ImageLoadState.Empty
                }
            }
        )
    }
}

// custom image placeholder
@Composable
fun PlaceholderImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Grey400.copy(alpha = .75f))
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_24px),
            contentDescription = "image placeholder",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(color = White, blendMode = BlendMode.SrcIn)
        )
    }
}