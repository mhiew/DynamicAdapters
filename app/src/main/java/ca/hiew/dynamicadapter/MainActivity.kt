package ca.hiew.dynamicadapter

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.hiew.dynamicadapter.common.DynamicAdapter
import ca.hiew.dynamicadapter.common.RecyclerViewUIState
import ca.hiew.dynamicadapter.common.ViewHolderUIEvent
import ca.hiew.dynamicadapter.ui.cat.Cat
import ca.hiew.dynamicadapter.ui.cat.CatUIState
import ca.hiew.dynamicadapter.ui.cat.CatView
import ca.hiew.dynamicadapter.ui.dog.DogUIState
import ca.hiew.dynamicadapter.ui.dog.DogView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class MainActivity : Activity() {
    private val adapter = DynamicAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val items = loadData()
        adapter.setItems(items)
        compositeDisposable += Observable.wrap(adapter)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
            onNext = { event: ViewHolderUIEvent ->
                Timber.d("$event")
                when (event.uiEvent) {
                    DogView.Event.DogButtonClicked -> { Timber.d("dog button clicked: ${event.position}")
                        adapter.setItems(shuffleData())
                    }
                    CatView.Event.CatViewClicked -> { Timber.d("cat view clicked: ${event.position}")
                        adapter.setItems(loadData())
                    }
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun loadData(): List<RecyclerViewUIState> {
        val items: List<RecyclerViewUIState> = (0 until 20).toList().map {
            if (it % 2 == 0) {
                DogUIState(id = it, name = "dog $it")
            } else if (it % 3 == 0) {
                CatUIState.Error("fake error $it")
            } else {
                CatUIState.Success(Cat(id = it, name = "cat $it"))
            }
        }
        return items
    }

    private fun shuffleData(): List<RecyclerViewUIState> {
        val shuffledList = loadData().toMutableList()
            shuffledList.shuffle()
        return shuffledList
    }
}