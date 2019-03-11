package com.qihe.kotlinncoroutines

import android.util.Log
import kotlinx.coroutines.awaitAll
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import java.util.concurrent.locks.ReentrantReadWriteLock

open class SyncExamples {
    /**
     * 可重入锁
     */
    private val mReentrantLock = ReentrantLock(true)
    private val condition = mReentrantLock.newCondition()
    private val mObject = java.lang.Object()


    companion object {
        @JvmStatic
        open fun syncExamples(){
            synchronized(this) {
                Log.e("tag", "${Thread.currentThread().name}syncExamples 开始执行....")
                try {
                    Thread.sleep(1000)//不释放锁
                } catch (ex: InterruptedException) {
                }
                Log.e("tag", "${Thread.currentThread().name}syncExamples 结束....")
            }
        }
    }

    open fun nosync() {
        Log.e("tag", "${Thread.currentThread().name}nosync 开始执行....")
        try {
            Thread.sleep(1000)//不释放锁
        } catch (ex: InterruptedException) {
        }
        Log.e("tag", "${Thread.currentThread().name}nosync 结束....")
    }

    @Synchronized
    open fun sync() {
        Log.e("tag", "${Thread.currentThread().name}sync开始执行....")
        try {
            Thread.sleep(1000)//不释放锁
        } catch (ex: InterruptedException) {
        }
        Log.e("tag", "${Thread.currentThread().name}sync结束....")
    }

    open fun syncs() {
        if (mReentrantLock.tryLock()) {
            try {
//            mReentrantLock.lock()
                Log.e("tag", "${Thread.currentThread().name}sync开始执行....")
                try {
                    Thread.sleep(1000)//不释放锁
//                condition.await(5000, TimeUnit.MILLISECONDS)//释放锁
                } catch (ex: InterruptedException) {
                }
                Log.e("tag", "${Thread.currentThread().name}sync结束....")
            } finally {
                mReentrantLock.unlock()
            }
        }
    }

    open fun sync2() {
        synchronized(this) {
            Log.e("tag", "${Thread.currentThread().name}sync2开始执行....")
            try {
                condition.await()
                Thread.sleep(1000)
            } catch (ex: InterruptedException) {
            }
            Log.e("tag", "${Thread.currentThread().name}sync2结束....")
        }
    }

    @Synchronized
    open fun syncLock() {
//        mReentrantLock.lock()
        synchronized(mObject) {
            try {
                Log.e("tag", "${Thread.currentThread().name}syncLock开始执行....")
                try {
                    mObject.wait()
//                condition.await()
                    Thread.sleep(1000)
                } catch (ex: InterruptedException) {
                }
                Log.e("tag", "${Thread.currentThread().name}syncLock结束....")
            } finally {
//            mReentrantLock.unlock()
            }
        }
    }

    open fun syncLock2() {
//        mReentrantLock.lock()
        synchronized(mObject) {
            try {
                Log.e("tag", "${Thread.currentThread().name}syncLock2开始执行....")
                try {
                    mObject.notify()
//                condition.signal()
                    Thread.sleep(1000)
                } catch (ex: InterruptedException) {
                }
                Log.e("tag", "${Thread.currentThread().name}syncLoc2k结束....")
            } finally {
//            mReentrantLock.unlock()
            }
        }
    }

    open fun sync3() {
        synchronized(SyncExamples::class.java) {
            Log.e("tag", "${Thread.currentThread().name}sync3开始执行....")
            try {
                Thread.sleep(1000)
                condition.signal()
            } catch (ex: InterruptedException) {
            }
            Log.e("tag", "${Thread.currentThread().name}sync3结束....")
        }
    }
}