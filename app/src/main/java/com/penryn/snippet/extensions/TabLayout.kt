package com.penryn.snippet.extensions

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.penryn.snippet.adapters.PagerAdapterWithIcon

/**
 * Created by hoangnhat on 2017-12-30.
 */
fun TabLayout.setupWithViewPagerWithIcons(viewPager: ViewPager) {
    setupWithViewPager(viewPager)
    val adapter = viewPager.adapter
    if (adapter is PagerAdapterWithIcon) {
        for (i in 0 until adapter.count) {
            getTabAt(i)!!.setIcon(adapter.getPageIcon(i))
        }
    }
}
