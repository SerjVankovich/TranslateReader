package com.example.translatereader.Utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.translatereader.R
import com.example.translatereader.Retrofit.API
import com.example.translatereader.Retrofit.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.LayoutInflater
import android.view.ViewGroup


class ClickedTextView(private val textView: TextView, private val context: Context, val view: ViewGroup) {

    fun setClicker() {
        val BASE_URL = "https://translate.yandex.net/"
        val KEY = "trnsl.1.1.20190709T081329Z.68bb8cf57479d9c6.9150ca0edebd3a3a1e9392e9550bee675ff5e72d"
        textView.setOnClickListener {
            val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api : API = retrofit.create(API::class.java)

            val call = api.translate(KEY, textView.text as String)

            call.enqueue(object: Callback<ResponseBody> {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val translate = response.body()
                    buildDialog(translate!!.text[0])
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("Fail", t.message ?: "Error")                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun buildDialog(translate: String) {
        val builder = AlertDialog.Builder(context)
        val view1 = LayoutInflater.from(context).inflate(R.layout.dialog, view, false)
        val tv = view1.findViewById<TextView>(R.id.translated)
        tv.text = translate
        builder.setView(view1)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(0))
    }
}