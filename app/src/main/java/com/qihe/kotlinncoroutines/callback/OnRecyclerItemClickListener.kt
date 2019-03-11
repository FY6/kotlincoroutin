package com.qihe.kotlinncoroutines.callback

import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnRecyclerItemClickListener(private val mRecyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {

    private val mGestureDetector by lazy { GestureDetectorCompat(mRecyclerView.context, ItemTouchHelperGestureDetector()) }

    override fun onInterceptTouchEvent(recycler: RecyclerView, event: MotionEvent): Boolean {
        mGestureDetector.onTouchEvent(event)
        return false
    }

    override fun onTouchEvent(recycler: RecyclerView, event: MotionEvent) {
        mGestureDetector.onTouchEvent(event)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}


    private inner class ItemTouchHelperGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val childView = mRecyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null)
                onItemClick(childView, mRecyclerView.getChildAdapterPosition(childView))
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            val childView = mRecyclerView.findChildViewUnder(e.x, e.y)
            if (childView != null)
                onItemLongClick(childView, mRecyclerView.getChildAdapterPosition(childView))
        }
    }


    open fun onItemClick(view: View, position: Int) {}
    open fun onItemLongClick(view: View, position: Int) {}
}