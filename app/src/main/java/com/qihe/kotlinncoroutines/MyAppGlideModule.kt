package com.qihe.kotlinncoroutines

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class MyAppGlideModule : AppGlideModule() {

    private val DISK_CACHE_SIZE = 500 * 1024 * 1024L

    /**
     * 更Glide改配置
     */
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context, DISK_CACHE_SIZE))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }
}