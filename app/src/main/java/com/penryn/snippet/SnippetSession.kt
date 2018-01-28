package com.penryn.snippet

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.penryn.snippet.adapters.viewpager.TabBarAdapter
import com.penryn.snippet.database.SnippetAppDatabase
import com.penryn.snippet.extensions.setupWithViewPagerWithIcons
import com.penryn.snippet.models.App

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
    private lateinit var pagerAdapter: TabBarAdapter
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
        pagerAdapter = TabBarAdapter(context, database)

        viewPager.adapter = pagerAdapter

        tabBar.setupWithViewPagerWithIcons(viewPager)

        searchBox.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null) return
//                val term = s.toString().toLowerCase()
//                currentTab.filter {
//                    it.packageName.toLowerCase().contains(term) or it.label.toLowerCase().contains(term)
//                }
            }
        })
    }
}
