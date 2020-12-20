package com.twocoders.dynamic.image.picasso

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:src", "loadWithCrossFade"], requireAll = false)
fun ImageView.loadDynamicImage(image: DynamicImage, withCrossFade: Boolean = false) =
    image.loadDrawableInto(this, withCrossFade)