package com.nguyen.develop.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.nguyen.develop.R
import com.nguyen.develop.network.VolleyHelper
import com.nguyen.develop.view.activity.BaseActivity
import com.nguyen.develop.view.adapter.PhotoAdapter
import com.nguyen.develop.viewmodel.NetWorkViewModel
import kotlinx.android.synthetic.main.fragment_network.*
import javax.inject.Inject

class NetworkFragment : BaseFragment() {

    companion object {

        val instance: NetworkFragment by lazy {
            NetworkFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var volleyHelper: VolleyHelper

    lateinit var photoAdapter: PhotoAdapter

    lateinit var viewModel: NetWorkViewModel


    override fun onAttach(context: Context) {
        super.onAttach(context)
        BaseActivity.activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_network, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoAdapter = PhotoAdapter(volleyHelper)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NetWorkViewModel::class.java)
        viewModel.requestPhotos()
        viewModel.getPhotos().observe(this, Observer {
            t ->
            t?.let {
                photoAdapter.addPhotos(it)
            }
        })

        initView()
    }

    override fun onPause() {
        super.onPause()
        volleyHelper.requestQueue.cancelAll("This")
    }

//    override fun getLifecycle(): Lifecycle {
//        return LifecycleRegistry(viewLifecycleOwner)
//    }

    private fun initView() {
        imageRecycleView.layoutManager = GridLayoutManager(context, 2)
        imageRecycleView.adapter = photoAdapter
    }

}