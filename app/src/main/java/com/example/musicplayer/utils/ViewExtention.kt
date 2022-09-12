package com.example.musicplayer.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.musicplayer.R

@BindingAdapter("set_url")
fun loadImage(img: AppCompatImageView, link: String) {
    Glide.with(img).load(link).placeholder(R.drawable.ic_music_note).into(img)
}