package ca.hiew.dynamicadapter

import ca.hiew.dynamicadapter.common.hub.BaseDataHub
import ca.hiew.dynamicadapter.util.exhaustive

class MainDataHub : BaseDataHub<MainDataHub.Action, MainDataHub.State, MainDataHub.Announcement>() {
    private val animals: MutableList<Animal> = mutableListOf()

    init {
        load()
    }

    override fun accept(action: Action) {
        when (action) {
            is Action.Reload -> load()
            is Action.AddDog -> appendDog()
            is Action.PetCat -> generateCatAnnouncement(action.position)
            is Action.None -> {}
        }.exhaustive
    }

    private fun load() {
        animals.clear()
        animals.addAll(generateData())
        stateRelay.accept(State(animals))
    }

    private fun appendDog() {
        animals += Animal.Dog(id = animals.size)
        stateRelay.accept(State(animals))
    }

    private fun generateCatAnnouncement(position: Int) {
        if(position in animals.indices) {
            val selectedCat = animals[position] as? Animal.Cat ?: return
            announcementRelay.accept(Announcement.Meow(selectedCat.id))
        }
    }

    private fun generateData(): List<Animal> {
        return (0 until 10).toList().map {
            when {
                (it % 2 == 0) -> Animal.Cat(id = it)
                else -> Animal.Dog(id = it)
            }
        }
    }

    sealed class Action {
        object Reload : Action()
        object AddDog : Action()
        data class PetCat(val position: Int) : Action()
        object None : Action()
    }

    data class State(val animals: List<Animal>)

    sealed class Announcement {
        data class Meow(val catId: Int) : Announcement()
    }
}

sealed class Animal {
    data class Cat(val id: Int) : Animal()
    data class Dog(val id: Int) : Animal()
}

