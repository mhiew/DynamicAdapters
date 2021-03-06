package ca.hiew.dynamicadapter.ui.main.dog

import ca.hiew.dynamicadapter.common.ui.DiffUIModel
import ca.hiew.dynamicadapter.util.areTheSame

data class DogUIModel(
    val id: Int,
    val name: String,
    val buttonLabel: String = "button $id"
) : DiffUIModel {
    override fun areItemsTheSame(o: Any?): Boolean = areTheSame(o) { other -> id == other.id }
}