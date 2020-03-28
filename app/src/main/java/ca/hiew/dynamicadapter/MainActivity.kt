package ca.hiew.dynamicadapter

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.hiew.dynamicadapter.common.DynamicAdapter
import ca.hiew.dynamicadapter.common.UIState
import ca.hiew.dynamicadapter.ui.cat.Cat
import ca.hiew.dynamicadapter.ui.cat.CatUIState
import ca.hiew.dynamicadapter.ui.dog.DogUIState

class MainActivity : Activity() {
    private val adapter = DynamicAdapter()

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
    }

    private fun loadData(): List<UIState> {
        val items: List<UIState> = (0 until 20).toList().map {
            if (it % 2 == 0) {
                DogUIState(id = it, name = "dog $it")
            }
            else if(it % 3 == 0) {
                CatUIState.Error("fake error $it")
            }
            else {
                CatUIState.Success(Cat(id = it, name = "cat $it"))
            }
        }
        return items
    }
}