package com.qihe.kotlinncoroutines

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class AsynContext(key: CoroutineContext.Key<*>) : AbstractCoroutineContextElement(key) {

}