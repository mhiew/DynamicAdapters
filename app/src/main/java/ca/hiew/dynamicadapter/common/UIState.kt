package ca.hiew.dynamicadapter.common

interface UIState

interface RecyclerViewUIState : UIState {
    fun getViewType(factory: ViewHolderFactory): Int = factory.getViewType(this)
    fun areItemsTheSame(o: Any?): Boolean
    fun areContentsTheSame(o: Any?): Boolean = (this == o)
}
