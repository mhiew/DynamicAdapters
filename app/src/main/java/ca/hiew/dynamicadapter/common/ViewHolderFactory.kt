package ca.hiew.dynamicadapter.common

import android.content.Context
import ca.hiew.dynamicadapter.ui.cat.CatUIState
import ca.hiew.dynamicadapter.ui.cat.CatView
import ca.hiew.dynamicadapter.ui.dog.DogUIState
import ca.hiew.dynamicadapter.ui.dog.DogView
import io.reactivex.functions.Consumer

interface ViewHolderFactory {
    fun getViewType(state: RecyclerViewUIState): Int
    fun getViewHolder(viewType: Int, context: Context, eventOutput: Consumer<ViewHolderUIEvent>): ViewHolder<UIState>
}

class UIViewHolderFactory : ViewHolderFactory {
    private enum class ViewType {
        CAT,
        DOG
    }

    override fun getViewType(state: RecyclerViewUIState): Int {
        return when (state) {
            is CatUIState -> ViewType.CAT.ordinal
            is DogUIState -> ViewType.DOG.ordinal
            else -> throw RuntimeException("cannot find corresponding UIState $state")
        }
    }

    override fun getViewHolder(viewType: Int, context: Context, eventOutput: Consumer<ViewHolderUIEvent>): ViewHolder<UIState> {
        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            ViewType.CAT.ordinal -> UIViewHolder(view = CatView(context), eventOutput = eventOutput)
            ViewType.DOG.ordinal -> UIViewHolder(view = DogView(context), eventOutput = eventOutput)
            else -> throw RuntimeException("cannot find corresponding ViewHolder for viewType $viewType")
        } as ViewHolder<UIState>
    }
}
