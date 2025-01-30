package com.venturo.venturolistitem.ui.screens.list.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.StrokeJoin
import java.time.format.TextStyle

@Composable
fun PromoRichText(
    modifier: Modifier = Modifier,
    text: String,
    highlightedText: String,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                fontSize = 20.sp,
            )
        ) {
            append(text)
        }
        append(" ")
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                fontSize = 35.sp,
                drawStyle = Stroke(
                    miter = 10f,
                    width = 2f,
                    join = StrokeJoin.Round
                )
            )
        ) {
            append(highlightedText)
        }
    }
    BasicText(
        modifier = modifier,
        text = annotatedString,
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 20.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    )
}

@Preview
@Composable
fun PromoRichTextPreview(){
    PromoRichText(
        text = "PROMO",
        highlightedText = "50%"
    )
}