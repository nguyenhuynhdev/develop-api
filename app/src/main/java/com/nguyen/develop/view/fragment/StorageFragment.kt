package com.nguyen.develop.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nguyen.develop.R

class StorageFragment: BaseFragment() {

    companion object {

        val instance: StorageFragment by lazy {
            StorageFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }
}