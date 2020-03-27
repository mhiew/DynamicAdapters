package ca.hiew.dynamicadapter.ui.cat

import ca.hiew.dynamicadapter.common.UIState

data class CatUIState(
    val id: Int,
    val name: String
) : UIState {
    override fun areItemsTheSame(o: Any?): Boolean = if (o is CatUIState) id == o.id else false
}
