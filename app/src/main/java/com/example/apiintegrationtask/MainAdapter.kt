package com.example.apiintegrationtask

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.apiintegrationtask.datasource.models.Hits

class MainAdapter (private val items: List<Hits>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindTo(item)
    }

    override fun getItemCount() = items.size

    class ViewHolder(private val views: View) : RecyclerView.ViewHolder(views) {
        private val tvTitle = views.findViewById<TextView>(R.id.tv_title)
        private val tvCreatedAt = views.findViewById<TextView>(R.id.tv_created_at)

        fun bindTo(item: Hits) {
            tvTitle.text = item.title
            tvCreatedAt.text = item.created_at
        }
    }

}