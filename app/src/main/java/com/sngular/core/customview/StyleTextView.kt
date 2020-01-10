package com.sngular.core.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.sngular.wheatherapp.R

class StyleTextView : AppCompatTextView {

    private var capitals = false

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


    @SuppressLint("Recycle", "CustomViewStyleable")
    fun init(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StyleTextView)
        val fontName = typedArray.getString(R.styleable.StyleTextView_txtTypeFont)
        capitals = typedArray.getBoolean(R.styleable.StyleTextView_txtCapitals, false)
        if (capitals) {
            setTextIsUpper(true, this)
        } else {
            setTextIsUpper(false, this)
        }
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
            "6" -> pathFont = "fonts/butterfly.otf"
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

    private fun setTextIsUpper(value: Boolean, view: View) {
        when (view) {
            is TextView -> view.isAllCaps = value
            is EditText -> view.isAllCaps = value
            is Button -> view.isAllCaps = value
        }

    }

}