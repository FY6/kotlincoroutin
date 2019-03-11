package com.qihe.kotlinncoroutines.callback

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.qihe.kotlinncoroutines.MainActivity
import com.qihe.kotlinncoroutines.dp2px
import java.util.*
import android.view.ViewGroup


class RecyclerItemTouchHelper(private val mAdapter: MainActivity.MyAdapter) : ItemTouchHelper.Callback() {


    /**
     *
     *
     * 该方法用于返回可以滑动的方向，比如说允许从右到左侧滑，允许上下拖动等。
     * 我们一般使用makeMovementFlags(int,int)或makeFlag(int, int)来构造我们的返回值
     *
     */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags: Int = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags, swipeFlags)
    }


    /**
     * 用户拖动一个Item进行上下移动从原始位置到新的位置的时候会调用该方法，
     * 在该方法内，我们可以调用Adapter的notifyItemMoved方法来交换两个ViewHolder的位置，
     * 最后返回true，表示被拖动的ViewHolder已经移动到了目的位置。
     * 所以，如果要实现拖动交换位置，可以重写该方法（前提是支持上下拖动）
     *
     */
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        Log.e("tag", "onMove")
        if (viewHolder.itemViewType != target.itemViewType) return false
        val fromPosition = viewHolder.adapterPosition//得到item原来的position
        val toPosition = target.adapterPosition//得到目标position
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mAdapter.datas, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mAdapter.datas, i, i - 1)
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition)
        return true
    }


    /**
     *
     * 当用户左右滑动Item达到删除条件时，会调用该方法，一般手指触摸滑动的距离达到RecyclerView宽度的一半时，
     * 再松开手指，此时该Item会继续向原先滑动方向滑过去并且调用onSwiped方法进行删除，
     * 否则会反向滑回原来的位置。
     *
     *
     *
     * 果在onSwiped方法内我们没有进行任何操作，即不删除已经滑过去的Item，
     * 那么就会留下空白的地方，因为实际上该ItemView还占据着该位置，
     * 只是移出了我们的可视范围内罢了。
     *
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.e("tag", "onSwiped")
//        mAdapter.datas.removeAt(viewHolder.adapterPosition)
//        mAdapter.notifyItemRemoved(viewHolder.adapterPosition)
    }


    /**
     * 在RecyclerView的onDraw方法中被ItemTouchHelper调用
     *
     * 我们可以在这个方法内实现我们自定义的交互规则或者自定义的动画效果
     *
     *
     */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {


        Log.e("tag", "${getSlideLimitation(viewHolder)}    =====   ${dX}")

        //仅对侧滑状态下的效果做出改变
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //如果dX小于等于删除方块的宽度，那么我们把该方块滑出来
            if (Math.abs(dX) <= getSlideLimitation(viewHolder)) {
                viewHolder.itemView.scrollTo(-dX.toInt(), 0)
            } else {
                viewHolder.itemView.scrollTo(300, 0)
            }
        } else {
            //拖拽状态下不做改变，需要调用父类的方法
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
        Log.e("tag", "onChildDraw")
    }

    /**
     * 获取删除方块的宽度
     */
    private fun getSlideLimitation(viewHolder: RecyclerView.ViewHolder): Int {
        val viewGroup = viewHolder.itemView as ViewGroup
        return viewGroup.getChildAt(1).layoutParams.width
    }

    /**
     *
     * 当用户操作完毕某个item并且其动画也结束后会调用该方法，
     * 一般我们在该方法内恢复ItemView的初始状态，防止由于复用而产生的显示错乱问题。
     *
     *
     * 这是清除View中所做的所有更改的好地方
     *
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        Log.e("tag", "clearView")
        viewHolder.itemView.scrollX = 0
    }

    /**
     *
     * 从闲置状态变为拖拽（drag）或者滑动（swipe）的时候会回调该方法，参数actionState表示当前的状态
     *
     * public static final int ACTION_STATE_IDLE = 0;//闲置状态
     * public static final int ACTION_STATE_SWIPE = 1// 滑动状态
     * public static final int ACTION_STATE_DRAG = 2;// 拖拽状态
     *
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        Log.e("tag", "onSelectedChanged   ${actionState}")
    }

    override fun isItemViewSwipeEnabled() = true
    override fun isLongPressDragEnabled() = true
}