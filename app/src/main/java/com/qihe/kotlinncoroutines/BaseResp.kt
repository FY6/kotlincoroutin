package com.qihe.kotlinncoroutines

/**
 * Describe: 统一响应体数据，正常请求服务器，返回的数据
 * Author: wfy
 *
 *company :
 */
data class BaseResp<T>(val errCode: Int, val errMsg: String, val data: T)