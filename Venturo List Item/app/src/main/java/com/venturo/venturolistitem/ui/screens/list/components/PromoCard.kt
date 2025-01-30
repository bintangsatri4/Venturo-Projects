package com.venturo.venturolistitem.ui.screens.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturolistitem.MainNetworkImage
import com.venturo.venturolistitem.ui.theme.White
import androidx.compose.ui.tooling.preview.Preview
import com.venturo.venturolistitem.ui.theme.Primary300

@Composable
fun PromoCard(
    modifier: Modifier = Modifier,
    imageUrl: String? = "https://javacode.landa.id/img/promo/gambar_62661b52223ff.png",
    title: String? = "Diskon",
    discount: String? = "10%",
    description: String? = "Mengisi review yang ditentukan"
) {
    Box(
        modifier = modifier
            .width(282.dp)
            .height(158.dp)
            .clip(shape = RoundedCornerShape(16.dp))
    ) {
        MainNetworkImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = imageUrl,
            colorFilter = ColorFilter.tint(
                color = Primary300.copy(alpha = .75f),
                blendMode = BlendMode.SrcAtop
            )
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PromoRichText(
                text = title!!,
                highlightedText = discount!!
            )
            Text(
                text = description!!,
                fontSize = 12.sp,
                color = White,
            )
        }
    }
}

@Preview
@Composable
fun PromoCardPreview() {
    PromoCard(
        imageUrl = "https://javacode.landa.id/img/promo/gambar_62661b52223ff.png",
        title = "Diskon",
        discount = "10%",
        description = "Mengisi review yang ditentukan"
    )
}

