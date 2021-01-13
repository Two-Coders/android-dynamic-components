package com.twocoders.dynamic.image.coil

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.twocoders.extensions.common.getDrawable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class DynamicImageCoilTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun emptyDynamicImageReturnsNullDrawable() {
        val emptyDynamicImage = DynamicImage.EMPTY

        emptyDynamicImage.getDrawable(context) { assertNull(it) }

        GlobalScope.launch { assertNull(emptyDynamicImage.getDrawable(context)) }

        val imageViewMock = mock(ImageView::class.java)
        emptyDynamicImage.loadDrawableInto(imageViewMock)
        verify(imageViewMock, never()).setImageDrawable(any())
    }

    @Test
    fun dynamicImageFromDrawableRes() {
        @DrawableRes val testedImageRes = android.R.drawable.ic_delete
        val testedDynamicImage = DynamicImage.from(testedImageRes)
        val expectedDrawable = context.getDrawable(drawableResId = testedImageRes) as BitmapDrawable
        val expectedBitmap = expectedDrawable.bitmap

        testedDynamicImage.getDrawable(context) { assertEquals(expectedBitmap, (it as BitmapDrawable).bitmap ) }

        GlobalScope.launch { assertEquals(expectedBitmap, (testedDynamicImage.getDrawable(context) as BitmapDrawable).bitmap) }

        val imageViewMock = mock(ImageView::class.java)
        `when`(imageViewMock.context).thenReturn(context)
        testedDynamicImage.loadDrawableInto(imageViewMock)
        verify(imageViewMock).setImageDrawable(expectedDrawable)
    }
}