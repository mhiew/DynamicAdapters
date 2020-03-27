package ca.hiew.dynamicadapter

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import ca.hiew.dynamicadapter.common.DynamicAdapter

class MainActivity : Activity() {
    private val adapter =  DynamicAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {

    }
}