package ca.hiew.dynamicadapter.common

import android.content.Context
import ca.hiew.dynamicadapter.ui.cat.CatUIModel
import ca.hiew.dynamicadapter.ui.cat.CatView
import ca.hiew.dynamicadapter.ui.dog.DogUIModel
import ca.hiew.dynamicadapter.ui.dog.DogView
import io.reactivex.functions.Consumer

interface ViewHolderFactory {
    fun getViewType(uiModel: DiffUIModel): Int
    fun getViewHolder(viewType: Int, context: Context, eventOutput: Consumer<ViewHolderUIEvent>): ViewHolder<UIModel>
}

class UIViewHolderFactory : ViewHolderFactory {
    private enum class ViewType {
        CAT,
        DOG
    }

    override fun getViewType(uiModel: DiffUIModel): Int {
        return when (uiModel) {
            is CatUIModel -> ViewType.CAT.ordinal
            is DogUIModel -> ViewType.DOG.ordinal
            else -> throw RuntimeException("cannot find corresponding UIState $uiModel")
        }
    }

    override fun getViewHolder(viewType: Int, context: Context, eventOutput: Consumer<ViewHolderUIEvent>): ViewHolder<UIModel> {
        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            ViewType.CAT.ordinal -> UIViewHolder(view = CatView(context), eventOutput = eventOutput)
            ViewType.DOG.ordinal -> UIViewHolder(view = DogView(context), eventOutput = eventOutput)
            else -> throw RuntimeException("cannot find corresponding ViewHolder for viewType $viewType")
        } as ViewHolder<UIModel>
    }
}
