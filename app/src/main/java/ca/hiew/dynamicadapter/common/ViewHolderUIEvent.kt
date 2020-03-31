package ca.hiew.dynamicadapter.common

data class ViewHolderUIEvent(
    val position: Int,
    val uiModel: UIModel = EmptyUIModel,
    val uiEvent: UIEvent
)

fun ViewHolderUIEvent.updateWithUIModel(uiModel: UIModel) : ViewHolderUIEvent = this.copy(uiModel = uiModel)
