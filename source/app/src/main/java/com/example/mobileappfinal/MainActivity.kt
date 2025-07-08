package com.example.mobileappfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // პირველი Fragment - Login
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    // LoginFragment-იდან, როცა მომხმარებელი წარმატებით შედის,
    // გამოიძახე ეს მეთოდი WebViewFragment-ზე გადასასვლელად
    fun showWebView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, WebViewFragment())
            .commit()
    }

    // შეგიძლია დაამატო კიდევ Logout მეთოდი,
    // რომელიც დააბრუნებს LoginFragment-ზე
    fun logout() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commit()
    }
}
