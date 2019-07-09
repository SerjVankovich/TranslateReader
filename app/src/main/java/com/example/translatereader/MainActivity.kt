package com.example.translatereader

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import android.util.DisplayMetrics
import android.view.Display



class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get width in dp
        val displayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        // reading book
        val lines: MutableList<MutableList<String>> = mutableListOf()
        val stream = this.resources.openRawResource(R.raw.test_book)
        val reader = InputStreamReader(stream)
        val buffer = BufferedReader(reader as Reader?)

        buffer.lines().forEach { line ->
            val wordsLine = line.split(" ")
            lines.add(wordsLine as MutableList<String>)
        }


        // make inflater for code add elements
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.activity_main, null)

        val root = view.findViewById<LinearLayout>(R.id.emptyLayout)


        //fill words
        lines.forEach {line ->
            val ll = LinearLayout(this)
            ll.orientation = LinearLayout.HORIZONTAL
            line.forEach() {
                val tv = TextView(this)
                tv.text = "$it "
                ll.addView(tv)
            }

            root.addView(ll)
        }

        setContentView(view)
    }
}
