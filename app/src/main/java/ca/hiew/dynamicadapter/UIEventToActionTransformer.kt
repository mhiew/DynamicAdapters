package ca.hiew.dynamicadapter

import ca.hiew.dynamicadapter.common.ui.ViewHolderUIEvent
import ca.hiew.dynamicadapter.ui.cat.CatView
import ca.hiew.dynamicadapter.ui.dog.DogView

class UIEventToActionTransformer {
    fun transform(event: ViewHolderUIEvent): MainDataHub.Action = when (event.uiEvent) {
        DogView.Event.DogButtonClicked -> MainDataHub.Action.AddDog
        CatView.Event.CatViewClicked -> MainDataHub.Action.PetCat(event.position)
        CatView.Event.CatNameClicked -> MainDataHub.Action.Reload
        else -> MainDataHub.Action.None
    }
}