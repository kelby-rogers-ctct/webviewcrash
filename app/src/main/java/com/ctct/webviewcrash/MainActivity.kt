package com.ctct.webviewcrash

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ctct.webviewcrash.ui.theme.WebViewCrashTheme
import java.security.AccessController.getContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WebView.setWebContentsDebuggingEnabled(true);
        setContentView(R.layout.layout)

        // Implement WebView with the given URL
        val myWebView = findViewById<WebView>(R.id.webview)
        myWebView.webViewClient = WebViewClient() // Opens pages inside the WebView instead of an external browser
        myWebView.settings.javaScriptEnabled = true // Enable JavaScript, if needed
        myWebView.loadUrl("http://www.example.com")
    }
    }


