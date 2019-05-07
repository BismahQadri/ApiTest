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
import com.example.apiintegrationtask.datasource.remote.NetworkState2
import com.example.apiintegrationtask.viewmodel.AssignmentViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: AssignmentViewModel

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
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.getDetails("story", "1")
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
                    if (state.data?.hits != null)
                        recyclerView.adapter = MainAdapter(state.data.hits)
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
