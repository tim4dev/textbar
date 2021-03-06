/*
 * The MIT License
 *
 * Copyright (c) 2019, 2021 https://www.tim4.dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package dev.tim4.textbar.internal

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import dev.tim4.textbar.R

/**
 * Creates Rectangle of a specified text. Fill background color if checked.
 */
class TextRectangle : FrameLayout {

    private val layoutId: Int get() = R.layout.textbar_item_view

    @Suppress("JoinDeclarationAndAssignment")
    private val layout: View

    private val textView: TextView

    private lateinit var data: TextRectangleData

    private var textSizePx: Int = 0
    private var marginsPx: Int = 0
    private var textColor: Int = 0
    private var colorChecked: Int = 0
    private var colorUnchecked: Int = 0
    private var colorStroke: Int = 0

    init {
        layout = View.inflate(context, layoutId, this)
        textView = findViewById(R.id.textbarTextRectangle)
    }

    /**
     * Create an instance programmatically
     */
    @Suppress("LongParameterList", "MagicNumber")
    constructor(
        context: Context,
        data: TextRectangleData,
        textSizePx: Int,
        marginsPx: Int,
        textColor: Int = Color.BLACK,
        colorChecked: Int = Color.GREEN,
        colorUnchecked: Int = Color.TRANSPARENT,
        colorStroke: Int = Color.GRAY,
        onClickListener: (TextRectangle, TextRectangleData) -> Unit = { _, _ -> }
    ) : super(context) {

        this.data = data.copy()
        this.textSizePx = textSizePx
        this.marginsPx = marginsPx
        this.textColor = textColor
        this.colorChecked = colorChecked
        this.colorUnchecked = colorUnchecked
        this.colorStroke = colorStroke

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSizePx.toFloat())
        textView.text = this.data.text
        textView.setTextColor(textColor)

        val paddingHorizontal = this.textSizePx / 2
        val paddingVertical = this.textSizePx / 4
        textView.setPadding(
            paddingHorizontal,
            paddingVertical,
            paddingHorizontal,
            paddingVertical
        )

        // onclick listener
        layout.setOnClickListener {
            val checked = !this.data.isChecked
            this.data = this.data.copy(isChecked = checked)
            showChecked(checked)
            onClickListener.invoke(this, this.data)
        }

        showChecked(data.isChecked)

        initView()
    }

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        // nothing
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // set margins
        if (layoutParams is MarginLayoutParams) {
            val mewLayoutParams = layoutParams as MarginLayoutParams
            mewLayoutParams.setMargins(marginsPx, marginsPx, marginsPx, marginsPx)
            layout.layoutParams = mewLayoutParams
        }
    }

    fun toggleChecked(isChecked: Boolean) {
        data = data.copy(isChecked = isChecked)
        showChecked(isChecked)
    }

    fun getData() = data

    private fun showChecked(isChecked: Boolean) {
        if (isChecked) {
            textView.background = makeBackground(colorChecked, colorStroke)
        } else {
            textView.background = makeBackground(colorUnchecked, colorStroke)
        }
    }

    private fun makeBackground(colorFill: Int, colorStroke: Int) = GradientDrawable().apply {
        setColor(colorFill)
        cornerRadius = CORNER_RADIUS_DEFAULT
        setStroke(STROKE_WIDTH_DEFAULT, colorStroke)
    }

    companion object {
        const val CORNER_RADIUS_DEFAULT = 60.0f
        const val STROKE_WIDTH_DEFAULT = 5
    }

}
