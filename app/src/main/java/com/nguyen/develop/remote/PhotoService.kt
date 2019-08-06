package com.nguyen.develop.remote

import com.nguyen.develop.model.PhotoModel
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.http.GET

interface PhotoService {

    @GET("photos")
    fun getPhotos(): Call<MutableList<PhotoModel>>

    @GET("photos")
    fun getPhotosFlowable(): Flowable<MutableList<PhotoModel>>
}