package com.penryn.snippet.adapters

import android.support.v7.widget.RecyclerView

/**
 * Created by hoangnhat on 2017-09-09.
 */
abstract class FilterableAdapter<T, VH : RecyclerView.ViewHolder?>(
    private val completeDataset: List<T>
) : RecyclerView.Adapter<VH>() {
    protected var filteredDataset: ArrayList<T>

    init {
        filteredDataset = ArrayList(completeDataset)
    }

    fun filter(predicate: (T) -> Boolean) {
        filteredDataset.clear()
        filteredDataset = ArrayList(completeDataset.filter(predicate))
        notifyDataSetChanged()
    }
}
