package com.twocoders.dynamic.image.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.twocoders.dynamic.image.BaseDynamicImage
import com.twocoders.dynamic.image.component.UriComponent

/**
 *
 * Handy class which can be used to bind image data to views using them.
 * Data can be in [DrawableRes], [Drawable], or [UriComponent] format.
 *
 * Use one of [DynamicImage.from] creator methods to create the data
 * and [DynamicImage.getDrawable] to obtain the final [Drawable] output. Or you can
 * provide target [ImageView] class into the [DynamicImage.loadDrawableInto] method.
 *
 * This [DynamicImage] implementation uses [Glide], for other implementations please visit our GitHub.
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

    override suspend fun getDrawable(context: Context): Drawable? {
        imageUri?.let { uri ->
            return BitmapDrawable(
                context.resources,
                Glide.with(context)
                    .asBitmap()
                    .load(uri.image)
                    .submit()
                    .get()
            )
        }

        return super.getDrawable(context)
    }

    override fun getDrawable(context: Context, callback: (drawable: Drawable) -> Unit) {
        imageUri?.let { imageUriComponent ->
            Glide.with(context)
                .asBitmap()
                .load(imageUriComponent.image)
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        callback(BitmapDrawable(context.resources, resource))
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }

        super.getDrawable(context, callback)
    }

    override fun loadDrawableInto(
        imageView: ImageView,
        withCrossFade: Boolean
    ) {
        imageUri?.let { imageUriComponent ->
            Glide.with(imageView.context)
                .load(imageUriComponent.image)
                .apply {
                    if (withCrossFade) transition(DrawableTransitionOptions.withCrossFade())
                    imageUriComponent.placeholderImage?.let { placeholder(it) }
                    imageUriComponent.errorImage?.let { error(it) }
                }
                .into(imageView)
        }

        imageDrawable?.let {
            Glide.with(imageView.context)
                .load(it)
                .apply {
                    if (withCrossFade) transition(DrawableTransitionOptions.withCrossFade())
                }
                .into(imageView)
        }

        imageRes?.let {
            Glide.with(imageView.context)
                .load(it)
                .apply {
                    if (withCrossFade) transition(DrawableTransitionOptions.withCrossFade())
                }
                .into(imageView)
        }
    }
}