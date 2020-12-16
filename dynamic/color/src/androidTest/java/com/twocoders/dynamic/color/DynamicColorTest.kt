package com.twocoders.dynamic.color

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.twocoders.extensions.common.getColorFromAttr
import com.twocoders.extensions.common.getColorInt
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DynamicColorTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun emptyDynamicColorReturnsTransparentColor() {
        Assert.assertEquals(Color.TRANSPARENT, DynamicColor.EMPTY.getColor(context))
    }

    @Test
    fun dynamicColorFromColorInt() {
        @ColorInt val expectedColorInt = Color.YELLOW
        Assert.assertEquals(expectedColorInt, DynamicColor.fromColorInt(expectedColorInt).getColor(context))
    }

    @Test
    fun dynamicColorFromColorRes() {
        @ColorRes val testedColorRes = android.R.color.black
        @ColorInt val expectedColorInt = context.getColorInt(testedColorRes)
        Assert.assertEquals(expectedColorInt, DynamicColor.fromColorRes(testedColorRes).getColor(context))
    }

    @Test
    fun dynamicColorFromAttrRes() {
        @AttrRes val testedColorAttr = android.R.attr.colorAccent
        @ColorInt val expectedColorInt = context.getColorFromAttr(testedColorAttr)
        Assert.assertEquals(expectedColorInt, DynamicColor.fromAttrRes(testedColorAttr).getColor(context))
    }

    @Test
    fun dynamicColorStateList() {
        @ColorInt val testedColorInt = Color.GREEN
        val expectedColorStateList = ColorStateList.valueOf(testedColorInt)
        Assert.assertEquals(expectedColorStateList, DynamicColor.fromColorInt(testedColorInt).getColorStateList(context))
    }
}