package ca.hiew.dynamicadapter.common

interface UIModel

interface DiffableUIModel : UIModel {
    fun getViewType(factory: ViewHolderFactory): Int = factory.getViewType(this)
    fun areItemsTheSame(o: Any?): Boolean
    fun areContentsTheSame(o: Any?): Boolean = (this == o)
}
