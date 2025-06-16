package com.example.mobileappfinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class Chest : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // დაბრუნდება fragment_chest.xml როგორც Fragment-ის View
        return inflater.inflate(R.layout.fragment_chest, container, false)
    }
}