package com.kacera.util.dynamictext.extensions

import android.content.Context
import android.os.Build
import android.text.Html
import androidx.annotation.StringRes

@Suppress("DEPRECATION")
fun Context.getText(@StringRes id: Int, vararg args: Any?): CharSequence =
    with(String.format(getString(id), *args)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(this)
        }
    }