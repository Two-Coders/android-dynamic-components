package com.twocoders.dynamic.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.twocoders.dynamic.image.component.UriComponent
import com.twocoders.extensions.common.NO_ID
import com.twocoders.extensions.common.getDrawable
import com.twocoders.extensions.common.logd

/**
 *
 * Handy class which can be used to bind image data to views using them.
 * Data can be in [DrawableRes], [Drawable], or [UriComponent] format.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
abstract class BaseDynamicImage : Parcelable {

    @DrawableRes protected val imageRes: Int?
    protected val imageDrawable: Drawable?
    protected val imageUri: UriComponent?

    protected constructor(
        @ColorInt imageRes: Int? = null,
        imageDrawable: Drawable? = null,
        imageUri: UriComponent? = null
    ) {
        this.imageRes = imageRes
        this.imageDrawable = imageDrawable
        this.imageUri = imageUri
    }

    protected constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readDrawable(),
        parcel.readParcelable(UriComponent::class.java.classLoader)
    )

    @CallSuper
    open suspend fun getDrawable(context: Context): Drawable? {
        imageDrawable?.let { drawable ->
            return drawable
        }

        imageRes?.let { res ->
            return context.getDrawable(drawableResId = res)
        }

        return null
    }

    @CallSuper
    open fun getDrawable(context: Context, callback: (drawable: Drawable) -> Unit) {
        imageDrawable?.let { drawable ->
            callback(drawable)
        }

        imageRes?.let { res ->
            context.getDrawable(drawableResId = res)?.let { callback(it) }
        }
    }

    abstract fun loadDrawableInto(imageView: ImageView, withCrossFade: Boolean = false)

    fun isEmpty() = imageRes == null && imageDrawable == null && imageUri == null

    fun isNotEmpty() = !isEmpty()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageRes ?: NO_ID)
        parcel.writeParcelable(imageDrawable.getParcelable(), flags)
        parcel.writeParcelable(imageUri, flags)
    }

    override fun describeContents() = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseDynamicImage

        if (imageRes != other.imageRes) return false
        if (imageDrawable != other.imageDrawable) return false
        if (imageUri != other.imageUri) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageRes ?: NO_ID
        result = 31 * result + imageDrawable.hashCode()
        result = 31 * result + imageUri.hashCode()
        return result
    }
}

private fun Parcel.readDrawable(): Drawable? =
    when (val parcelDrawable = readParcelable<Parcelable>(BaseDynamicImage::class.java.classLoader)) {
        is Bitmap -> @Suppress("DEPRECATION") BitmapDrawable(parcelDrawable)
        else -> null
    }

private fun Drawable?.getParcelable(): Parcelable? = when (this) {
    is BitmapDrawable -> bitmap
    is Drawable -> {
        logd("Unsupported $this in imageDrawable for parcel, only BitmapDrawable is supported now.")
        null
    }
    else -> null
}