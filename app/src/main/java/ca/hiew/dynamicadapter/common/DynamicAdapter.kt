package ca.hiew.dynamicadapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.ObservableSource

class DynamicAdapter(
    private val factory: ViewHolderFactory = UIViewHolderFactory(),
    private val eventRelay: PublishRelay<ViewHolderUIEvent> = PublishRelay.create()
) :
    RecyclerView.Adapter<ViewHolder<UIState>>(),
    ObservableSource<ViewHolderUIEvent> by eventRelay {

    private val asyncDiffer = AsyncListDiffer<RecyclerViewUIState>(this, DiffItemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<UIState> {
        return factory.getViewHolder(viewType, parent.context, eventRelay)
    }

    override fun onBindViewHolder(holder: ViewHolder<UIState>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = asyncDiffer.currentList.size

    override fun getItemViewType(position: Int): Int = getItem(position).getViewType(factory)

    fun setItems(newItems: List<RecyclerViewUIState>) {
        //quirk - need to make sure we have a new list reference or diffing wont occur with item ordering changes
        val copiedList = ArrayList(newItems)
        asyncDiffer.submitList(copiedList)
    }

    private fun getItem(position: Int): RecyclerViewUIState = asyncDiffer.currentList[position]
}

object DiffItemCallback : DiffUtil.ItemCallback<RecyclerViewUIState>() {
    override fun areItemsTheSame(oldItem: RecyclerViewUIState, newItem: RecyclerViewUIState): Boolean = oldItem.areItemsTheSame(newItem)
    override fun areContentsTheSame(oldItem: RecyclerViewUIState, newItem: RecyclerViewUIState): Boolean = oldItem.areContentsTheSame(newItem)
}








