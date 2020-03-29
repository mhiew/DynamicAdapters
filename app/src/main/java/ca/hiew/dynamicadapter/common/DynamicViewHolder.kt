package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

abstract class DynamicViewHolder<S : UIState>(
    view: View,
    private val eventRelay: PublishRelay<ViewHolderUIEvent> = PublishRelay.create(),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) :
    RecyclerView.ViewHolder(view),
    ObservableSource<ViewHolderUIEvent> by eventRelay,
    Disposable by compositeDisposable {
    fun bind(state: S) {
        compositeDisposable.clear()
        doBinding(state)
    }

    abstract fun doBinding(state: S)
    open fun onAttach() {}
    open fun onDetach() {}

    protected fun outputUIEvent(source: ObservableSource<UIEvent>) {
        outputViewHolderUIEvent(adapterPosition, source, eventRelay, compositeDisposable)
    }

    private fun outputViewHolderUIEvent(
        position: Int,
        source: ObservableSource<UIEvent>,
        consumer: Consumer<ViewHolderUIEvent>,
        compositeDisposable: CompositeDisposable
    ) {
        compositeDisposable += Observable.wrap(source)
            .map { uiEvent -> ViewHolderUIEvent(position, uiEvent) }
            .subscribeBy(
                onNext = { viewHolderUiEvent ->
                    Timber.d("$viewHolderUiEvent")
                    consumer.accept(viewHolderUiEvent)
                }
            )
    }
}

