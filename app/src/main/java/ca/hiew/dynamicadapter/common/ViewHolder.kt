package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
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
        compositeDisposable += outputViewHolderUIEvent(
            position = adapterPosition,
            source = view,
            output = eventOutput
        )
    }

    private fun outputViewHolderUIEvent(
        position: Int,
        source: ObservableSource<UIEvent>,
        output: Consumer<ViewHolderUIEvent>
    ): Disposable {
        return Observable.wrap(source)
            .map { uiEvent -> ViewHolderUIEvent(position, uiEvent) }
            .subscribe(output)
    }
}

