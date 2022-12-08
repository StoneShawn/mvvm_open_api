package com.shawn.network.repository

import com.shawn.network.NetWorkManager
import com.shawn.network.api.ApiService

class AttractionRepository: BaseRepository() {

    val service: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        NetWorkManager.getRetrofit().create(ApiService::class.java)
    }

    suspend fun getAttractions(lang: String, page: Int) = safeApiCall {
        service.getAttractions(lang, page)
    }
}