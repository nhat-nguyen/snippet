package com.penryn.snippet

import android.annotation.SuppressLint
import android.app.assist.AssistContent
import android.app.assist.AssistStructure
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.penryn.snippet.adapters.AppAdapter
import com.penryn.snippet.models.App
import io.realm.Realm

/**
 * Created by hoangnhat on 2017-09-03.
 */
class SnippetSession(
    context: Context,
    private val packageManager: PackageManager,
    private val inputMethodManager: InputMethodManager
) : VoiceInteractionSession(context) {

    private lateinit var scrim: View
    private lateinit var background: View
    private lateinit var searchBox: EditText
    private lateinit var searchResults: RecyclerView

    private val appAdapter: AppAdapter

    init {
        appAdapter = AppAdapter(
            context,
            Realm.getDefaultInstance().where(App::class.java).findAllSorted("label"),
            this::onAppClicked
        )
    }

    override fun onHandleAssist(data: Bundle?, structure: AssistStructure?, content: AssistContent?) {
        super.onHandleAssist(data, structure, content)
    }

    @SuppressLint("InflateParams")
    override fun onCreateContentView(): View {
        val v = layoutInflater.inflate(R.layout.dialog_search, null)
        scrim = v.findViewById(R.id.scrim)
        background = v.findViewById(R.id.background)
        searchResults = v.findViewById(R.id.results)
        searchBox = v.findViewById(R.id.search)
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

        searchResults.adapter = appAdapter
        searchResults.layoutManager = LinearLayoutManager(context)
        searchResults.addItemDecoration(LineItemDecoration(
            ContextCompat.getDrawable(context, R.drawable.line_divider)!!
        ))

        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null) return
                val term = s.toString().toLowerCase()
                appAdapter.filter {
                    it.packageName.toLowerCase().contains(term) or it.label.toLowerCase().contains(term)
                }
            }
        })
    }
}
