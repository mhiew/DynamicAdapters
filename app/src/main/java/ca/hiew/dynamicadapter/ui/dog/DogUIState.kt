package ca.hiew.dynamicadapter.ui.dog

import ca.hiew.dynamicadapter.common.RecyclerViewUIState
import ca.hiew.dynamicadapter.util.areTheSame

data class DogUIState(
    val id: Int,
    val name: String,
    val buttonLabel: String = "button $id"
) : RecyclerViewUIState {
    override fun areItemsTheSame(o: Any?): Boolean = areTheSame(o) { other -> id == other.id }
}