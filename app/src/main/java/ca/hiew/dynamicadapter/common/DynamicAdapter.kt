package ca.hiew.dynamicadapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.ObservableSource

class DynamicAdapter(
    private val items: MutableList<UIState> = mutableListOf(),
    private val factory: ViewHolderFactory = UIViewHolderFactory(),
    private val viewHolderUiEventRelay: PublishRelay<ViewHolderUIEvent> = PublishRelay.create()
) :
    RecyclerView.Adapter<UIStateViewHolder<UIState>>(),
    ObservableSource<ViewHolderUIEvent> by viewHolderUiEventRelay {

    val diffCallback = DiffCallback()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UIStateViewHolder<UIState> {
        return factory.getViewHolder(viewType, parent.context, viewHolderUiEventRelay)
    }

    override fun onBindViewHolder(holder: UIStateViewHolder<UIState>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType(factory)

    fun setItems(newItems: Collection<UIState>) {
        diffCallback.setItems(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}









