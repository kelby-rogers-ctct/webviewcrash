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

    val TAG: String = "WebViewCrash"

    val unencodedHtml = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Three.js Spinning Cube</title>
            <style>
                body { margin: 0; }
                canvas { display: block; }
            </style>
        </head>
        <body>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r128/three.min.js"></script>
            <script>
                // Set up the scene, camera, and renderer
                const scene = new THREE.Scene();
                const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
                const renderer = new THREE.WebGLRenderer();
                renderer.setSize(window.innerWidth, window.innerHeight);
                document.body.appendChild(renderer.domElement);

                // Create a cube
                const geometry = new THREE.BoxGeometry();
                const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
                const cube = new THREE.Mesh(geometry, material);
                scene.add(cube);

                camera.position.z = 5;

                // Animation loop
                function animate() {
                    requestAnimationFrame(animate);

                    // Rotate the cube
                    cube.rotation.x += 0.01;
                    cube.rotation.y += 0.01;

                    renderer.render(scene, camera);
                }

                animate();

                // Handle window resizing
                window.addEventListener('resize', () => {
                    camera.aspect = window.innerWidth / window.innerHeight;
                    camera.updateProjectionMatrix();
                    renderer.setSize(window.innerWidth, window.innerHeight);
                });
            </script>
        </body>
        </html>
    """.trimIndent()

    @OptIn(ExperimentalEncodingApi::class)
    val encodedHtml = Base64.encode(unencodedHtml.toByteArray())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WebView.setWebContentsDebuggingEnabled(true);
        setContentView(R.layout.layout)

        // Implement WebView with the given URL
        val myWebView = findViewById<WebView>(R.id.webview)
        myWebView.webViewClient = WebViewClient() // Opens pages inside the WebView instead of an external browser
        myWebView.settings.javaScriptEnabled = true // Enable JavaScript, if needed
        myWebView.loadData(encodedHtml, "text/html","base64")
    }

    }


