package com.mehedi.tlecevideo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mehedi.tlecevideo.R

@BindingAdapter("app:loadImage")
fun ImageView.loadImageFromUrl(imageUrl: String) {
    Glide.with(this.context).load(imageUrl).placeholder(R.drawable.img_placeholder)
        .error(R.drawable.broken_image_placeholder).into(this)

}
