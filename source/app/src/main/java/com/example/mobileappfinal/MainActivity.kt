package com.example.mobileappfinal

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.JavascriptInterface
import android.widget.Toast  // ეს უნდა დაამატოთ Toast-ს გამოყენებისთვის
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // WebView-ის ნახვა
        val webView: WebView = findViewById(R.id.webview)

        // JavaScript-ის მხარდაჭერის აქტივაცია
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // JavaScript interface-ის შექმნა
        webView.addJavascriptInterface(WebAppInterface(this), "Android")

        // SVG ფაილის დატვირთვა assets ფოლდერიდან
//        webView.loadUrl("file:///android_asset/human_body.svg")
//        webView.loadUrl("file:///android_asset/svg-path%20(1).svg")
        webView.loadUrl("file:///android_asset/ssss.svg")


         //JavaScript-ის კოდი, რომელიც ელემენტზე click მოვლენას დაადგენს
        val jsCode = """
            function setupClicks() {
                var chestLeft = document.getElementById('chest1');
                chestLeft.addEventListener('click', function() {
                    Android.onMuscleClicked('chest1');
                });

                // დამატებით კუნთების ID-ს დასმა
                var chestRight = document.getElementById('chest2');
                chestRight.addEventListener('click', function() {
                    Android.onMuscleClicked('chest2');
                });

                // სხვა კუნთებიც აქ დააყენეთ...
            }
            setupClicks();
        """.trimIndent()

        // JavaScript კოდის შესრულება
        webView.evaluateJavascript(jsCode, null)
    }
}

// JavaScript Interface კლასი
class WebAppInterface(private val activity: MainActivity) {

    @JavascriptInterface
    fun onMuscleClicked(muscleId: String) {
        activity.runOnUiThread {
            val js = """
            (function(){
                var el = document.getElementById('$muscleId');
                if (el) {
                    var originalFill = el.style.fill;
                    el.style.fill = 'red';
                    setTimeout(function() {
                        el.style.fill = originalFill;
                    }, 300);
                }
            })();
        """.trimIndent()

            val webView = activity.findViewById<WebView>(R.id.webview)
            webView.evaluateJavascript(js, null)

            // Fragment გადასვლა 1.5 წამით დაყოვნებით
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                if (muscleId == "chest1" || muscleId == "chest2") {
                    val fragment = Chest()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.webview, fragment) // თუ Fragment container-ს სხვა ID აქვს, აქ შეცვალე
                        .addToBackStack(null)
                        .commit()
                }
            }, 1500)
        }
    }


}
