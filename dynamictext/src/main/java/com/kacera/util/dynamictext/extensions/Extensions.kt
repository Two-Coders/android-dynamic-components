package com.kacera.util.dynamictext.extensions

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.text.HtmlCompat

@Suppress("DEPRECATION")
fun Context.getText(@StringRes id: Int, vararg args: Any?): CharSequence =
    HtmlCompat.fromHtml(String.format(getString(id), *args), HtmlCompat.FROM_HTML_MODE_COMPACT)