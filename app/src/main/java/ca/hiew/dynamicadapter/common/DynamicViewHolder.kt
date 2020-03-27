package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class DynamicViewHolder<S: UIState>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(state: S)
}
