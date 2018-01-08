package com.penryn.snippet.adapters.viewpager

import android.support.annotation.DrawableRes
import android.support.v4.view.PagerAdapter

/**
 * Created by hoangnhat on 2017-12-30.
 */
abstract class PagerAdapterIconDrawable : PagerAdapter() {
    @DrawableRes
    abstract fun getPageIcon(position: Int): Int
}
