package com.twocoders.dynamic.color

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:textColor"])
fun setDynamicTextColor(textView: TextView, dynamicColor: DynamicColor) {
    with(textView) { setTextColor(dynamicColor.getColor(context)) }
}

@BindingAdapter(value = ["android:textColorHint"])
fun setDynamicHintTextColor(textView: TextView, dynamicColor: DynamicColor) {
    with(textView) { setHintTextColor(dynamicColor.getColor(context)) }
}

@BindingAdapter(value = ["android:textColorLink"])
fun setDynamicLinkTextColor(textView: TextView, dynamicColor: DynamicColor) {
    with(textView) { setLinkTextColor(dynamicColor.getColor(context)) }
}

@BindingAdapter(value = ["android:tint"])
fun setDynamicTintImageColor(imageView: ImageView, dynamicColor: DynamicColor) {
    with(imageView) { imageTintList = dynamicColor.getColorStateList(context) }
}

@BindingAdapter(value = ["android:background"])
fun setDynamicBackgroundColor(view: View, dynamicColor: DynamicColor) {
    with(view) { setBackgroundColor(dynamicColor.getColor(context)) }
}

@BindingAdapter(value = ["android:backgroundTint"])
fun setDynamicTintBackgroundColor(view: View, dynamicColor: DynamicColor) {
    with(view) { backgroundTintList = dynamicColor.getColorStateList(context) }
}

@RequiresApi(Build.VERSION_CODES.M)
@BindingAdapter(value = ["android:foregroundTint"])
fun setDynamicTintForegroundColor(view: View, dynamicColor: DynamicColor) {
    with(view) { foregroundTintList = dynamicColor.getColorStateList(context) }
}