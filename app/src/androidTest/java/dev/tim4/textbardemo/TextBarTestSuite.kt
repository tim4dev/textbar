/*
 * The MIT License
 *
 * Copyright (c) 2021 https://www.tim4.dev
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

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.text.KTextView
import org.junit.Rule
import org.junit.Test

class TextBarTestSuite : TestCase() {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun textbarTest() {
        run {
            step("Check Main Screen") {
                MainScreen {
                    textBar1 {
                        isVisible()
                    }
                    textBar2 {
                        isVisible()
                    }
                }
            }
            step("Checked Text Bar") {
                MainScreen {
                    MainActivity.TEXTS_1.forEach { data ->
                        textBar1 {
                            val bar = KTextView {
                                withId(R.id.textbarTextRectangle)
                                withText(data.text)
                            }
                            bar { click() }
                        }
                        log1 {
                            hasText("${data.text}, true, ${data.tag}")
                        }
                    }
                }
            }
            step("UnChecked Text Bar") {
                MainScreen {
                    val data = MainActivity.TEXTS_1.last()
                    textBar1 {
                        val bar = KTextView {
                            withId(R.id.textbarTextRectangle)
                            withText(data.text)
                        }
                        bar { click() }
                    }
                    log1 {
                        hasText("${data.text}, false, ${data.tag}")
                    }
                }
            }
        }
    }
}
