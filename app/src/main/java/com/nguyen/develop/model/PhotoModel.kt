package com.nguyen.develop.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
        @SerializedName("albumId")
        var albumId: Int,
        @SerializedName("id")
        var id: Int,
        @SerializedName("title")
        var title: String,
        @SerializedName("url")
        var url: String,
        @SerializedName("thumbnailUrl")
        var thumbnailUrl: String
)