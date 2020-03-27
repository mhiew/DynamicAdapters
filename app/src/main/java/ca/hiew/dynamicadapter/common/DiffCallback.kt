package ca.hiew.dynamicadapter.common

import androidx.recyclerview.widget.DiffUtil

class DiffCallback(
    private val oldItems : MutableList<UIState> = mutableListOf(),
    private val newItems : MutableList<UIState> = mutableListOf()
) : DiffUtil.Callback() {

    fun <T: UIState> setItems(oldItems: Collection<T>, newItems: Collection<T>) {
        this.oldItems.clear()
        this.oldItems.addAll(oldItems)
        this.newItems.clear()
        this.newItems.addAll(newItems)
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.areContentsTheSame(newItem)
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.areItemsTheSame(newItem)
    }
}