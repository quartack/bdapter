package com.quartack.bdapter

import androidx.annotation.Keep
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

@Keep
open class BdapterViewHolder<M : Any, VM : ViewModel>(
    open val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    open fun onBindViewHolder(item: M, holder: BdapterViewHolder<M, VM>, viewModel: VM) {
    }

    open fun onViewAttachedToWindow(item: M, holder: BdapterViewHolder<M, VM>, viewModel: VM) {
    }

    open fun onViewDetachedFromWindow(holder: BdapterViewHolder<M, VM>, viewModel: VM) {
    }

    open fun onViewRecycled(holder: BdapterViewHolder<M, VM>) {
    }

    open fun onFailedToRecycleView(holder: BdapterViewHolder<M, VM>): Boolean {
        return false
    }
}
