package ca.hiew.dynamicadapter.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DynamicViewHolder<S : UIState>(
    view: View,
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
) :
    RecyclerView.ViewHolder(view),
    Disposable by compositeDisposable
{
    abstract fun bind(state: S)
    open fun onAttach() {}
    open fun onDetach() {
        compositeDisposable.clear()
    }
}
