package com.nguyen.develop.view.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.transaction
import com.nguyen.develop.AndroidApplication
import com.nguyen.develop.di.components.ActivityComponent
import com.nguyen.develop.di.components.DaggerActivityComponent
import com.nguyen.develop.di.modules.ActivityModule

/**
 * Base [DaggerAppCompatActivity] class for every Activity in this application.
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {

        val activityComponent: ActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(AndroidApplication.applicationComponent).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidApplication.applicationComponent.inject(this)
        setFullScreen()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hiddenNavigationBar()
        }
    }

    /**
     * Adds a [Fragment] to this activity's layout.

     * @param containerViewId The container view to where add the fragment.
     * *
     * @param fragment        The fragment to be added.
     */
    protected fun replaceFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        supportFragmentManager.transaction(allowStateLoss = true) {
            replace(containerViewId, fragment, tag).commitNow()
        }
    }

    private fun setFullScreen() {
        window.apply {
            setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
            setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        hiddenNavigationBar()
    }

    private fun hiddenNavigationBar() {
        window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}