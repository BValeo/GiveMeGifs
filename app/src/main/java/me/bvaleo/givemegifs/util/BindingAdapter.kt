package me.bvaleo.givemegifs.util

import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context)
            .asGif()
            .load(Uri.parse(url))
            .into(view)
}