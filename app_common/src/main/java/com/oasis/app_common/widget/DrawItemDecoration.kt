package com.oasis.app_common.widget

import android.graphics.Canvas
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class DrawItemDecoration: ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.forEachIndexed { _, child ->
            c.save()
            c.clipRect(child.left, child.top, child.right, child.bottom)
            child.draw(c)
            c.restore()
        }
    }
}