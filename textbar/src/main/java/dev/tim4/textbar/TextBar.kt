/*
 * The MIT License
 *
 * Copyright (c) 2019 https://www.tim4.dev
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
import dev.tim4.textbar.internal.TextRectangle
import dev.tim4.textbar.internal.TextRectangleData
import dev.tim4.textbar.internal.dpToPx
import dev.tim4.textbar.internal.spToPx

class TextBar : TableLayout {

    private var textSizePx: Int = 0
    private var marginsPx: Int = 0
    private var columns: Int = 0

    private var colorChecked: Int = 0
    private var colorUnchecked: Int = 0
    private var colorStroke: Int = 0

    //original data
    var dataList: List<TextRectangleData> = ArrayList()
    //result checked data
    private var viewList: MutableList<TextRectangle> = ArrayList()



    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, _attrs: AttributeSet) : super(context, _attrs) {
        initView(_attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        View.inflate(context, R.layout.custom_text_bar, this)

        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.TextBar,
                0, 0)

        textSizePx = typedArray.getDimensionPixelSize(R.styleable.TextBar_textBarSizeSp, TEXT_SIZE_DEFAULT_DP.spToPx)
        marginsPx = typedArray.getDimensionPixelSize(R.styleable.TextBar_textBarMarginsDp, MARGINS_DEFAULT_DP.dpToPx)
        columns = typedArray.getInteger(R.styleable.TextBar_textBarColumns,
            COLUMNS_COUNT_DEFAULT
        )
        colorChecked = typedArray.getColor(R.styleable.TextBar_textBarColorChecked, Color.GREEN)
        colorUnchecked = typedArray.getColor(R.styleable.TextBar_textBarColorUnchecked, Color.TRANSPARENT)
        colorStroke = typedArray.getColor(R.styleable.TextBar_textBarColorStroke, Color.GRAY)

        if (dataList.isEmpty()) stub()

        typedArray.recycle()
    }

    private fun stub() {
        val row = TableRow(context)
        for (i in 0..4) {
            val textView = TextRectangle(
                context,
                TextRectangleData("t$i"),
                textSizePx,
                marginsPx,
                colorChecked,
                colorUnchecked,
                colorStroke
            ) { /* nothing */ }
            row.addView(textView)
        }
        addView(row)
    }

    fun drawTextBar(_dataList: List<TextRectangleData>,
                    _onClickListener: (TextRectangleData) -> Unit = {} // external callback
    ) {
        this.removeAllViews()

        this.dataList = _dataList
        this.viewList = ArrayList(_dataList.size)

        var countCols = 0

        var row = TableRow(context)
        for (data in _dataList) {

            // create text rectangle
            val textView =
                TextRectangle(
                    context,
                    data,
                    textSizePx,
                    marginsPx,
                    colorChecked,
                    colorUnchecked,
                    colorStroke
                ) { textData ->
                    // radio button behavior
                    val idx = dataList.indexOf(textData)
                    for (i in dataList.indices) {
                        if (i != idx) {
                            dataList[i].isChecked = false
                            viewList[i].setChecked(false)
                        }
                    }

                    // call external callback
                    _onClickListener.invoke(textData)
                }

            viewList.add(textView)

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
    }

    /**
     * Validate.
     * @return true if selected
     */
    override fun isSelected(): Boolean {
        var result = false
        for (data in dataList) {
            if (data.isChecked) {
                result = true
                break
            }
        }
        return result
    }

    companion object {
        private const val COLUMNS_COUNT_DEFAULT = 5
        private const val TEXT_SIZE_DEFAULT_DP = 20
        private const val MARGINS_DEFAULT_DP = 4
    }
}