package com.sunny.learn.pcfassignmentapp

import retrofit2.Call
import retrofit2.http.GET

interface PCFEndpoints {

    @GET("repositories")
    fun getAllDataList(): Call<List<Result>>
}