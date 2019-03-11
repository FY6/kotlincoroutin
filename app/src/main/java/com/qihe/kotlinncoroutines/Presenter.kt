package com.qihe.kotlinncoroutines

import android.util.Log
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class Presenter {

    private val retrofit by lazy {
        Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://118.31.36.50/")
                .build()
    }

    fun getData() = GlobalScope.launch {
        try {
            Log.e("tag", "${Thread.currentThread().name}")
            val baseResp = retrofit.create(Api::class.java).searchHot().await()
            Log.e("tag", "${baseResp}")
        } catch (ex: Exception) {

        }

    }
}