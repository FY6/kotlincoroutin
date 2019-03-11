package com.qihe.kotlinncoroutines

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class FYLayoutManager(val context: Context) : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)


    override fun canScrollVertically(): Boolean {
        return true
    }

    private var verticalScrollOffset: Int = 0

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        //每次滑动时先释放掉所有的View，因为后面调用recycleAndFillView()时会重新addView()。
        detachAndScrapAttachedViews(recycler)
        // 列表向下滚动dy为正，列表向上滚动dy为负，这点与Android坐标系保持一致。
        // 实际要滑动的距离
        var travel = dy
        // 如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {// 如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset
        }
        // 调用该方法通知view在y方向上移动指定距离
        offsetChildrenVertical(-travel)
        recycleAndFillView(recycler, state) //回收并显示View
        // 将竖直方向的偏移量+travel
        verticalScrollOffset += travel
        return travel
    }

    private fun getVerticalSpace(): Int {
        // 计算RecyclerView的可用高度，除去上下Padding值
        return height - paddingBottom - paddingTop
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount <= 0 || state.isPreLayout) {
            return
        }
        // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示    // 这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        super.onLayoutChildren(recycler, state)
        detachAndScrapAttachedViews(recycler)
        /* 这个方法主要用于计算并保存每个ItemView的位置 */
        calculateChildrenSite(recycler)
        recycleAndFillView(recycler, state)
    }

    private fun recycleAndFillView(recycler: RecyclerView.Recycler, state: RecyclerView.State) {

    }

    private var totalHeight: Int = 0

    private fun calculateChildrenSite(recycler: RecyclerView.Recycler) {
        totalHeight = 0
        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            //我们自己指定ItemView的尺寸。
            measureChildWithMargins(view, DisplayUtils.getScreenWidget(context) / 2, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            val mTmpRect = Rect()
            calculateItemDecorationsForChild(view, mTmpRect)
            if (i % 2 == 0) { //当i能被2整除时，是左，否则是右。
                //左
                layoutDecoratedWithMargins(view, 0, totalHeight, DisplayUtils.getScreenWidget(context) / 2,
                        totalHeight + height)
            } else {
                //右，需要换行
                layoutDecoratedWithMargins(view, DisplayUtils.getScreenWidget(context) / 2, totalHeight,
                        DisplayUtils.getScreenWidget(context), totalHeight + height)
                totalHeight += height
            }
        }

    }
}