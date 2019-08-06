package com.nguyen.develop.view.activity

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.nguyen.develop.R
import com.nguyen.develop.view.adapter.GoogleViewPagerAdapter
import com.nguyen.develop.view.fragment.*
import kotlinx.android.synthetic.main.activity_google.*

class GoogleActivity : BaseActivity() {

    companion object {

        val fragments = linkedMapOf(
                Pair(MapsFragment.instance, "Maps"),
                Pair(NetworkFragment.instance, "Network"),
                Pair(StorageFragment.instance, "Storage"),
                Pair(OauthFragment.instance, "Oauth"),
                Pair(NotificationFragment.instance, "Notification"),
                Pair(AdwordsFragment.instance, "Adwords"))
    }

    private lateinit var googleViewPagerAdapter: GoogleViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google)
        setSupportActionBar(toolbar)
        collapsing.title = "Google"
        appBar.addOnOffsetChangedListener(com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener { _, p1 ->
            when {
                Math.abs(p1) == appBar.totalScrollRange -> {
                    // Collapsed
                    pagerScrollView.enableScrolling = false
                }
                p1 == 0 -> {
                    // Expanded
                }
                else -> {
                    pagerScrollView.enableScrolling = true
                }
            }
        })
        //Setup adapter
        googleViewPagerAdapter = GoogleViewPagerAdapter(supportFragmentManager)
        googleViewPagerAdapter.fragments = fragments
        viewPager.adapter = googleViewPagerAdapter

        tabs.setupWithViewPager(viewPager)
    }
}