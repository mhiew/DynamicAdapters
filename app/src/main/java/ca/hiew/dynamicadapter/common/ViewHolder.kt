package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign

abstract class ViewHolder<S : UIState>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(uiState: S)
}

class UIViewHolder<V, S : UIState>(
    private val view: V,
    private val eventOutput: Consumer<ViewHolderUIEvent>,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) :
    ViewHolder<S>(view),
    Disposable by compositeDisposable
        where V : View, V : UIView<S> {

    override fun bind(uiState: S) {
        compositeDisposable.clear()
        view.accept(uiState)
        compositeDisposable += observableViewHolderUIEvent.subscribe(eventOutput)
    }

    private val observableAdapterPosition: Observable<Int> =
        Observable.fromCallable { adapterPosition }

    private val observableUIEvent: Observable<UIEvent> = Observable.wrap(view)

    //combines current adapter Position and UIEvent to create a ViewHolderUIEvent
    private val observableViewHolderUIEvent: Observable<ViewHolderUIEvent> =
        Observables.combineLatest(
            observableAdapterPosition,
            observableUIEvent
        ) { adapterPosition, event ->
            ViewHolderUIEvent(position = adapterPosition, uiEvent = event)
        }
}


