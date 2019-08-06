package com.nguyen.develop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.google.gson.Gson
import com.nguyen.develop.model.PhotoModel
import com.nguyen.develop.network.GsonRequest
import com.nguyen.develop.network.VolleyHelper
import com.nguyen.develop.remote.PhotoService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class                                                                                                                                                                                                                                                                                                                                                                                                                                                                NetWorkViewModel @Inject constructor(
        private val volleyHelper: VolleyHelper) : ViewModel() {

    private val photos: MutableLiveData<MutableList<PhotoModel>> = MutableLiveData()

    fun getPhotos(): MutableLiveData<MutableList<PhotoModel>> {
        return photos
    }

    private val compositeDisposable = CompositeDisposable()

    fun requestPhotos() {

        val retrofit = Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .client(OkHttpClient.Builder()
                        .connectTimeout(10000,
                                TimeUnit.SECONDS).build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
        val service = retrofit.create(PhotoService::class.java)


        val dispose = service.getPhotosFlowable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnError { _ ->
                }
                .subscribe { t: MutableList<PhotoModel>? ->
                    photos.value = t

                }
        compositeDisposable.add(dispose)

        //using callable retrofit
//        service.getPhotos().enqueue(object : Callback<MutableList<PhotoModel>> {
//            override fun onFailure(call: Call<MutableList<PhotoModel>>?, t: Throwable?) {
//
//            }
//
//            override fun onResponse(call: Call<MutableList<PhotoModel>>?, response: retrofit2.Response<MutableList<PhotoModel>>?) {
//                photos.value = response?.body()
//            }
//
//        })

        //using volley
//        val request =
//                GsonRequest("http://jsonplaceholder.typicode.com/photos",
//                        Array<PhotoModel>::class.java, null,
//                        Response.Listener { response ->
//                            photos.value = response.toMutableList()
//                        }, Response.ErrorListener { _ ->
//
//                })
//        request.tag = "This"
//        volleyHelper.addToRequestQueue(request)
    }
}