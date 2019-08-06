package com.nguyen.develop.view.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nguyen.develop.R
import kotlinx.android.synthetic.main.fragment_adwords.*

class AdwordsFragment: BaseFragment(){

    companion object {

        val instance: AdwordsFragment by lazy {
            AdwordsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adwords, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgFromServer.setImageURI(Uri.parse("https://github.com/nguyenhuynhdev/tiki-deal/blob/master/presentation/src/main/assets/data/images/10-11017A.jpg"))
    }
}