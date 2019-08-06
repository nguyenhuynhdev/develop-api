package com.nguyen.develop.model.mapper

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.nguyen.develop.model.PhotoModel



class PhotoJsonDataMapper (private val gson: Gson){

    fun transform(jsonResponse: String): PhotoModel {
        val entityType = object : TypeToken<PhotoModel>() {

        }.type
        return gson.fromJson<PhotoModel>(jsonResponse, entityType)
    }

    @Throws(JsonSyntaxException::class)
    fun transformCollection(jsonResponse: String): Collection<PhotoModel> {
        val listOfEntityType = object : TypeToken<Collection<PhotoModel>>() {

        }.type
        return gson.fromJson<Collection<PhotoModel>>(jsonResponse, listOfEntityType)
    }
}