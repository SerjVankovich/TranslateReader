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
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.translatereader.Utils.ClickedTextView
import com.example.translatereader.Utils.Separator


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get width in dp
        val displayMetrics = this.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density

        // reading book
        val stream = this.resources.openRawResource(R.raw.test_book)
        val reader = InputStreamReader(stream)
        val buffer = BufferedReader(reader as Reader?)

        val separator = Separator(buffer)
        var lines = separator.toLines()


        // make inflater for code add elements
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.activity_main, null)

        val root = view.findViewById<LinearLayout>(R.id.emptyLayout)


        //fill words
        fillWords(lines, view, root)

        val nextPage = Button(this)
        nextPage.height = 100
        nextPage.width = 100
        nextPage.text = ">"

        nextPage.setOnClickListener {
            root.removeAllViews()
            lines = separator.toLines()
            fillWords(lines, view, root)
            root.addView(nextPage)
        }

        root.addView(nextPage)
        setContentView(view)
    }

    fun fillWords(lines: List<List<String>>, view: View, root: LinearLayout) {
        lines.forEach {line ->
            val ll = LinearLayout(this)
            ll.orientation = LinearLayout.HORIZONTAL
            line.forEach() {
                val tv = TextView(this)
                tv.text = "$it "
                val clickedTextView = ClickedTextView(tv, this, view as ViewGroup)
                clickedTextView.setClicker()
                ll.addView(tv)
            }

            root.addView(ll)
        }
    }
}
