package ca.hiew.dynamicadapter.common

import android.content.Context

interface ViewHolderFactory {
    fun getViewType(state: UIState) : Int
    fun getViewHolder(viewType: Int, context: Context) : DynamicViewHolder<UIState>
}

class DynamicViewHolderFactory() : ViewHolderFactory {
    override fun getViewType(state: UIState): Int {
        throw RuntimeException("cannot find corresponding UIState $state")
    }

    override fun getViewHolder(viewType: Int, context: Context) : DynamicViewHolder<UIState> {
        throw RuntimeException("cannot find corresponding viewholder for viewType $viewType")
    }
}
