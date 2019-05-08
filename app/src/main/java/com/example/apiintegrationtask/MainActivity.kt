package com.example.apiintegrationtask

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.apiintegrationtask.datasource.models.Hits
import com.example.apiintegrationtask.datasource.remote.NetworkState2
import com.example.apiintegrationtask.viewmodel.AssignmentViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: AssignmentViewModel
    private var list: MutableList<Hits> = ArrayList()
    private lateinit var adapter: MainAdapter
    private var pages = 1
    private var totalPages: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        addObserver()


    }


    private fun init() {
        recyclerView = findViewById(R.id.recycler_view)
        progressBar = findViewById(R.id.progress_bar)
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel::class.java)

        adapter = MainAdapter(list)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        viewModel.getDetails("story", pages.toString())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (pages < totalPages)
                viewModel.getDetails("story", pages.toString())
            }
        })

    }


    private fun addObserver() {
        viewModel.detailsState.observe(this, Observer {
            it ?: return@Observer

            val state = it.getContentIfNotHandled() ?: return@Observer

            if (state is NetworkState2.Loading) {
                progressBar.visibility = View.VISIBLE
                return@Observer
            }

            progressBar.visibility = View.GONE

            when (state) {
                is NetworkState2.Success -> {
                    if (state.data?.hits != null){
                        adapter.dataChange(state.data.hits as MutableList<Hits>)
                        totalPages = state.data.nbPages!!.toInt()
                        pages = state.data.page!!.toInt()
                        if (pages < totalPages)
                           pages += 1
                    }

                }
                is NetworkState2.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()

                }
                is NetworkState2.Failure -> {
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()

                }
                else -> {
                    Toast.makeText(this, "Connection Error", Toast.LENGTH_LONG).show()

                }
            }
        })
    }
}
