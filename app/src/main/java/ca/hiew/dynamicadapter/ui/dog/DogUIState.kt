package ca.hiew.dynamicadapter.ui.dog

import ca.hiew.dynamicadapter.common.UIState
import ca.hiew.dynamicadapter.util.areTheSame

data class DogUIState(
    val id: Int,
    val name: String,
    val buttonLabel: String = "button $id"
) : UIState {
    override fun areItemsTheSame(o: Any?): Boolean = areTheSame(o) { other -> id == other.id }
}