package com.twocoders.dynamic.color

import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.twocoders.extensions.common.NO_ID
import com.twocoders.extensions.common.getColorFromAttr
import com.twocoders.extensions.common.getColorInt

/**
 * Handy class which can be used to bind color data to views using them.
 * Data can be in [ColorInt], [ColorRes], or [AttrRes] integer format.
 *
 * Use one of [DynamicColor.fromColorInt], [DynamicColor.fromColorRes] or [DynamicColor.fromAttrRes]
 * creator methods to create the data and [DynamicColor.getColor] to obtain the final [ColorInt] output
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class DynamicColor : Parcelable {

    @ColorInt protected val colorInt: Int?
    @ColorRes protected val colorRes: Int?
    @AttrRes protected val attrRes: Int?

    protected constructor(
        @ColorInt colorInt: Int? = null,
        @ColorRes colorRes: Int? = null,
        @AttrRes attrRes: Int? = null
    ) {
        this.colorInt = colorInt
        this.colorRes = colorRes
        this.attrRes = attrRes
    }

    protected constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    companion object {

        val EMPTY = DynamicColor()

        @JvmStatic
        fun fromColorInt(@ColorInt colorInt: Int) = DynamicColor(colorInt = colorInt)

        @JvmStatic
        fun fromColorRes(@ColorRes colorRes: Int) = DynamicColor(colorRes = colorRes)

        @JvmStatic
        fun fromAttrRes(@AttrRes attrRes: Int) = DynamicColor(attrRes = attrRes)

        @JvmField
        val CREATOR = object : Parcelable.Creator<DynamicColor> {
            override fun createFromParcel(parcel: Parcel) = DynamicColor(parcel)
            override fun newArray(size: Int): Array<DynamicColor?> = arrayOfNulls(size)
        }
    }

    fun isEmpty() =
        colorInt == null && (colorRes == null || colorRes == NO_ID) && attrRes == null

    fun isNotEmpty() = !isEmpty()

    @ColorInt
    open fun getColor(context: Context): Int {
        colorInt?.let { color ->
            return color
        }

        if (colorRes != null && colorRes != NO_ID) {
            return context.getColorInt(colorRes)
        }

        attrRes?.let { attr ->
            return context.getColorFromAttr(attr)
        }

        return context.getColorInt(android.R.color.transparent)
    }

    open fun getColorStateList(context: Context) = ColorStateList.valueOf(getColor(context))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(colorInt ?: NO_ID)
        parcel.writeInt(colorRes ?: NO_ID)
        parcel.writeInt(attrRes ?: NO_ID)
    }

    override fun describeContents() = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DynamicColor

        if (colorInt != other.colorInt) return false
        if (colorRes != other.colorRes) return false
        if (attrRes != other.attrRes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = colorInt ?: NO_ID
        result = 31 * result + colorRes.hashCode()
        result = 31 * result + attrRes.hashCode()
        return result
    }
}