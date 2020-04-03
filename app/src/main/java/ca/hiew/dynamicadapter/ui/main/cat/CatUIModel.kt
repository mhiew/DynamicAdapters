package ca.hiew.dynamicadapter.ui.main.cat

import ca.hiew.dynamicadapter.common.ui.DiffUIModel
import ca.hiew.dynamicadapter.util.areTheSame

data class CatData(
    val id: Int,
    val name: String
)

sealed class CatUIModel : DiffUIModel {
    data class Success(val cat: CatData) : CatUIModel() {
        override fun areItemsTheSame(o: Any?): Boolean =
            areTheSame(o) { other -> cat.id == other.cat.id }
    }

    data class Loading(val cat: CatData) : CatUIModel() {
        override fun areItemsTheSame(o: Any?): Boolean =
            areTheSame(o) { other -> cat.id == other.cat.id }
    }

    data class Error(val errorMessage: String?) : CatUIModel() {
        override fun areItemsTheSame(o: Any?): Boolean = areTheSame(o) { other -> errorMessage == other.errorMessage }
    }
}




