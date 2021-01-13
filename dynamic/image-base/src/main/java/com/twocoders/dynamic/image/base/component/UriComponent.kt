package com.twocoders.dynamic.image.base.component

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.twocoders.extensions.common.NO_ID

data class UriComponent(
    val image: Uri,
    @DrawableRes val errorImage: Int? = null,
    @DrawableRes val placeholderImage: Int? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Uri::class.java.classLoader)!!,
        parcel.readInt(),
        parcel.readInt()
    )

    companion object CREATOR : Parcelable.Creator<UriComponent> {
        override fun createFromParcel(parcel: Parcel) = UriComponent(parcel)
        override fun newArray(size: Int): Array<UriComponent?> = arrayOfNulls(size)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(image, flags)
        parcel.writeInt(errorImage ?: NO_ID)
        parcel.writeInt(placeholderImage ?: NO_ID)
    }

    override fun describeContents() = 0
}