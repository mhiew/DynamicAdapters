package ca.hiew.dynamicadapter.ui.main

import ca.hiew.dynamicadapter.common.ui.DiffUIModel
import ca.hiew.dynamicadapter.domain.Animal
import ca.hiew.dynamicadapter.ui.main.cat.CatData
import ca.hiew.dynamicadapter.ui.main.cat.CatUIModel
import ca.hiew.dynamicadapter.ui.main.dog.DogUIModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

object MainStateToUIModelTransformer {
    operator fun invoke() = ObservableTransformer<MainDataHub.State, List<DiffUIModel>> {
        it.flatMap { state -> Observable.just(
            transform(
                state
            )
        ) }
    }

    private fun transform(state: MainDataHub.State) : List<DiffUIModel> {
        return state.animals.map { animal ->
            when(animal) {
                is Animal.Cat -> CatUIModel.Success(CatData(id = animal.id, name = "Cat ${animal.id}"))
                is Animal.Dog -> DogUIModel(id = animal.id, name = "Dog ${animal.id}")
            }
        }
    }
}
