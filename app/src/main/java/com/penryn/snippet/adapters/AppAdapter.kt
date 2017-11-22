package com.penryn.snippet.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.penryn.snippet.R
import com.penryn.snippet.models.App

/**
 * Created by hoangnhat on 2017-09-04.
 */

class AppAdapter(
    private val context: Context,
    dataset: List<App>,
    private val listener: (app: App) -> Unit
) : FilterableAdapter<App, AppAdapter.AppViewHolder>(dataset) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.app_item, parent, false)
        return AppViewHolder(v)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = filteredDataset[position]
        val icon = context.packageManager.getApplicationIcon(app.packageName)
        holder.appIcon.setImageDrawable(icon)
        holder.appLabel.setText(app.label)
        holder.appPackageName.setText(app.packageName)
        holder.rootView.setOnClickListener {
            listener.invoke(filteredDataset[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return filteredDataset.size
    }

    class AppViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {
        val appIcon: ImageView = rootView.findViewById(R.id.app_icon) as ImageView
        val appLabel: TextView = rootView.findViewById(R.id.app_label) as TextView
        val appPackageName: TextView = rootView.findViewById(R.id.app_package_name) as TextView
    }
}
