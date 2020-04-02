package ca.hiew.dynamicadapter.common.ui

interface UIModel

interface DiffUIModel : UIModel {
    fun getViewType(factory: ViewHolderFactory): Int = factory.getViewType(this)
    fun areItemsTheSame(o: Any?): Boolean
    fun areContentsTheSame(o: Any?): Boolean = (this == o)
}

object EmptyUIModel : UIModel
