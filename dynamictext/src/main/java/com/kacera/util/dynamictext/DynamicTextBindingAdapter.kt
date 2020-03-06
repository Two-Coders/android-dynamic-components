package com.kacera.util.dynamictext

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:text"])
fun setTextHolder(textView: TextView, textHolder: DynamicText?) {
    textView.text = textHolder?.getText(textView.context)
}

@BindingAdapter(value = ["android:hint"])
fun setHint(textView: TextView, textHolder: DynamicText?) {
    textView.hint = textHolder?.getText(textView.context)
}