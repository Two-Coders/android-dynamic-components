package com.twocoders.dynamic.color

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:textColor"])
fun TextView.setDynamicTextColor(dynamicColor: DynamicColor) {
    setTextColor(dynamicColor.getColor(context))
}

@BindingAdapter(value = ["android:textColorHint"])
fun TextView.setDynamicHintTextColor(dynamicColor: DynamicColor) {
    setHintTextColor(dynamicColor.getColor(context))
}

@BindingAdapter(value = ["android:textColorLink"])
fun TextView.setDynamicLinkTextColor(dynamicColor: DynamicColor) {
    setLinkTextColor(dynamicColor.getColor(context))
}

@BindingAdapter(value = ["android:tint"])
fun ImageView.setDynamicTintImageColor(dynamicColor: DynamicColor) {
    imageTintList = dynamicColor.getColorStateList(context)
}

@BindingAdapter(value = ["android:background"])
fun View.setDynamicBackgroundColor(dynamicColor: DynamicColor) {
    setBackgroundColor(dynamicColor.getColor(context))
}

@BindingAdapter(value = ["android:backgroundTint"])
fun View.setDynamicBackgroundTintColor(dynamicColor: DynamicColor) {
    backgroundTintList = dynamicColor.getColorStateList(context)
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter(value = ["android:foregroundTint"])
fun View.setDynamicForegroundTintColor(dynamicColor: DynamicColor) {
    foregroundTintList = dynamicColor.getColorStateList(context)
}