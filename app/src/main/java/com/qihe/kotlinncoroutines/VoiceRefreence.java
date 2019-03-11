package com.qihe.kotlinncoroutines;

import android.util.Log;

import java.util.concurrent.CyclicBarrier;

public class VoiceRefreence implements Runnable {
    private final CyclicBarrier mCyclicBarrier;

    public VoiceRefreence(CyclicBarrier cyclicBarrie) {
        this.mCyclicBarrier = cyclicBarrie;
    }


    @Override
    public void run() {
        try {
            Log.e("tag", Thread.currentThread().getName() + "  " + mCyclicBarrier.getParties());
            Thread.sleep(3000);
            mCyclicBarrier.await();
            Thread.sleep(3000);
            Log.e("tag", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

