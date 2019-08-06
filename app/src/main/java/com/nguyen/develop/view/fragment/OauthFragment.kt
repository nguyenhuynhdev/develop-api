package com.nguyen.develop.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nguyen.develop.R

class OauthFragment: BaseFragment(){

    companion object {

        val instance: OauthFragment by lazy {
            OauthFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_oauth, container, false)
    }
}