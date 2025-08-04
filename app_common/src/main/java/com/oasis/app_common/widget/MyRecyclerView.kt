package com.oasis.app_common.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerView : RecyclerView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style)

    override fun onDraw(c: Canvas) {
        c.clipRect(0, 0, width, height)
        super.onDraw(c)
    }
}