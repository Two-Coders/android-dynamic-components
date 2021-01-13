package com.twocoders.dynamic.image.picasso

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.twocoders.dynamic.image.base.BaseDynamicImage
import com.twocoders.dynamic.image.base.component.UriComponent

/**
 *
 * Handy class which can be used to bind image data to views.
 * Data can be in [DrawableRes], [Drawable], or [UriComponent] format.
 *
 * Use one of [DynamicImage.from] creator methods to create the data
 * and [DynamicImage.getDrawable] to obtain the final [Drawable] output. Or you can
 * provide target [ImageView] class into the [DynamicImage.loadDrawableInto] method.
 *
 * This [DynamicImage] implementation uses [Picasso], for other implementations please visit our GitHub:
 * [Android Dynamic Components](https://github.com/Two-Coders/android-dynamic-components)
 *
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class DynamicImage : BaseDynamicImage {

    protected constructor(
        @ColorInt imageRes: Int? = null,
        imageDrawable: Drawable? = null,
        imageUri: UriComponent? = null
    ) : super(imageRes, imageDrawable, imageUri)

    protected constructor(parcel: Parcel) : super(parcel)

    companion object {

        val EMPTY = DynamicImage()

        @JvmStatic
        fun from(@DrawableRes imageRes: Int) = DynamicImage(imageRes = imageRes)

        @JvmStatic
        fun from(imageDrawable: Drawable) = DynamicImage(imageDrawable = imageDrawable)

        @JvmStatic
        fun from(imageUri: UriComponent) = DynamicImage(imageUri = imageUri)

        @JvmField
        val CREATOR = object : Parcelable.Creator<DynamicImage> {
            override fun createFromParcel(parcel: Parcel) = DynamicImage(parcel)
            override fun newArray(size: Int): Array<DynamicImage?> = arrayOfNulls(size)
        }
    }

    override suspend fun getDrawableFromUri(
        context: Context,
        imageUriComponent: UriComponent
    ) = BitmapDrawable(
        context.resources,
        Picasso.get()
            .load(imageUriComponent.image)
            .get()
    )

    override fun getDrawableFromUri(
        context: Context,
        imageUriComponent: UriComponent,
        callback: (drawable: Drawable) -> Unit
    ) {
        Picasso.get()
            .load(imageUriComponent.image)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    callback(BitmapDrawable(context.resources, bitmap))
                }
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            })
    }

    override fun loadDrawableInto(
        imageView: ImageView,
        withCrossFade: Boolean
    ) {
        imageUri?.let { imageUriComponent ->
            Picasso.get()
                .load(imageUriComponent.image)
                .apply {
                    imageUriComponent.placeholderImage?.let { placeholder(it) }
                    imageUriComponent.errorImage?.let { error(it) }
                }
                .into(imageView)
        }

        imageDrawable?.let {
            imageView.setImageDrawable(it)
        }

        imageRes?.let {
            Picasso.get().load(it).into(imageView)
        }
    }
}