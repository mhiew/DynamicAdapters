package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class DynamicViewHolder<S : UIState>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(state: S)
    open fun onAttach() {
        Timber.d("onAttach position: $adapterPosition")
    }
    open fun onDetach() {
        Timber.d("onDetach position: $adapterPosition")
    }
}
