package com.nguyen.develop.model

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import java.util.*

data class PlaceInfo(
        var name: String = "",
        var address: String = "",
        var phoneNumber: String = "",
        var locate: Locale = Locale.US,
        var id: String = "",
        var websiteUri: Uri = Uri.EMPTY,
        var latLng: LatLng = LatLng(0.0, 0.0),
        var rating: Float = 0.0f,
        var attributions: String = ""
)