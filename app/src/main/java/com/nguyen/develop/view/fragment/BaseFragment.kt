package com.nguyen.develop.view.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import android.widget.Toast

/**
 * Base [DaggerFragment] class for every fragment in this application.
 */
abstract class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    /**
     * Shows a [android.widget.Toast] message.

     * @param message An string representing a message to be shown.
     */
    protected fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}