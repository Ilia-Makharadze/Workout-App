package com.example.mobileappfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class Chest : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gifImageView = view.findViewById<ImageView>(R.id.barbelChestView)
        val pushUpImageView = view.findViewById<ImageView>(R.id.pushUpView)
        val dumbbellInclineChestFlysImage=view.findViewById<ImageView>(R.id.dumbbellInclineChestFlys)

        Glide.with(this)
            .asGif()
            .load(R.drawable.male_barbell_bench_press_side_givnk12)
            .into(gifImageView)

        Glide.with(this)
            .asGif()
            .load(R.drawable.male_bodyweight_pushup_front)
            .into(pushUpImageView)

        Glide.with(this)
            .asGif()
            .load(R.drawable.male_dumbbell_incline_chest_flys_side)
            .into(dumbbellInclineChestFlysImage)
    }
}
