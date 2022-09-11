package com.example.musicplayer.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.musicplayer.R

@BindingAdapter("set_url")
fun loadImage(img: ImageView, link: String) {
    Glide.with(img)
        .load(link)
        .placeholder(R.mipmap.ic_launcher)
        .into(img)
}
