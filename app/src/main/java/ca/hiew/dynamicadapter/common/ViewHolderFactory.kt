package ca.hiew.dynamicadapter.common

import android.content.Context
import ca.hiew.dynamicadapter.ui.cat.CatUIState
import ca.hiew.dynamicadapter.ui.cat.CatView
import ca.hiew.dynamicadapter.ui.dog.DogUIState
import ca.hiew.dynamicadapter.ui.dog.DogView
import com.jakewharton.rxrelay2.Relay

interface ViewHolderFactory {
    fun getViewType(state: UIState): Int
    fun getViewHolder(viewType: Int, context: Context, eventRelay: Relay<ViewHolderUIEvent>): UIStateViewHolder<UIState>
}

class UIViewHolderFactory : ViewHolderFactory {
    private enum class ViewType {
        CAT,
        DOG
    }

    override fun getViewType(state: UIState): Int {
        return when (state) {
            is CatUIState -> ViewType.CAT.ordinal
            is DogUIState -> ViewType.DOG.ordinal
            else -> throw RuntimeException("cannot find corresponding UIState $state")
        }
    }

    override fun getViewHolder(viewType: Int, context: Context, eventRelay: Relay<ViewHolderUIEvent>): UIStateViewHolder<UIState> {
        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            ViewType.CAT.ordinal -> UIViewHolder(view = CatView(context), eventRelay = eventRelay)
            ViewType.DOG.ordinal -> UIViewHolder(view = DogView(context), eventRelay = eventRelay)
            else -> throw RuntimeException("cannot find corresponding ViewHolder for viewType $viewType")
        } as UIStateViewHolder<UIState>
    }
}
