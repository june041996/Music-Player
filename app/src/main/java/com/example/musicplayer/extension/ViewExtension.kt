package com.example.musicplayer.extension

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.musicplayer.R
import com.example.musicplayer.model.Song
import java.time.Instant
import java.time.ZoneId

//set img song off/on
@BindingAdapter("set_url")
fun loadImage(img: ImageView, link: String) {
    Glide.with(img)
        .load(link)
        .placeholder(R.mipmap.ic_launcher)
        .into(img)
}

@BindingAdapter("set_date_time")
fun setDateTime(tv: TextView, time: String) {
    val d = Instant.ofEpochSecond(time.toLong()).atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    tv.text = "${d.dayOfMonth}-${d.monthValue - 1}-${d.year}  ${d.hour}:${d.minute}"
}

@BindingAdapter("set_icon_favourite")
fun setIcon(img: AppCompatImageButton, check: Boolean) {
    if (check) {
        img.setImageResource(R.drawable.ic_favorite)
    } else {
        img.setImageResource(R.drawable.ic_unfavorite)
    }
}

@BindingAdapter("set_icon_download")
fun setIconDownload(img: AppCompatImageButton, song: Song) {
    if (song.isOffline) {
        img.visibility = View.GONE
    } else {
        img.setImageResource(R.drawable.ic_file_download)
    }
}

@BindingAdapter("set_icon_device")
fun setIconDevice(img: AppCompatImageView, song: Song) {
    if (song.isOffline) {
        img.setImageResource(R.drawable.ic_sd_storage)
    } else {
        img.visibility = View.GONE
    }
}

@BindingAdapter("set_img_url")
fun loadImage(img: ImageView, s: Song) {
    if (s.isOffline) {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(s.urlSong)
        val data: ByteArray? = mmr.embeddedPicture
        if (data != null) {
            val bitmap = BitmapFactory.decodeByteArray(data, 0, data!!.size)
            img.setImageBitmap(bitmap)
        }
    } else {
        if (s.urlImage != "") {
            //Picasso.get().load().into(binding.imgSong)
            Glide.with(img)
                .load(s.urlImage)
                .placeholder(R.mipmap.ic_launcher)
                .into(img)
        }
    }

}



