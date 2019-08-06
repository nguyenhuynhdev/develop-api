package com.nguyen.develop.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyen.develop.R
import com.nguyen.develop.model.PhotoModel
import com.nguyen.develop.network.NetWorkRequest
import com.nguyen.develop.network.VolleyHelper
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter
constructor(private val volleyHelper: VolleyHelper) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val photos = mutableListOf<PhotoModel>()

    lateinit var applicationContext: Context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoViewHolder {
        applicationContext = p0.context.applicationContext
        val item = LayoutInflater.from(p0.context).inflate(R.layout.item_photo, p0, false)
        return PhotoViewHolder(item)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(p0: PhotoViewHolder, p1: Int) {
        val photo = photos[p1]
        p0.bind(photo)
    }

    fun addPhotos(mutableCollection: MutableCollection<PhotoModel>) {
        photos.addAll(mutableCollection)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        fun bind(photo: PhotoModel) {
            itemView.txtTitle.text = photo.title
            itemView.imgPhoto.setDefaultImageResId(R.drawable.img_default)
            itemView.imgPhoto.setErrorImageResId(R.drawable.img_error)
            itemView.imgPhoto.setImageUrl(photo.url, volleyHelper.imageLoader)
        }
    }
}