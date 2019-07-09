package com.example.translatereader.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("api/v1.5/tr.json/translate")
    fun translate(@Query("key") key: String,
                  @Query("text") text: String,
                  @Query("lang") lang: String = "en-ru"): Call<ResponseBody>
}