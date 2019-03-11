package com.qihe.kotlinncoroutines

import android.annotation.SuppressLint
import android.util.Log
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.annotation.GlideOption
import jp.wasabeef.glide.transformations.BlurTransformation


@GlideExtension
object MyGlideExtension {
    @SuppressLint("CheckResult")
    @JvmStatic
    @GlideOption
    fun cacheSource(options: RequestOptions) {
        Log.e("tag", "cacheSource")
        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }


    @JvmStatic
    @SuppressLint("CheckResult")
    @GlideOption
    fun blurImage(options: RequestOptions, radius: Int, sampling: Int) {
        options.transform(BlurTransformation(radius, sampling))
    }
}