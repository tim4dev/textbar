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

package dev.tim4.textbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.withStyledAttributes
import dev.tim4.textbar.internal.TextRectangle
import dev.tim4.textbar.internal.TextRectangleData
import dev.tim4.textbar.internal.dpToPx
import dev.tim4.textbar.internal.spToPx
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextBar : TableLayout, ITextBar {

    private val layoutId: Int get() = R.layout.textbar_layout

    private lateinit var _valueFlow: MutableStateFlow<TextRectangleData>
    override lateinit var valueFlow: StateFlow<TextRectangleData>

    private var textSizePx: Int = 0
    private var marginsPx: Int = 0
    private var columns: Int = 0

    private var textColor: Int = 0
    private var colorChecked: Int = 0
    private var colorUnchecked: Int = 0
    private var colorStroke: Int = 0

    private var childs: MutableList<TextRectangle> = ArrayList()

    init {
        View.inflate(context, layoutId, this)
    }

    constructor(context: Context) : super(context) {
        initLayout(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout(context, attrs)
    }

    private fun initLayout(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.TextBar) {
            textSizePx = getDimensionPixelSize(
                R.styleable.TextBar_textBarSizeSp,
                TEXT_SIZE_DEFAULT_DP.spToPx
            )
            marginsPx = getDimensionPixelSize(
                R.styleable.TextBar_textBarMarginsDp,
                MARGINS_DEFAULT_DP.dpToPx
            )
            columns = getInteger(
                R.styleable.TextBar_textBarColumns,
                1
            )
            textColor = getColor(R.styleable.TextBar_textBarTextColor, Color.BLACK)
            colorChecked = getColor(R.styleable.TextBar_textBarColorChecked, Color.GREEN)
            colorUnchecked =
                getColor(R.styleable.TextBar_textBarColorUnchecked, Color.TRANSPARENT)
            colorStroke = getColor(R.styleable.TextBar_textBarColorStroke, Color.GRAY)
        }

        if (isInEditMode) stub()
    }

    private fun stub() {
        val row = TableRow(context)
        for (i in 0 until columns) {
            val textView = TextRectangle(
                context = context,
                data = TextRectangleData(
                    text = "t$i",
                    isChecked = (i == 0)
                ),
                textSizePx = textSizePx,
                marginsPx = marginsPx,
                textColor = textColor,
                colorChecked = colorChecked,
                colorUnchecked = colorUnchecked,
                colorStroke = colorStroke
            )
            row.addView(textView)
        }
        addView(row)
    }

    override fun setupTextBar(dataList: List<TextRectangleData>) {
        this.removeAllViews()

        childs = ArrayList(dataList.size)

        var countCols = 0

        var row = TableRow(context)
        dataList.forEach { data ->
            // create text rectangle
            val textView =
                TextRectangle(
                    context = context,
                    data = data,
                    textSizePx = textSizePx,
                    marginsPx = marginsPx,
                    textColor = textColor,
                    colorChecked = colorChecked,
                    colorUnchecked = colorUnchecked,
                    colorStroke = colorStroke
                ) { view, data1 ->
                    // onClick
                    emitValue(view, data1)
                }

            childs.add(textView)

            row.addView(textView)

            countCols++

            if (countCols >= columns) {
                // new row
                addView(row)
                row = TableRow(context)
                countCols = 0
            }
        }
        addView(row)

        initStateFlow()
    }

    private fun initStateFlow() {
        val first = childs.find {
            it.getData().isChecked
        } ?: childs[0]
        _valueFlow = MutableStateFlow(first.getData())
        valueFlow = _valueFlow
    }

    private fun emitValue(view: TextRectangle, data: TextRectangleData) {
        // radio button behavior
        childs.forEach { child ->
            if (child != view) {
                child.toggleChecked(false)
            } else {
                child.toggleChecked(data.isChecked)
            }
        }
        // emit
        _valueFlow.value = data
    }

    companion object {
        private const val TEXT_SIZE_DEFAULT_DP = 20
        private const val MARGINS_DEFAULT_DP = 4
    }
}
