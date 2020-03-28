package ca.hiew.dynamicadapter.ui.dog

import android.content.Context
import ca.hiew.dynamicadapter.common.DynamicViewHolder
import ca.hiew.dynamicadapter.common.UIEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class DogViewHolder(private val dogView: DogView) : DynamicViewHolder<DogUIState>(dogView) {
    override fun bind(state: DogUIState) {
        dogView.accept(state)
        compositeDisposable += Observable.wrap(dogView).subscribeBy(
            onNext = { Timber.d("$adapterPosition $it") }
        )
    }

    companion object {
        fun create(context: Context): DogViewHolder {
            val dogView = DogView(context)
            return DogViewHolder(dogView)
        }
    }
}
