package com.kacera.util.dynamictext

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:text"])
fun setTextHolder(textView: TextView, textHolder: DynamicText?) {
    textHolder?.let {
        textView.text = it.getText(textView.context)
        return
    }

    textView.text = null
}