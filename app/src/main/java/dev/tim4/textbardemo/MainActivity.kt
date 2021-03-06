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

package dev.tim4.textbardemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.tim4.textbar.TextBar
import dev.tim4.textbar.internal.TextRectangleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textBarDemo1 = findViewById<TextBar>(R.id.textBarDemo1)
        val textBarDemo2 = findViewById<TextBar>(R.id.textBarDemo2)

        val log1 = findViewById<TextView>(R.id.textViewLog1)
        val log2 = findViewById<TextView>(R.id.textViewLog2)

        textBarDemo1.setupTextBar(TEXTS_1)

        // NOTE. GlobalScope is used here only as an example app.
        GlobalScope.launch(Dispatchers.Main) {
            textBarDemo1.valueFlow.collect { data ->
                Log.d(TAG, "collect1 = $data")
                log1.text = "${data.text}, ${data.isChecked}, ${data.tag}"
            }
        }

        textBarDemo2.setupTextBar(TEXTS_2)

        // NOTE. GlobalScope is used here only as an example app.
        GlobalScope.launch(Dispatchers.Main) {
            textBarDemo2.valueFlow.collect { data ->
                Log.d(TAG, "collect2 = $data")
                log2.text = "${data.text}, ${data.isChecked}, ${data.tag}"
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"

        val TEXTS_1 = listOf(
            TextRectangleData(text = "S", tag = 111),
            TextRectangleData(text = "L", isChecked = true, tag = 222),
            TextRectangleData(text = "XL", tag = 333),
            TextRectangleData(text = "XXL", tag = 444),
            TextRectangleData(text = "39", tag = 555)
        )

        val TEXTS_2 = listOf(
            TextRectangleData(text = "text 1", tag = "tag_1"),
            TextRectangleData(text = "text 2", tag = "tag_2"),
            TextRectangleData(text = "text 3", tag = "tag_3"),
            TextRectangleData(text = "text 4", tag = "tag_4"),
            TextRectangleData(text = "text any 5", tag = "tag_5"),
            TextRectangleData(text = "text 6", tag = "tag_6"),
            TextRectangleData(text = "text long 7", tag = "tag_7"),
            TextRectangleData(text = "text 8", tag = "tag_8"),
            TextRectangleData(text = "text 9", tag = "tag_9")
        )
    }
}
