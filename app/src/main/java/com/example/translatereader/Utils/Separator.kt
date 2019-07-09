package com.example.translatereader.Utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.BufferedReader

@RequiresApi(Build.VERSION_CODES.N)
class Separator(var reader: BufferedReader) {
    var index = 0
    @RequiresApi(Build.VERSION_CODES.N)
    var chunks: List<List<String>>

    init {
        val lines: MutableList<String> = mutableListOf()
        reader.lines().forEach{
            lines.add(it)
        }
        chunks = lines.chunked(15)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun toLines(): List<List<String>> {
        val lines: MutableList<MutableList<String>> = mutableListOf()
        chunks[index].forEach { line ->
            val i = findSpace(line as String)
            val subLines = line.chunked(i + 1)

            subLines.forEach {
                val wordsLine = it.split(" ")
                lines.add(wordsLine as MutableList<String>)
            }

        }
        index++
        return lines
    }

    private fun findSpace(line: String): Int {
        if (line.length < 60) {
            return 59
        }
        var i = 59
        while(true) {
            if (line[i].equals(' ')) {
                Log.d("index", i.toString())
                return i
            }
            i--
        }
    }
}