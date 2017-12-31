package com.penryn.snippet.views

import android.content.Context
import android.util.AttributeSet
import com.penryn.snippet.R
import com.penryn.snippet.adapters.AppAdapter
import com.penryn.snippet.models.App

/**
 * Created by hoangnhat on 2017-12-25.
 */
class AppFilterableRecyclerView : FilterableRecyclerView<App, AppAdapter.AppViewHolder, AppAdapter> {

    constructor(context: Context):
        super(context, R.layout.app_listview, R.id.results)

    constructor(context: Context, attribute: AttributeSet?):
        super(context, attribute, R.layout.app_listview, R.id.results)

    override fun provideAdapter(): AppAdapter {
        return AppAdapter(context, emptyList())
    }
}
