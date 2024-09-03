package com.ctct.webviewcrash

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


class MainActivity : ComponentActivity() {

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WebView.setWebContentsDebuggingEnabled(true);
        setContentView(R.layout.layout)

        // Implement WebView with the given URL
        val myWebView = findViewById<WebView>(R.id.webview)
        myWebView.webViewClient = WebViewClient() // Opens pages inside the WebView instead of an external browser
        myWebView.settings.javaScriptEnabled = true // Enable JavaScript, if needed
        myWebView.settings.setAllowFileAccess(true)
        myWebView.loadUrl("file:///android_asset/index.html")
    }

    }


