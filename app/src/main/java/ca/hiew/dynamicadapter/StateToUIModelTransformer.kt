package ca.hiew.dynamicadapter

import ca.hiew.dynamicadapter.common.ui.DiffUIModel
import ca.hiew.dynamicadapter.ui.cat.CatData
import ca.hiew.dynamicadapter.ui.cat.CatUIModel
import ca.hiew.dynamicadapter.ui.dog.DogUIModel

class StateToUIModelTransformer {
    fun transform(state: MainDataHub.State) : List<DiffUIModel> {
        return state.animals.map { animal ->
            when(animal) {
                is Animal.Cat -> CatUIModel.Success(CatData(id = animal.id, name = "Cat ${animal.id}"))
                is Animal.Dog -> DogUIModel(id = animal.id, name = "Dog ${animal.id}")
            }
        }
    }
}
