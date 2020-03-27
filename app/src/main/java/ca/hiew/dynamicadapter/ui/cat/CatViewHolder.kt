package ca.hiew.dynamicadapter.ui.cat

import android.content.Context
import ca.hiew.dynamicadapter.common.DynamicViewHolder


class CatViewHolder(private val catView: CatView) : DynamicViewHolder<CatUIState>(catView) {
    override fun bind(state: CatUIState) {
        catView.display(state)
    }

    companion object {
        fun create(context: Context): CatViewHolder {
            val catView = CatView(context)
            return CatViewHolder(catView)
        }
    }
}