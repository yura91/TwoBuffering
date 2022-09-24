package com.example.twobufferpagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogsAdapter : RecyclerView.Adapter<ViewHolder>() {

    val items: ArrayList<String?> = arrayListOf()

    fun addItem(item: String) {
        items.add(item)
        val insertedAt = items.size - 1
        notifyItemInserted(insertedAt)
    }

    fun clear() {
        val range = items.size
        items.clear()
        notifyItemRangeRemoved(0, range)
    }

    fun addMoreItems(item: ArrayList<String?>) {
        val previousRange = items.size
        items.addAll(item)
        val newRange = items.size
        notifyItemRangeInserted(previousRange, newRange)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemPosition.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemPosition: TextView = view.findViewById(R.id.contentLog)
}