package com.penryn.snippet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.penryn.snippet.R
import com.penryn.snippet.models.Snippet

/**
 * Created by hoangnhat on 2017-09-04.
 */
class SnippetAdapter(
    private val context: Context,
    private val dataset: List<Snippet>,
    private val listener: OnClickListener?
) : RecyclerView.Adapter<SnippetAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.search_item, parent, false)
        return ResultViewHolder(v)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.name.setText(dataset[position].name)
        holder.value.setText(dataset[position].value)
        if (listener != null) {
            val newPosition = holder.adapterPosition
            holder.rootView.setOnClickListener { _ -> listener.onClick(dataset[newPosition]) }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface OnClickListener {
        fun onClick(snippet: Snippet)
    }

    class ResultViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
        val name: TextView = rootView.findViewById(R.id.name)
        val value: TextView = rootView.findViewById(R.id.value)
    }
}
