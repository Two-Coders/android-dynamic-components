package com.twocoders.dynamic.image.coil

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.twocoders.dynamic.image.BaseDynamicImage
import com.twocoders.dynamic.image.component.UriComponent
import com.twocoders.extensions.common.getDrawable

/**
 *
 * Handy class which can be used to bind image data to views using them.
 * Data can be in [DrawableRes], [Drawable], or [UriComponent] format.
 *
 * Use one of [DynamicImage.from] creator methods to create the data
 * and [DynamicImage.getDrawable] to obtain the final [Drawable] output. Or you can
 * provide target [ImageView] class into the [DynamicImage.loadDrawableInto] method.
 *
 * This [DynamicImage] implementation uses [Coil], for other implementations please visit our GitHub.
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
            val request = ImageRequest.Builder(context)
                .data(uri)
                .build()
            return ImageLoader(context).execute(request).drawable
        }

        imageDrawable?.let { drawable ->
            return drawable
        }

        imageRes?.let { res ->
            return context.getDrawable(drawableResId = res)
        }

        return null
    }

    override fun getDrawable(context: Context, callback: (drawable: Drawable) -> Unit) {
        imageUri?.let { imageUriComponent ->
            val request = ImageRequest.Builder(context)
                .data(imageUriComponent.image)
                .target { callback(it) }
                .build()
            ImageLoader(context).enqueue(request)
        }

        imageDrawable?.let { drawable ->
            callback(drawable)
        }

        imageRes?.let { res ->
            context.getDrawable(drawableResId = res)?.let { callback(it) }
        }
    }

    override fun loadDrawableInto(
        imageView: ImageView,
        withCrossFade: Boolean
    ) {
        imageUri?.let { imageUriComponent ->
            imageView.load(imageUriComponent.image) {
                crossfade(withCrossFade)
                imageUriComponent.placeholderImage?.let { placeholder(it) }
                imageUriComponent.errorImage?.let { error(it) }
            }
        }

        imageDrawable?.let {
            // Note: Coil has currently issue with loading vectors
            if (it is VectorDrawable) {
                imageView.setImageDrawable(it)
            } else {
                imageView.load(it) {
                    crossfade(withCrossFade)
                }
            }
        }

        imageRes?.let {
            // We need to get drawable before setting it to imageView through Coil,
            // there is a problem with background tint from theme attribute
            getDrawable(imageView.context) { drawable ->
                // Note: Coil has currently issue with loading vectors
                if (drawable is VectorDrawable) {
                    imageView.setImageDrawable(drawable)
                } else {
                    imageView.load(drawable) {
                        crossfade(withCrossFade)
                    }
                }
            }
        }
    }
}