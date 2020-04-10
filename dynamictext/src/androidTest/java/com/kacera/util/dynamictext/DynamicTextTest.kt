package com.kacera.util.dynamictext

import android.content.Context
import android.os.Parcel
import android.text.Spanned
import android.view.View.NO_ID
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kacera.util.dynamictext.test.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DynamicTextTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun emptyDynamicTextReturnsEmptyText() {
        Assert.assertEquals("", DynamicText.EMPTY.getText(context))
    }

    @Test
    fun dynamicTextWitTextOnlyDataReturnsSameText() {
        val expectedText = "Success Text"
        Assert.assertEquals(expectedText, DynamicText.from(expectedText).getText(context))
    }

    @Test
    fun dynamicTextWithStringFormatReturnsFormattedText() {
        val expectedText = "Formatted Text with 45 and 0.3450 and everything"
        Assert.assertEquals(expectedText, DynamicText.from("Formatted Text with %d and %.4f and %s", 45, 0.345, "everything").getText(context))
    }

    @Test
    fun dynamicTextWithMultipleTextDataReturnsJoinedText() {
        val expectedText = "Success Text"
        Assert.assertEquals(expectedText, DynamicText.from(NO_ID ,"Success", "Text").getText(context))
    }

    @Test
    fun dynamicTextWithResourceDataReturnsCorrectText() {
        val expectedText = "Simple test string"
        Assert.assertEquals(expectedText, DynamicText.from(R.string.simple_string).getText(context))
    }

    @Test
    fun dynamicTextWithNumberDataReturnsCorrectText() {
        val intNumber = 500
        val doubleNumber = 234.99

        Assert.assertEquals("Number: $intNumber", DynamicText.from(R.string.string_with_int_variable, intNumber).getText(context))
        Assert.assertEquals("Number: $doubleNumber", DynamicText.from(R.string.string_with_decimal_variable, doubleNumber).getText(context))
    }

    @Test
    fun dynamicTextWithMixedDataReturnsCorrectText() {
        val variable1 = "Variable 1 text"
        val variable2 = "Variable 2 text"
        val variable3 = 500

        Assert.assertEquals("Text: $variable1", DynamicText.from(R.string.string_with_string_variable, variable1).getText(context))
        Assert.assertEquals("Text: $variable1 $variable2 $variable3", DynamicText.from(R.string.string_with_multiple_variables, variable1, variable2, variable3).getText(context))
    }

    @Test
    fun dynamicTextWithHtmlContent() {
        Assert.assertTrue(DynamicText.from(R.string.html_unescaped).getText(context) is Spanned)
        Assert.assertFalse(DynamicText.from(R.string.html_escaped).getText(context) is Spanned)
    }

    @Test
    fun htmlContentBinding() {
        val textView = TextView(context)
        setHtmlTextHolder(textView, DynamicText.from(R.string.html_escaped))
        Assert.assertTrue(textView.text is Spanned)

        setHtmlTextHolder(textView, DynamicText.from(R.string.html_unescaped))
        Assert.assertTrue(textView.text is Spanned)

        setHtmlTextHolder(textView, DynamicText.from(R.string.no_html))
        Assert.assertFalse(textView.text is Spanned)
    }

    @Test
    fun recreateDynamicTextFromParcel() {
        val variable1 = "Variable 1 text"
        val variable2 = "Variable 2 text"
        val variable3 = 500

        val p1 = Parcel.obtain()
        val p2 = Parcel.obtain()

        val text1 = DynamicText.from(R.string.string_with_multiple_variables, variable1, variable2, variable3)

        val bytes = with(p1) {
            text1.writeToParcel(this, 0)
            marshall()
        }

        p2.unmarshall(bytes, 0, bytes.size)
        p2.setDataPosition(0)

        val text2 = DynamicText.CREATOR.createFromParcel(p2)

        Assert.assertEquals(text1, text2)
        Assert.assertEquals(text1.getText(context), text2.getText(context))
    }
}