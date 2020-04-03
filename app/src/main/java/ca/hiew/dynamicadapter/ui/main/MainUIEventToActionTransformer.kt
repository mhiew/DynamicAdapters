package ca.hiew.dynamicadapter.ui.main

import ca.hiew.dynamicadapter.common.ui.ViewHolderUIEvent
import ca.hiew.dynamicadapter.ui.main.cat.CatView
import ca.hiew.dynamicadapter.ui.main.dog.DogView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

object MainUIEventToActionTransformer {
    operator fun invoke() = ObservableTransformer<ViewHolderUIEvent, MainDataHub.Action> {
        it.flatMap { event -> Observable.just(
            transform(
                event
            )
        ) }
    }
    private fun transform(event: ViewHolderUIEvent): MainDataHub.Action = when (event.uiEvent) {
        DogView.Event.DogButtonClicked -> MainDataHub.Action.AddDog
        CatView.Event.CatViewClicked -> MainDataHub.Action.PetCat(event.position)
        CatView.Event.CatNameClicked -> MainDataHub.Action.Reload
        else -> MainDataHub.Action.None
    }
}