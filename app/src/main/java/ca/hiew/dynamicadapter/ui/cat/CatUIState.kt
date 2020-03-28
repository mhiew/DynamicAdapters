package ca.hiew.dynamicadapter.ui.cat

import ca.hiew.dynamicadapter.common.UIState
import ca.hiew.dynamicadapter.util.areTheSame

data class Cat(
    val id: Int,
    val name: String
)

sealed class CatUIState : UIState {
    data class Success(val cat: Cat) : CatUIState() {
        override fun areItemsTheSame(o: Any?): Boolean =
            areTheSame(o) { other -> cat.id == other.cat.id }
    }

    data class Loading(val cat: Cat) : CatUIState() {
        override fun areItemsTheSame(o: Any?): Boolean =
            areTheSame(o) { other -> cat.id == other.cat.id }
    }

    data class Error(val errorMessage: String?) : CatUIState() {
        override fun areItemsTheSame(o: Any?): Boolean = areTheSame(o) { other -> errorMessage == other.errorMessage }
    }
}




