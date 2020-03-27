package ca.hiew.dynamicadapter.ui.dog

import ca.hiew.dynamicadapter.common.UIState

data class DogUIState(
    val id: Int,
    val name: String
) : UIState {
    override fun areItemsTheSame(o: Any?): Boolean = if(o is DogUIState) id == o.id else false
}