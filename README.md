
[![](https://jitpack.io/v/tim4dev/textbar.svg)](https://jitpack.io/#tim4dev/textbar)

# Text Bar Selector

A simple text choice for your e-commerce store client. Written in Kotlin.

`minSdkVersion 16`

![Text Bar in Action (picture).](https://res.cloudinary.com/ddhl2pupw/image/upload/v1571757921/library-textbar/Screenshot_1.png)


[![Text Bar in Action (youtube).](https://img.youtube.com/vi/AJa6G4DF41s/0.jpg)](https://www.youtube.com/watch?v=AJa6G4DF41s)

# Gradle

    allprojects {
        repositories {
    	    maven { url 'https://jitpack.io' }
    	}
    }


    dependencies {
        implementation "com.github.tim4dev:textbar:VERSION"
    }    
            
    
    

# Usage

## XML Layout

    <dev.tim4.textbar.TextBar
            android:id="@+id/textBarDemo"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
    
            app:textBarColorChecked="#4FFF2C"
            app:textBarColorStroke="#999999"
            app:textBarColorUnchecked="#fff"
            app:textBarColumns="7"
            app:textBarMarginsDp="2dp"
            app:textBarSizeSp="20sp" />

## Kotlin example

    val texts = listOf(
                TextRectangleData("S", true),
                TextRectangleData("L"),
                TextRectangleData("XL"),
                TextRectangleData("XXL"),
                TextRectangleData("39")
            )
    textBarDemo.drawTextBar(texts) { data -> Log.d(TAG, "onClickEvent. data = $data") }

# See also

[A simple color choice for your e-commerce store client.](https://github.com/tim4dev/colorbar)    

# License

The MIT License

Copyright (c) 2019 tim4dev https://www.tim4.dev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
