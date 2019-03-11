package com.qihe.kotlinncoroutines

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PanddingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = resources.getColor(R.color.colorAccent)
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom

        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom
        val radius = Math.max(width, height) / 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
        // 在wrap_content的情况下默认长度为200dp
        val minSize = 200
        // wrap_content的specMode是AT_MOST模式，这种情况下宽/高等同于specSize
        // 查表得这种情况下specSize等同于parentSize，也就是父容器当前剩余的大小
        // 在wrap_content的情况下如果特殊处理，效果等同martch_parent
        if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, minSize)
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(minSize, heightSpecSize)
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, minSize)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}