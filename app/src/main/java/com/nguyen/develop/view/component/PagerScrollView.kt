package com.nguyen.develop.view.component

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.widget.NestedScrollView

import android.util.AttributeSet
import android.view.MotionEvent

class PagerScrollView : NestedScrollView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    var enableScrolling = true

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean =
            if (enableScrolling) super.onInterceptTouchEvent(ev) else false


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean =
            if (enableScrolling) super.onTouchEvent(ev) else false

}