package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign

abstract class ViewHolder<Model : UIModel>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(uiModel: Model)
}

class DynamicViewHolder<RV, Model : UIModel>(
    private val view: RV,
    private val eventOutput: Consumer<ViewHolderUIEvent>,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) :
    ViewHolder<Model>(view),
    Disposable by compositeDisposable
        where RV : View, RV : ReactiveView<Model> {

    override fun bind(uiModel: Model) {
        compositeDisposable.clear()
        view.accept(uiModel)
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
            .filter { vhe: ViewHolderUIEvent -> vhe.position != RecyclerView.NO_POSITION } //sanity
}


