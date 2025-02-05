// RichText.kt
package com.venturo.venturoreport

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun RichText(
    text: String,
    highlightedText: String,
    textStyle: SpanStyle,
    highlightedTextStyle: SpanStyle
) {
    val annotatedString = buildAnnotatedString {
        append(text)
        append(" ")
        pushStyle(highlightedTextStyle)
        append(highlightedText)
        pop()
    }
    BasicText(text = annotatedString)
}