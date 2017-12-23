package com.penryn.snippet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * Created by hoangnhat on 2017-09-09.
 */
abstract class FilterableAdapter<T, VH : RecyclerView.ViewHolder?>(
    protected val context: Context,
    private var completeDataset: List<T>
) : RecyclerView.Adapter<VH>() {

    protected var filteredDataset: List<T> = completeDataset

    fun filter(predicate: (T) -> Boolean) {
        filteredDataset = completeDataset.filter(predicate)
        notifyDataSetChanged()
    }

    fun setDataset(dataset: List<T>) {
        completeDataset = dataset
        filteredDataset = dataset
        notifyDataSetChanged()
    }
}
