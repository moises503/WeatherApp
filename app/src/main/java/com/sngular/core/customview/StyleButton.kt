package com.sngular.core.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.sngular.wheatherapp.R


class StyleButton : AppCompatButton {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { init(context, it) }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.let { init(context, it) }
    }

    @SuppressLint("Recycle")
    fun init(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StyleButton)
        val fontName = typedArray.getString(R.styleable.StyleButton_btnTypeFont)
        setFontStyLe(fontName)
    }

    private fun setFontStyLe(nameFont: String?) {
        var pathFont = ""
        when (nameFont) {
            "1" -> pathFont = "fonts/GoogleSans-Bold.ttf"
            "2" -> pathFont = "fonts/GoogleSans-Medium.ttf"
            "3" -> pathFont = "fonts/GoogleSans-Regular.ttf"
            "4" -> pathFont = "fonts/roboto_light.ttf"
            "5" -> pathFont = "fonts/CircularStd-Bold.otf"
        }
        val typeface = Typeface.createFromAsset(context.assets, pathFont)
        setTypeFace(typeface, this)
    }

    private fun setTypeFace(typeFace: Typeface, view: View) {
        when (view) {
            is TextView -> view.typeface = typeFace
            is EditText -> (view as TextView).typeface = typeFace
            is Button -> (view as TextView).typeface = typeFace
        }

    }

}