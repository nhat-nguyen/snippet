package com.penryn.snippet

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.penryn.snippet.database.SnippetAppDatabase
import com.penryn.snippet.extensions.runOnUiThread
import com.penryn.snippet.models.App
import com.penryn.snippet.views.AppFilterableRecyclerView

/**
 * Created by hoangnhat on 2017-09-03.
 */
class SnippetSession(
    context: Context,
    private val database: SnippetAppDatabase,
    private val packageManager: PackageManager,
    private val inputMethodManager: InputMethodManager
) : VoiceInteractionSession(context) {

    private lateinit var scrim: View
    private lateinit var background: View
    private lateinit var searchBox: EditText
    private lateinit var pagerAdapter: SearchPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabBar: TabLayout

    @SuppressLint("InflateParams")
    override fun onCreateContentView(): View {
        val v = layoutInflater.inflate(R.layout.dialog_search, null)
        scrim = v.findViewById(R.id.scrim)
        background = v.findViewById(R.id.background)
        searchBox = v.findViewById(R.id.search)
        viewPager = v.findViewById(R.id.viewpager)
        tabBar = v.findViewById(R.id.tab)
        setupViews()
        return v
    }

    private fun onAppClicked(app: App) {
        context.startActivity(packageManager.getLaunchIntentForPackage(app.packageName))
        inputMethodManager.hideSoftInputFromWindow(searchBox.windowToken, 0)
        hide()
    }

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)
        window.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        searchBox.requestFocus()
        inputMethodManager.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViews() {
        // touches outside of the dialog will dismiss it
        scrim.setOnClickListener { hide() }

        // consume the event so that touches within the dialog won't get passed to the background,
        // causing the dialog to be dismissed
        background.setOnTouchListener { _, _ -> true }
        pagerAdapter = SearchPagerAdapter(context, database)

        viewPager.adapter = pagerAdapter
        tabBar.setupWithViewPager(viewPager)

        // TODO: refactor. bind icon array size
        val icons = arrayOf(
            R.drawable.ic_search_black_24dp, R.drawable.clipboard,
            R.drawable.play_store, R.drawable.ic_settings_black_24dp
        )
        for (i in icons.indices) {
            tabBar.getTabAt(i)!!.setIcon(icons[i])
        }

        searchBox.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null) return
                val term = s.toString().toLowerCase()
//                currentTab.filter {
//                    it.packageName.toLowerCase().contains(term) or it.label.toLowerCase().contains(term)
//                }
            }
        })
    }
}

class SearchPagerAdapter(
    private val context: Context,
    private val appDatabase: SnippetAppDatabase
): PagerAdapter() {

    private val viewCache: Array<View?> = arrayOfNulls(4)

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        if (viewCache[position] != null) {
            return viewCache[position]!!
        }

        viewCache[position] = LayoutInflater.from(context).inflate(R.layout.tab_app_search, container, false)
        container.addView(viewCache[position])

        when (position) {
            0, 1, 2, 3 -> {
                Thread(Runnable {
                    val apps = appDatabase.appDao().getAll().sortedBy { it.label }
                    // TODO: Dangling view. Race conditions. Probably need to use destroyItem to unsubscribe properly
                    // TODO: Don't reload
                    // TODO: position?
                    context.runOnUiThread(Runnable {
                        (viewCache[position] as AppFilterableRecyclerView?)?.setDataset(apps)
                    })
                }).start()
            }
        }

        return viewCache[position]!!
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return 4
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        viewCache[position] = null // to be garbage collected
    }
}
