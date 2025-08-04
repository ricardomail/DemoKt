package com.oasis.app_common.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.oasis.app_common.util.Extension.dp2px


class DrawItemDecoration : ItemDecoration() {
    private val dividerPaint = Paint().apply {
        color = Color.GREEN
    }

    private val overPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }
    private var dividerTopAndBottom = 5.dp2px()
    private var offsetLeft = 30.dp2px()
    private var offsetRight = 10.dp2px()
    private val radius = 4.dp2px()

    init {
        dividerPaint.setColor(Color.GREEN)
        dividerPaint.setStyle(Paint.Style.STROKE)
    }

    //getItemOffsets 是针对每一个 ItemView
    // 注意的是这些属性都是偏移量，是指偏移 ItemView 各个方向的数值。
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = dividerTopAndBottom
        }
        outRect.left = offsetLeft
        outRect.right = offsetRight
        outRect.bottom = dividerTopAndBottom
    }

    /**
     * onDraw 方法，ItemDecoration 是在 ItemView 的下方绘制的，也就是 ItemView 可能会覆盖 ItemDecoration 的内容
     */
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        // 可见ItemView个数
        for (i in 0 until parent.childCount) {
            val visibleView = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(visibleView)
            val dividerTop =
                if (index == 0) visibleView.top else visibleView.top - dividerTopAndBottom
            val dividerLeft = parent.paddingLeft // 如果需要计算padding，就需要使用这个，如果计算的位置不需要padding，直接可以使用item的left
            val divideRight = parent.width - parent.paddingRight
            val dividerBottom = visibleView.bottom

//            val centerX = visibleView.left / 2 recyclerView到item的距离
            val centerX = dividerLeft + offsetLeft / 2
            val centerY = dividerTop + (dividerBottom - dividerTop) / 2

            val upLineBottomY = centerY - radius

            canvas.drawLine(
                centerX.toFloat(),
                dividerTop.toFloat(), centerX.toFloat(), upLineBottomY.toFloat(), dividerPaint
            )

            canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), dividerPaint)
            val downLineTopY = centerY + radius

            canvas.drawLine(
                centerX.toFloat(),
                downLineTopY.toFloat(), centerX.toFloat(),
                dividerBottom.toFloat(), dividerPaint
            )
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            val top = view.top
            val left = view.left
            if (index < 3){
                c.drawCircle((left + 20).toFloat(),(top + 20).toFloat(),
                    radius.toFloat(), overPaint)
            }
        }
    }
}