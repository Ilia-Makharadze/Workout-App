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
                var bicepsRight=document.getElementById('biceps1');
                biceps1.addEventListener('click',function(){
                    Android.onMuscleCliked('biceps1');
                });
                
                  var bicepsLeft=document.getElementById('biceps2');
                biceps1.addEventListener('click',function(){
                    Android.onMuscleCliked('biceps2');
                });
                
                var press=document.getElementById('press');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('press');
                })
                var shoulderRight=document.getElementById('shoulders1');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('shoulders1');
                })
                var shoulderLeft=document.getElementById('shoulders2');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('shoulders2');
                })
                var forearms1=document.getElementById('forearms1');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('forearms1');
                })
                var forearms2=document.getElementById('forearms2');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('forearms2');
                })
                 var quads1=document.getElementById('quads1');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('quads1');
                })
                 var quads2=document.getElementById('quads2');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('quads2');
                })
                obliques1
                 var obliques1=document.getElementById('obliques1');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('obliques1');
                })
                 var obliques2=document.getElementById('obliques2');
                press.addEventListener('click',function(){
                    Android.onMuscleClicked('obliques2');
                })
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
                if(muscleId=="biceps1" || muscleId=="biceps2"){
                    val fragment = Biceps()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.webview, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                if(muscleId=="press"){
                    val fragment=Press()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.webview, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                if(muscleId=="shoulders1" || muscleId=="shoulders2"){
                    val fragment=Shoulders()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.webview, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                if(muscleId=="forearms1" || muscleId=="forearms2" ){
                    val fragment=Forearms()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.webview, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                if(muscleId=="quads1" || muscleId=="quads2" ){
                    val fragment=Quads()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.webview, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                if(muscleId=="obliques1" || muscleId=="obliques2" ){
                    val fragment=Obliques()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.webview, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }, 900)
        }
    }


}
