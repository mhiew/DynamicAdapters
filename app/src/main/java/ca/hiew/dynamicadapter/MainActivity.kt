package ca.hiew.dynamicadapter

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.hiew.dynamicadapter.common.ui.DynamicAdapter
import ca.hiew.dynamicadapter.ui.main.MainDataHub
import ca.hiew.dynamicadapter.ui.main.MainStateToUIModelTransformer
import ca.hiew.dynamicadapter.ui.main.MainUIEventToActionTransformer
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class MainActivity : Activity() {
    private val dataHub = MainDataHub()
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
        //DataHub State -> Adapter UIModel
        compositeDisposable += Observable.wrap(dataHub).compose(MainStateToUIModelTransformer()).subscribe(adapter)

        //Adapter Events -> DataHub Action
        compositeDisposable += Observable.wrap(adapter).compose(MainUIEventToActionTransformer()).subscribe(dataHub)

        //DataHub Announcements
        compositeDisposable += Observable.wrap(dataHub.announcement).subscribeBy(
            //TODO: handle this explicitly with a announcement consumer
            onNext = { announcement: MainDataHub.Announcement ->
                when (announcement) {
                    is MainDataHub.Announcement.Meow -> Toast.makeText(this, "meow ${announcement.catId}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}