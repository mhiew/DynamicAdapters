package ca.hiew.dynamicadapter.ui.dog

import ca.hiew.dynamicadapter.common.DiffableUIModel
import ca.hiew.dynamicadapter.util.areTheSame

data class DogUIModel(
    val id: Int,
    val name: String,
    val buttonLabel: String = "button $id"
) : DiffableUIModel {
    override fun areItemsTheSame(o: Any?): Boolean = areTheSame(o) { other -> id == other.id }
}