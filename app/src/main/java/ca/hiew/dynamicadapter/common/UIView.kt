package ca.hiew.dynamicadapter.common

interface UIView<S: UIState> {
    fun display(uiState: S)
}
