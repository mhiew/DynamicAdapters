package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

abstract class UIStateViewHolder<S : UIState>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(uiState: S)
}

class UIViewHolder<V, S : UIState>(
    private val view: V,
    private val eventRelay: Relay<ViewHolderUIEvent>,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) :
    UIStateViewHolder<S>(view),
    Disposable by compositeDisposable,
    ObservableSource<ViewHolderUIEvent> by eventRelay
        where V : View, V : UIView<S> {
    override fun bind(uiState: S) {
        compositeDisposable.clear()
        view.accept(uiState)
        compositeDisposable += outputViewHolderUIEvent(
            position = adapterPosition,
            source = view,
            output = eventRelay
        )
    }

    private fun outputViewHolderUIEvent(
        position: Int,
        source: ObservableSource<UIEvent>,
        output: Consumer<ViewHolderUIEvent>
    ): Disposable {
        return Observable.wrap(source)
            .map { uiEvent -> ViewHolderUIEvent(position, uiEvent) }
            .subscribeBy(
                onNext = { viewHolderUiEvent ->
                    Timber.d("$viewHolderUiEvent")
                    output.accept(viewHolderUiEvent)
                }
            )
    }
}

