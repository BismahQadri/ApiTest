package com.example.apiintegrationtask.datasource.remote.assignment

import com.example.apiintegrationtask.ApiClient
import com.example.apiintegrationtask.datasource.models.Base
import com.example.apiintegrationtask.datasource.remote.ApiCallback
import com.example.apiintegrationtask.datasource.remote.ApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignmentService {

    companion object {
        val instance: AssignmentService by lazy { AssignmentService() }
    }

    fun getDetails( tags: String, page: String, callback: ApiCallback<Base>) {
        val service = ApiClient.retrofit.create(AssignmentApiInterface::class.java)
        val call = service.getDetails(tags, page)

        call.enqueue(object : Callback<Base> {
            override fun onFailure(call: Call<Base>, t: Throwable) {
                callback.onFailure(t)
            }

            override fun onResponse(call: Call<Base>, response: Response<Base>) {
                val result = response.body() ?: return callback.onFailure(Throwable("Connection Error"))

                    callback.onSuccess(result)

            }

        })
    }
}