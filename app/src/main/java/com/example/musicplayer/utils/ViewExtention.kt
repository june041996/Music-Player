package com.example.musicplayer.utils


import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("set_url")
fun loadImage(img: ShapeableImageView, link: String?) {
    Glide.with(img).load(link).placeholder(R.drawable.music).into(img)
}


//fun getImgSong(path:String):ByteArray?{
//    val retriever = MediaMetadataRetriever()
//    retriever.setDataSource(path)
//    return retriever.embeddedPicture
//}