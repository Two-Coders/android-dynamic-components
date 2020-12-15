package com.twocoders.dynamic.text

fun String?.toDynamicText() = this?.let { DynamicText.from(it) } ?: DynamicText.EMPTY