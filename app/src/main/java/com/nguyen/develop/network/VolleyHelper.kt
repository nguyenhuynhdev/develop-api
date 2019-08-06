package com.nguyen.develop.network

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader

interface VolleyHelper{

    val imageLoader: ImageLoader

    val requestQueue: RequestQueue

    fun <T> addToRequestQueue(req: Request<T>)
}