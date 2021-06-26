
[![](https://jitpack.io/v/tim4dev/textbar.svg)](https://jitpack.io/#tim4dev/textbar)

# Text Bar Selector

A simple text choice for your e-commerce store client. Written in Kotlin + coroutines.

 - `minSdkVersion 16`
 - AndroidX

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
        TextRectangleData(text = "S", tag = 111),
        TextRectangleData(text = "L", isChecked = true, tag = 222),
        TextRectangleData(text = "XL", tag = 333),
        TextRectangleData(text = "XXL", tag = 444),
        TextRectangleData(text = "39", tag = 555)
    )
    val textBarDemo = findViewById<TextBar>(R.id.textBarDemo)
    textBarDemo.setupTextBar(texts)

    // NOTE. GlobalScope is used here only as an example app.
    GlobalScope.launch(Dispatchers.Main) {
        textBarDemo.valueFlow.collect { data ->
            Log.d(TAG, "collect = $data")
            log.text = "${data.text}, ${data.isChecked}, ${data.tag}"
        }
    }

# See also

[A simple color choice for your e-commerce store client.](https://github.com/tim4dev/colorbar)    
