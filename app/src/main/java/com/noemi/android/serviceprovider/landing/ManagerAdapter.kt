package com.noemi.android.serviceprovider.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.noemi.android.serviceprovider.R
import kotlinx.android.synthetic.main.item_manager.view.*

typealias ManagerClickListener = (item: ManagerItem) -> Unit

class ManagerAdapter(private val managerClickListener: ManagerClickListener) :
    ListAdapter<ManagerItem, ManagerAdapter.ManagerVH>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manager, parent, false)
        return ManagerVH(view)
    }

    override fun onBindViewHolder(holder: ManagerVH, position: Int) {
        holder.bindManager(getItem(position))
    }

    inner class ManagerVH(view: View) : RecyclerView.ViewHolder(view) {

        fun bindManager(item: ManagerItem) {
            itemView.apply {
                ivManger.setImageResource(item.id)
                tvManager.text = item.name

                setOnClickListener {
                    managerClickListener.invoke(item)
                }
            }
        }
    }

    companion object {

        private val diffUtil = object : DiffUtil.ItemCallback<ManagerItem>() {
            override fun areItemsTheSame(oldItem: ManagerItem, newItem: ManagerItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ManagerItem, newItem: ManagerItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}