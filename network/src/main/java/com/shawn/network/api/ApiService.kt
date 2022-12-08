package com.shawn.network.api

import com.shawn.network.model.AttractionModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("{lang}"+ConstantUrl.ATTRACTIONS_ALL)
    suspend fun getAttractions(@Path("lang") lang: String ,@Query("page") page: Int) : AttractionModel
}