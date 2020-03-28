package ca.hiew.dynamicadapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy

class DynamicAdapter(
    private val items: MutableList<UIState> = mutableListOf(),
    private val factory: ViewHolderFactory = DynamicViewHolderFactory(),
    private val uiEventRelay: PublishRelay<UIEvent> = PublishRelay.create()
) : RecyclerView.Adapter<DynamicViewHolder<UIState>>() {

    val diffCallback = DiffCallback()
    val uiEventStream = uiEventRelay.toFlowable(BackpressureStrategy.BUFFER)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicViewHolder<UIState> {
        return factory.getViewHolder(viewType, parent.context)
    }

    override fun onBindViewHolder(holder: DynamicViewHolder<UIState>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType(factory)

    override fun onViewAttachedToWindow(holder: DynamicViewHolder<UIState>) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: DynamicViewHolder<UIState>) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }

    fun setItems(newItems: Collection<UIState>) {
        diffCallback.setItems(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}









