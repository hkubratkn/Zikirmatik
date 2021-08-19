package com.kapirti.zikirmatik.servis

import com.kapirti.zikirmatik.model.ZikirModel
import io.reactivex.Single
import retrofit2.http.GET

interface ZikirApi {

    @GET("hkubratkn/zikir-data-list/main/zikir.json")
    fun getZikir():Single<List<ZikirModel>>
}