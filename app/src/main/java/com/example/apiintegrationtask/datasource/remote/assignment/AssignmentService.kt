package com.example.apiintegration.datasource.remote.assignment

import com.example.apiintegration.ApiClient
import com.example.apiintegration.datasource.models.Item
import com.fh.payday.datasource.remote.ApiCallback
import com.fh.payday.datasource.remote.ApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignmentService {

    companion object {
        val instance: AssignmentService by lazy { AssignmentService() }
    }

    fun getDetails( tags: String, page: String, callback: ApiCallback<List<Item>>) {
        val service = ApiClient.retrofit.create(AssignmentApiInterface::class.java)
        val call = service.getDetails(tags, page)

        call.enqueue(object : Callback<ApiResult<List<Item>>> {
            override fun onFailure(call: Call<ApiResult<List<Item>>>, t: Throwable) {
                callback.onFailure(t)
            }

            override fun onResponse(call: Call<ApiResult<List<Item>>>, response: Response<ApiResult<List<Item>>>) {
                val result = response.body() ?: return callback.onFailure(Throwable("Connection Error"))
                if (result.data != null) {
                    callback.onSuccess(result.data)
                } else {
                    callback.onFailure(Throwable("Connection Error"))
                }
            }

        })
    }
}