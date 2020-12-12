package com.twocoders.util.dynamictext

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View.NO_ID
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

/**
 * Handy class which can be used to bind text data to views displaying them.
 * Data can be in [String] format, [StringRes] integer format or a combination of these.
 *
 * Use one of [DynamicText.from] creator methods to create the data and [DynamicText.getText] to
 * obtain the final text output
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class DynamicText : Parcelable {

    @StringRes
    protected val textResource: Int

    protected var args: Array<out Any?>

    protected constructor(
        @StringRes textResource: Int = NO_ID,
        vararg args: Any? = emptyArray()
    ) {
        this.textResource = textResource
        this.args = args
    }

    protected constructor(parcel: Parcel) : this(
        parcel.readInt(),
        *(parcel.readArray(ClassLoader.getSystemClassLoader()) ?: emptyArray())
    )

    companion object {

        val EMPTY = DynamicText()

        @JvmStatic
        fun from(@StringRes resId: Int) = DynamicText(resId)

        @JvmStatic
        fun from(text: String) = DynamicText(NO_ID, text)

        @JvmStatic
        fun from(format: String, vararg args: Any) = from(String.format(format, *args))

        @JvmStatic
        fun from(@StringRes resId: Int, text: String) = DynamicText(resId, text)

        @JvmStatic
        fun from(@StringRes resId: Int, number: Number) = DynamicText(resId, number)

        @JvmStatic
        fun from(@StringRes resId: Int, vararg args: Any) = DynamicText(resId, *args)

        @JvmStatic
        fun plural(@PluralsRes resId: Int, quantity: Quantity, vararg args: Any): DynamicText =
            PluralDynamicText(resId, quantity, *args)

        @JvmField
        val CREATOR = object : Parcelable.Creator<DynamicText> {
            override fun createFromParcel(parcel: Parcel): DynamicText {
                return DynamicText(parcel)
            }

            override fun newArray(size: Int): Array<DynamicText?> {
                return arrayOfNulls(size)
            }
        }
    }

    open fun getText(context: Context): CharSequence {
        if (textResource != NO_ID && args.isNotEmpty()) {
            return context.getString(textResource, *args)
        }

        if (textResource != NO_ID) {
            return context.getText(textResource)
        }

        return args.joinToString(separator = " ") { it.toString() }
    }

    fun isEmpty() = textResource == NO_ID && args.isEmpty()
    fun isNotEmpty() = !isEmpty()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(textResource)
        parcel.writeArray(args)
    }

    override fun describeContents(): Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DynamicText

        if (textResource != other.textResource) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = textResource
        result = 31 * result + args.contentHashCode()
        return result
    }
}