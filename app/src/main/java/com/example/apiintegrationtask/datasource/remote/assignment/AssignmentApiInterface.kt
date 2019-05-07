package com.example.apiintegrationtask.datasource.remote.assignment


import com.example.apiintegrationtask.datasource.models.Item
import com.example.apiintegrationtask.datasource.remote.ApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AssignmentApiInterface {
    @GET("search_by_date")
    fun getDetails(
            @Query("tags") tags: String = "story",
            @Query("page") page: String = "1"
    ): Call<ApiResult<List<Item>>>

}