package com.sngular.core.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<Model>(
    private val variableItemId: Int,
    private val binding: ViewDataBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: Model) {
        binding.setVariable(variableItemId, item)
        binding.executePendingBindings()
    }
}