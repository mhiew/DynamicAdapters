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
    enum class ViewType {
        CAT,
        DOG;

        val type: Int get() = this.ordinal
    }

    override fun getViewType(uiModel: DiffUIModel): Int {
        return when (uiModel) {
            is CatUIModel -> ViewType.CAT.type
            is DogUIModel -> ViewType.DOG.type
            else -> throw IllegalArgumentException("cannot find corresponding ViewType for $uiModel")
        }
    }

    override fun getViewHolder(viewType: Int, context: Context, eventOutput: Consumer<ViewHolderUIEvent>): ViewHolder<UIModel> {
        @Suppress("UNCHECKED_CAST")
        return when (viewType) {
            ViewType.CAT.type-> DynamicViewHolder(view = CatView(context), eventOutput = eventOutput)
            ViewType.DOG.type -> DynamicViewHolder(view = DogView(context), eventOutput = eventOutput)
            else -> throw IllegalArgumentException("cannot find corresponding ViewHolder or View for viewType $viewType")
        } as ViewHolder<UIModel>
    }
}
