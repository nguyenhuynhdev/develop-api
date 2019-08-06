package com.nguyen.develop.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nguyen.develop.R

class NotificationFragment : BaseFragment() {

    companion object {

        val instance: NotificationFragment by lazy {
            NotificationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

}