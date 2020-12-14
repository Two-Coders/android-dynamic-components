package com.twocoders.dynamic.text

import android.util.Log
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
        val text = it.getText(textView.context)
        val html = HtmlCompat.fromHtml(text.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        return@let if (html.getSpans(0, text.length, Object::class.java).isEmpty()) {
            if (text is String) Log.w("DynamicText", "No HTML tags found in the string provided! Maybe you forgot to escape HTML tags?")
            text
        } else {
            html
        }
    }
}

@BindingAdapter(value = ["android:hint"])
fun setHint(textView: TextView, textHolder: DynamicText?) {
    textView.hint = textHolder?.getText(textView.context)
}