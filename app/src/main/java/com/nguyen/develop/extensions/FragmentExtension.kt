package com.nguyen.develop.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: Any) {
    activity?.let {
        Toast.makeText(it, message.toString(), Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.hiddenSoftKeyBoard() {
    (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager).hideSoftInputFromWindow(view!!.windowToken, 0)
}