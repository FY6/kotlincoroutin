package com.qihe.kotlinncoroutines

import kotlinx.coroutines.Deferred
import retrofit2.http.POST

interface Api {
    @POST("APP/homepage/hot_search")
    fun searchHot(): Deferred<BaseResp<HotSearchResp>>
}