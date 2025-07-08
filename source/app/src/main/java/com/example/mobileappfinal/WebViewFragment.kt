package com.example.mobileappfinal

import android.os.Bundle
import android.view.*
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.appcompat.app.AlertDialog

class WebViewFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)

        webView = view.findViewById(R.id.webview)
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.addJavascriptInterface(WebAppInterface(requireActivity() as MainActivity), "Android")

        loadSvg("file:///android_asset/ssss.svg") // დეფოლტად წინა მხარე

        setupMenu()

        return view
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.webview_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId) {
                    R.id.action_front_body -> {
                        loadSvg("file:///android_asset/ssss.svg")
                        true
                    }
                    R.id.action_back_body -> {
                        loadSvg("file:///android_asset/svg-path.svg")
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun loadSvg(url: String) {
        webView.loadUrl(url)

        val jsCode = """
            function setupClicks() {
                const ids = [
                    'chest1', 'chest2', 'biceps1', 'biceps2', 'press',
                    'shoulders1', 'shoulders2', 'forearms1', 'forearms2',
                    'quads1', 'quads2', 'obliques1', 'obliques2'
                ];
                ids.forEach(id => {
                    var el = document.getElementById(id);
                    if (el) {
                        el.addEventListener('click', function() {
                            Android.onMuscleClicked(id);
                        });
                    }
                });
            }
            setupClicks();
        """.trimIndent()

        // ველი რომ SVG ჩაიტვირთოს და მერე ვრთავთ JS-ს
        webView.postDelayed({
            webView.evaluateJavascript(jsCode, null)
        }, 500)
    }

    inner class WebAppInterface(private val activity: MainActivity) {

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

                webView.evaluateJavascript(js, null)

                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    val fragment = when (muscleId) {
                        "chest1", "chest2" -> Chest()
                        "biceps1", "biceps2" -> Biceps()
                        "press" -> Press()
                        "shoulders1", "shoulders2" -> Shoulders()
                        "forearms1", "forearms2" -> Forearms()
                        "quads1", "quads2" -> Quads()
                        "obliques1", "obliques2" -> Obliques()
                        else -> null
                    }

                    fragment?.let {
                        activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, it)
                            .addToBackStack(null)
                            .commit()
                    }
                }, 900)
            }
        }
    }
}
