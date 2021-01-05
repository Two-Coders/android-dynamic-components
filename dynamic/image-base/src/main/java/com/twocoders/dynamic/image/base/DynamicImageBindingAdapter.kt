package com.twocoders.dynamic.image.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:src", "loadWithCrossFade"], requireAll = false)
fun ImageView.loadDynamicImage(image: BaseDynamicImage, withCrossFade: Boolean = false) =
    image.loadDrawableInto(this, withCrossFade)