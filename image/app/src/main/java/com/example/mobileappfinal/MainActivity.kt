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

    // ამ ფუნქციაში მოვიდა ინფორმაცია JavaScript-იდან
    @JavascriptInterface
    fun onMuscleClicked(muscleId: String) {
        // აქ შეგიძლიათ დაამატოთ ლოგიკა, როგორ უნდა მოიქცეს აპლიკაცია, როდესაც კუნთზე დაკლიკება ხდება
        if (muscleId == "chest1" || muscleId == "chest2") {
            activity.runOnUiThread {
                val fragment = Chest()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.webview, fragment) // ID იგივე უნდა იყოს მთავარ layout-ში
                    .addToBackStack(null) // რომ დაბრუნება შეიძლებოდეს
                    .commit()
            }
        }
    }
}
