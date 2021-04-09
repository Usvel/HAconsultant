package com.example.haconsultant.fragment.catalog

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.haconsultant.fragment.home.HomeDecorationTypeProvider
import com.example.haconsultant.fragment.home.dpToPx

class SearchItemDecoration : RecyclerView.ItemDecoration() {

    companion object {
        private val PADDING_LEFT_ITEM = 15
        private val PADDING_RIGHT_ITEM = 5
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) {
            return
        }

        val adapter = parent.adapter
        if (adapter is SearchDecorationTypeProvider) {
            val type = adapter.getTypeProsition(position, parent.context)
            outRect.left = if (type) {
                PADDING_LEFT_ITEM.dpToPx(parent.context).toInt()
            } else {
                PADDING_RIGHT_ITEM.dpToPx(parent.context).toInt()
            }
        }
    }
}