package ca.hiew.dynamicadapter.common

interface UIState {
    fun getViewType(factory: ViewHolderFactory): Int = factory.getViewType(this)
    fun areItemsTheSame(o: Any?) : Boolean
    fun areContentsTheSame(o: Any?): Boolean = (this == o)
}
