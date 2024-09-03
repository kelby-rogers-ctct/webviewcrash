package com.ctct.webviewcrash

import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewClientCompat


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", AssetsPathHandler(this))
            .build()






        WebView.setWebContentsDebuggingEnabled(true);
        setContentView(R.layout.layout)

        // Implement WebView with the given URL
        val myWebView = findViewById<WebView>(R.id.webview)
        myWebView.webViewClient =
            WebViewClient() // Opens pages inside the WebView instead of an external browser
        myWebView.settings.javaScriptEnabled = true // Enable JavaScript, if needed
        myWebView.settings.setAllowFileAccess(true)

        myWebView.setWebViewClient(object : WebViewClientCompat() {
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return assetLoader.shouldInterceptRequest(request.url)
            }


            @Suppress("deprecation") // for API < 21
            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
                return assetLoader.shouldInterceptRequest(Uri.parse(url))
            }
        })


        val webViewSettings: WebSettings = myWebView.settings

// Setting this off for security. Off by default for SDK versions >= 16.
        webViewSettings.allowFileAccessFromFileURLs = false

// Off by default, deprecated for SDK versions >= 30.
        webViewSettings.allowUniversalAccessFromFileURLs = false


// Keeping these off is less critical but still a good idea, especially if your app is not
// using file:// or content:// URLs.
        webViewSettings.allowFileAccess = false
        webViewSettings.allowContentAccess = false


        // Assets are hosted under http(s)://appassets.androidplatform.net/assets/... .
        // If the application's assets are in the "main/assets" folder this will read the file
        // from "main/assets/www/index.html" and load it as if it were hosted on:
        // https://appassets.androidplatform.net/assets/www/index.html
        myWebView.loadUrl("https://appassets.androidplatform.net/assets/index.html")
    }

}


