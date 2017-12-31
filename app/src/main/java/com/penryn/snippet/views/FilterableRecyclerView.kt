package com.penryn.snippet.views

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.penryn.snippet.LineItemDecoration
import com.penryn.snippet.R
import com.penryn.snippet.adapters.FilterableAdapter

/**
 * Created by hoangnhat on 2017-12-25.
 */
abstract class FilterableRecyclerView<Model, VH : RecyclerView.ViewHolder?, Adapter: FilterableAdapter<Model, VH>>: LinearLayout {

    private val layoutResource: Int
    private val recyclerViewResource: Int

    constructor(context: Context, @LayoutRes layoutResource: Int, @IdRes recyclerViewResource: Int)
        : super(context)
    {
        this.layoutResource = layoutResource
        this.recyclerViewResource = recyclerViewResource
        setupViews(context)
    }

    constructor(context: Context, attributes: AttributeSet?, @LayoutRes layoutResource: Int, @IdRes recyclerViewResource: Int)
        : super(context, attributes)
    {
        this.layoutResource = layoutResource
        this.recyclerViewResource = recyclerViewResource
        setupViews(context)
    }

    // TODO: remove listener?
    private var listener: ((app: Model) -> Unit)? = null
    private lateinit var recyclerView: RecyclerView
    private val adapter: Adapter by lazy {
        provideAdapter()
    }

    abstract protected fun provideAdapter(): Adapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(LineItemDecoration(
            ContextCompat.getDrawable(context, R.drawable.line_divider)!!
        ))
    }

    fun filter(predicate: (model: Model) -> Boolean) {
        adapter.filter(predicate)
    }

    fun setDataset(dataset: List<Model>) {
        adapter.setDataset(dataset)
    }

    fun setOnClickListener(listener: (app: Model) -> Unit) {
        adapter.setOnClickListener(listener)
    }

    private fun setupViews(context: Context) {
        // TODO: if no dataset then show loading indicator
        val layout = LayoutInflater.from(context).inflate(layoutResource, this, true)
        recyclerView = layout.findViewById(recyclerViewResource)
    }
}
