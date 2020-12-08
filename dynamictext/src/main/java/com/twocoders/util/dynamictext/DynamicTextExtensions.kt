package com.twocoders.util.dynamictext

fun String?.toDynamicText() = this?.let { DynamicText.from(it) } ?: DynamicText.EMPTY