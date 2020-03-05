package com.sngular.core.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GenericDataBindingAdapter<Model>(
    private val itemVariableId: Int,
    private val layoutResId: Int
) : RecyclerView.Adapter<DataBindingViewHolder<Model>>() {

    private var items = mutableListOf<Model>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<Model> {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutResId,
            parent,
            false
        )
        return DataBindingViewHolder(itemVariableId, binding)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: MutableList<Model>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: DataBindingViewHolder<Model>, position: Int) {
        val item = items[position]
        holder.bindItem(item)
    }
}