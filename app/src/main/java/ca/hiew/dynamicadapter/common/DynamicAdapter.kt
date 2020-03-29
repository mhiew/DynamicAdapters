package ca.hiew.dynamicadapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.ObservableSource

class DynamicAdapter(
    private val items: MutableList<RecyclerViewUIState> = mutableListOf(),
    private val factory: ViewHolderFactory = UIViewHolderFactory(),
    private val eventRelay: PublishRelay<ViewHolderUIEvent> = PublishRelay.create()
) :
    RecyclerView.Adapter<ViewHolder<UIState>>(),
    ObservableSource<ViewHolderUIEvent> by eventRelay {

    val diffCallback = DiffCallback()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<UIState> {
        return factory.getViewHolder(viewType, parent.context, eventRelay)
    }

    override fun onBindViewHolder(holder: ViewHolder<UIState>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType(factory)

    fun setItems(newItems: Collection<RecyclerViewUIState>) {
        diffCallback.setItems(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}









