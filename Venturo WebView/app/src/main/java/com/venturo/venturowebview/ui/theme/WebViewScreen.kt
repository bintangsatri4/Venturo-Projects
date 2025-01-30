package com.venturo.venturowebview.ui.theme

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(url: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.padding(top = 16.dp), // Add padding to avoid overlap with status/app bar
        factory = {
            val webView = WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }

            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            // Set a cookie
            cookieManager.setCookie("https://venturo.id/", "guest")

            // Get cookies
            val cookies = cookieManager.getCookie("https://venturo.id/")
            println("Cookies: $cookies")

            // Remove all cookies
            cookieManager.removeAllCookies {
                // TODO on removed
            }

            // Remove session cookies
            cookieManager.removeSessionCookies {
                // TODO on removed
            }

            // Remove specific cookie
            cookieManager.setCookie("https://venturo.id/", null)

            // Refresh cookies
            cookieManager.flush()

            webView
        },
        update = {
            it.loadUrl(url)
        }
    )
}