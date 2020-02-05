package com.kacera.util.dynamictext

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View.NO_ID
import androidx.annotation.StringRes

private const val EMPTY_STRING = ""

/**
 * Handy class which can be used to bind text data to views displaying them.
 * Data can be in [String] format, [StringRes] integer format or a combination of these.
 *
 * Use one of [DynamicText.from] creator methods to create the data and [DynamicText.getText] to
 * obtain the final text output
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class DynamicText private constructor(
    @StringRes private var textResource: Int = NO_ID,
    private var textString: String = EMPTY_STRING
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().orEmpty()
    )

    companion object {

        val EMPTY = DynamicText()

        @JvmStatic
        fun from(@StringRes resId: Int): DynamicText {
            return DynamicText(textResource = resId)
        }

        @JvmStatic
        fun from(text: String): DynamicText {
            return DynamicText(textString = text)
        }

        @JvmStatic
        fun from(@StringRes resId: Int, text: String): DynamicText {
            return DynamicText(textResource = resId, textString = text)
        }

        @JvmStatic
        fun from(@StringRes resId: Int, number: Int): DynamicText {
            return DynamicText(textResource = resId, textString = number.toString())
        }

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

    open fun getText(context: Context): String {
        if (textResource != NO_ID && textString.isNotEmpty()) {
            return String.format(context.getString(textResource), textString)
        }

        if (textResource != NO_ID) {
            return context.getString(textResource)
        }

        return textString
    }

    fun isEmpty() = textResource == NO_ID && textString == EMPTY_STRING
    fun isNotEmpty() = !isEmpty()
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(textResource)
        parcel.writeString(textString)
    }

    override fun describeContents(): Int {
        return 0
    }
}