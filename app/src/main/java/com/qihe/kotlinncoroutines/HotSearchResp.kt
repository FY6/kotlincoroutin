package com.qihe.kotlinncoroutines

data class HotSearchResp(val hotSongs: List<HotSearchData>)

data class HotSearchData(
        val linktype: Int,
        val linkurl: Int,
        val strong: Int,
        val word: String?
)