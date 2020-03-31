package ca.hiew.dynamicadapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.ObservableSource
import io.reactivex.Observer

class DynamicAdapter(
    private val factory: ViewHolderFactory = UIViewHolderFactory(),
    private val eventRelay: PublishRelay<ViewHolderUIEvent> = PublishRelay.create()
) :
    RecyclerView.Adapter<ViewHolder<UIModel>>(),
    ObservableSource<ViewHolderUIEvent> {

    private val asyncDiffer = AsyncListDiffer<DiffUIModel>(this, DiffItemCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<UIModel> {
        return factory.getViewHolder(viewType, parent.context, eventRelay)
    }

    override fun onBindViewHolder(holder: ViewHolder<UIModel>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = asyncDiffer.currentList.size

    override fun getItemViewType(position: Int): Int = getItem(position).getViewType(factory)

    fun setItems(newItems: List<DiffUIModel>) {
        //quirk - need to make sure we have a new list reference or diffing wont occur with item ordering changes
        val copiedList = ArrayList(newItems)
        asyncDiffer.submitList(copiedList)
    }

    private fun getItem(position: Int): DiffUIModel = asyncDiffer.currentList[position]

    override fun subscribe(observer: Observer<in ViewHolderUIEvent>) {
        eventRelay
            .filter{vhEvent: ViewHolderUIEvent -> vhEvent.position in asyncDiffer.currentList.indices } //sanitize for index out of bounds
            .map { vhEvent: ViewHolderUIEvent ->
                //pass along the corresponding UIModel at the position of this UIEvent
                val uiModel = getItem(vhEvent.position)
                vhEvent.updateWithUIModel(uiModel)
            }
            .subscribe(observer)
    }
}

object DiffItemCallback : DiffUtil.ItemCallback<DiffUIModel>() {
    override fun areItemsTheSame(oldItem: DiffUIModel, newItem: DiffUIModel): Boolean =
        oldItem.areItemsTheSame(newItem)

    override fun areContentsTheSame(oldItem: DiffUIModel, newItem: DiffUIModel): Boolean =
        oldItem.areContentsTheSame(newItem)
}








