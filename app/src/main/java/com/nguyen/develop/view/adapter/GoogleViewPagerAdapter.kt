package com.nguyen.develop.view.adapter


import com.nguyen.develop.view.fragment.BaseFragment

class GoogleViewPagerAdapter constructor(fragmentManager: androidx.fragment.app.FragmentManager) :
        androidx.fragment.app.FragmentStatePagerAdapter(fragmentManager) {

    var fragments = linkedMapOf<BaseFragment, String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(p0: Int): androidx.fragment.app.Fragment = fragments.keys.elementAt(p0)

    override fun getPageTitle(position: Int): CharSequence? = fragments[getItem(position)]

    override fun getCount(): Int = fragments.size
}