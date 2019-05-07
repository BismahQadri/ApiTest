package com.example.apiintegrationtask.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.apiintegrationtask.datasource.models.Item
import com.example.apiintegrationtask.datasource.remote.ApiCallback
import com.example.apiintegrationtask.datasource.remote.NetworkState2
import com.example.apiintegrationtask.datasource.remote.assignment.AssignmentService
import com.example.apiintegrationtask.utilities.Event

class AssignmentViewModel: ViewModel() {
    private val _detailsState = MutableLiveData<Event<NetworkState2<List<Item>>>>()
    val detailsState: LiveData<Event<NetworkState2<List<Item>>>>
        get() = _detailsState


    fun getDetails( tags: String, page: String) {
        _detailsState.value = Event(NetworkState2.Loading())

        AssignmentService.instance.getDetails(tags,page, object : ApiCallback<List<Item>> {
            override fun onFailure(t: Throwable) {
                _detailsState.value = Event(NetworkState2.Failure(Throwable("Connection Error")))
            }

            override fun onError(message: String) {
                _detailsState.value = Event(NetworkState2.Error(message))
            }

            override fun onSuccess(data: List<Item>) {
                _detailsState.value = Event(NetworkState2.Success(data))
            }
        })
    }

}