package com.qihe.kotlinncoroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object CoroutinesUtils {
    fun launch(block: () -> Unit) = GlobalScope.launch { block.invoke() }
}