package ca.hiew.dynamicadapter.ui.dog

import android.content.Context
import ca.hiew.dynamicadapter.common.DynamicViewHolder
import ca.hiew.dynamicadapter.common.UIEvent
import ca.hiew.dynamicadapter.common.ViewHolderUIEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class DogViewHolder(private val dogView: DogView) : DynamicViewHolder<DogUIState>(dogView) {
    private val viewHolderUIEvent: Observable<ViewHolderUIEvent> =
        Observable.wrap(dogView).map { event -> ViewHolderUIEvent(adapterPosition, event) }

    override fun bind(state: DogUIState) {
        compositeDisposable.clear()
        dogView.accept(state)
        compositeDisposable += viewHolderUIEvent.subscribeBy(
            onNext = { Timber.d("$it") }
        )
    }

    companion object {
        fun create(context: Context): DogViewHolder {
            val dogView = DogView(context)
            return DogViewHolder(dogView)
        }
    }
}
