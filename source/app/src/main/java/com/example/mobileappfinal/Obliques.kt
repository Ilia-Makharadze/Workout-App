package com.example.mobileappfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class Obliques : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_press, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gifImageView = view.findViewById<ImageView>(R.id.plankass)
        val pushUpImageView = view.findViewById<ImageView>(R.id.pushUpView)
        val dumbbellInclineChestFlysImage=view.findViewById<ImageView>(R.id.dumbbellInclineChestFlys)

        Glide.with(this)
            .asGif()
            .load(R.drawable.planka) // ← აქ შენ გიფის ფაილის სახელი
            .into(gifImageView)

        Glide.with(this)
            .asGif()
            .load(R.drawable.russiantwist) // ← აქ შენ გიფის ფაილის სახელი
            .into(pushUpImageView)

        Glide.with(this)
            .asGif()
            .load(R.drawable.layingleg) // ← აქ შენ გიფის ფაილის სახელი
            .into(dumbbellInclineChestFlysImage)
    }
}
