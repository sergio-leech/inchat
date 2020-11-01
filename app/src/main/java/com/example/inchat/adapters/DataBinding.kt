package com.example.inchat.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.example.inchat.R

@BindingAdapter("setImage")
fun ImageView.bindImage(imageUrl: String?) {
    load(imageUrl) {
        crossfade(true)
        placeholder(R.drawable.ic_anonymous_mask)
        transformations(CircleCropTransformation())
    }
}