package ca.hiew.dynamicadapter.common

import android.content.Context
import ca.hiew.dynamicadapter.ui.cat.CatUIState
import ca.hiew.dynamicadapter.ui.cat.CatViewHolder
import ca.hiew.dynamicadapter.ui.dog.DogUIState
import ca.hiew.dynamicadapter.ui.dog.DogViewHolder

interface ViewHolderFactory {
    fun getViewType(state: UIState): Int
    fun getViewHolder(viewType: Int, context: Context): DynamicViewHolder<UIState>
}

class DynamicViewHolderFactory() : ViewHolderFactory {
    private enum class View {
        CAT,
        DOG
    }
    private val View.type get() = this.ordinal

    override fun getViewType(state: UIState): Int {
        return when (state) {
            is CatUIState -> View.CAT.type
            is DogUIState -> View.DOG.type
            else -> throw RuntimeException("cannot find corresponding UIState $state")
        }
    }

    override fun getViewHolder(viewType: Int, context: Context): DynamicViewHolder<UIState> {
        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            View.CAT.type -> CatViewHolder.create(context)
            View.DOG.type -> DogViewHolder.create(context)
            else -> throw RuntimeException("cannot find corresponding ViewHolder for viewType $viewType")
        } as DynamicViewHolder<UIState>
    }
}
