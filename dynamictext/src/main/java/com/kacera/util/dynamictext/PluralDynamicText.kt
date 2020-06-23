package com.kacera.util.dynamictext

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.annotation.PluralsRes

@SuppressLint("ResourceType")
internal class PluralDynamicText : DynamicText {

    private val quantity: Quantity

    internal constructor(
        @PluralsRes textResource: Int,
        quantity: Quantity,
        vararg args: Any? = emptyArray()
    ) : super(textResource, *args) {
        this.quantity = quantity
    }

    private constructor(parcel: Parcel) : super(parcel) {
        this.quantity = parcel.readParcelable(Quantity::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeParcelable(quantity, flags)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<PluralDynamicText> {
            override fun createFromParcel(parcel: Parcel): PluralDynamicText {
                return PluralDynamicText(parcel)
            }

            override fun newArray(size: Int): Array<PluralDynamicText?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun getText(context: Context): CharSequence {
        val quantityArgs = if (quantity.useInText) {
            arrayOf(quantity.number, *args)
        } else {
            args
        }

        if (textResource != View.NO_ID && quantityArgs.isNotEmpty()) {
            return context.resources.getQuantityString(textResource, quantity.number, *quantityArgs)
        }

        return context.resources.getQuantityText(textResource, quantity.number)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as PluralDynamicText

        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + quantity.hashCode()
        return result
    }
}