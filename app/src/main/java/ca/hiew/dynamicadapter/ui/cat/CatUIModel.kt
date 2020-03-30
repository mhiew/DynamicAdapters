package ca.hiew.dynamicadapter.ui.cat

import ca.hiew.dynamicadapter.common.DiffableUIModel
import ca.hiew.dynamicadapter.util.areTheSame

data class Cat(
    val id: Int,
    val name: String
)

sealed class CatUIModel : DiffableUIModel {
    data class Success(val cat: Cat) : CatUIModel() {
        override fun areItemsTheSame(o: Any?): Boolean =
            areTheSame(o) { other -> cat.id == other.cat.id }
    }

    data class Loading(val cat: Cat) : CatUIModel() {
        override fun areItemsTheSame(o: Any?): Boolean =
            areTheSame(o) { other -> cat.id == other.cat.id }
    }

    data class Error(val errorMessage: String?) : CatUIModel() {
        override fun areItemsTheSame(o: Any?): Boolean = areTheSame(o) { other -> errorMessage == other.errorMessage }
    }
}




