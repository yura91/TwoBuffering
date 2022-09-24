package com.example.twobufferpagination

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var recyclerView: RecyclerView
    private val adapter: LogsAdapter = LogsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.logListView)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val pos = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (pos == adapter.itemCount - 5) {
                    viewModel.readFiles(this@MainActivity)
                }
            }
        })
        viewModel.readFiles(this)
        viewModel.fileTextWrapper.observe(this) {
            adapter.addMoreItems(it)
        }
    }
}
