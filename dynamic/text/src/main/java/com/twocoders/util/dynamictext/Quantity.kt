package com.twocoders.util.dynamictext

import android.os.Parcel
import android.os.Parcelable
import androidx.core.os.ParcelCompat

/**
 * Class representing quantity in [DynamicText]'s plural form.
 *
 * For plurals without formatting arguments use `useInText` `false`
 * ```
 *  <plurals name="months">
 *   <item quantity="one">month</item>
 *   <item quantity="other">months</item>
 * </plurals>
 *```
 * For plurals which text should contain quantity number use `useInText` `true` to obtain complete string e.g. "2 months"
 * ```
 * <plurals name="months">
 *   <item quantity="one">%d month</item>
 *   <item quantity="other">%d months</item>
 * </plurals>
 * ```
 *
 * @param number used to get the correct string for the current language's plural rules.
 * @param useInText `true` if number is also part of the strings formatting arguments, `false` otherwise
 */
data class Quantity(val number: Int, val useInText: Boolean = false) : Parcelable {

    private constructor(parcel: Parcel) : this(
        parcel.readInt(),
        ParcelCompat.readBoolean(parcel)
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(number)
        ParcelCompat.writeBoolean(dest, useInText)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Quantity> {
            override fun createFromParcel(parcel: Parcel): Quantity {
                return Quantity(parcel)
            }

            override fun newArray(size: Int): Array<Quantity?> {
                return arrayOfNulls(size)
            }
        }
    }
}