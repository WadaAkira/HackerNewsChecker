package com.example.hackernewschecker.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewschecker.R

/**
 * RecyclerView の Decoration クラス<br>
 * 最初の要素にのみ marginTop を設定するための設定
 */
class MainDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = parent.context.resources.getDimensionPixelSize(R.dimen.default_padding)
        }
    }
}