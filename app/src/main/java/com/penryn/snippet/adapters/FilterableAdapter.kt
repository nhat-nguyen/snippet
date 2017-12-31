package com.penryn.snippet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * Created by hoangnhat on 2017-09-09.
 */
abstract class FilterableAdapter<Model, VH : RecyclerView.ViewHolder?>(
    protected val context: Context,
    private var completeDataset: List<Model>
) : RecyclerView.Adapter<VH>() {

    protected var listener: ((app: Model) -> Unit)? = null
    protected var filteredDataset: List<Model> = completeDataset

    fun filter(predicate: (Model) -> Boolean) {
        filteredDataset = completeDataset.filter(predicate)
        notifyDataSetChanged()
    }

    fun setDataset(dataset: List<Model>) {
        completeDataset = dataset
        filteredDataset = dataset
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (app: Model) -> Unit) {
        this.listener = listener
        notifyDataSetChanged()
    }
}
