package com.kacera.util.dynamictext

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["android:text"])
fun setTextHolder(textView: TextView, textHolder: DynamicText?) {
    textView.text = textHolder?.getText(textView.context)
}

@BindingAdapter(value = ["android:htmlText"])
fun setHtmlTextHolder(textView: TextView, textHolder: DynamicText?) {
    textView.text = textHolder?.let {
        HtmlCompat.fromHtml(it.getText(textView.context), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

@BindingAdapter(value = ["android:hint"])
fun setHint(textView: TextView, textHolder: DynamicText?) {
    textView.hint = textHolder?.getText(textView.context)
}