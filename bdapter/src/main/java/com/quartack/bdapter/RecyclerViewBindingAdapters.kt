package com.quartack.bdapter

import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(
    "binding:bdapterItems",
    "binding:bdapterViewModel",
    "binding:bdapterDiffUtil",
    "binding:bdapterChangedItemsPosition",
    requireAll = false
)
fun RecyclerView.initBdapter(
    items: List<Any>?,
    viewModel: ViewModel?,
    diffUtil: DiffUtil.ItemCallback<Any?>?,
    changedItems: List<Int>?
) {
    initBindingAdapter(diffUtil)

    items?.let {
        with(adapter as Bdapter<*, *>) {
            submitList(items)
        }
    }

    viewModel?.let {
        with(adapter as Bdapter<*, *>) {
            this.viewModel = viewModel
        }
    }

    changedItems?.forEach { position ->
        (findViewHolderForAdapterPosition(position) as? BdapterViewHolder<*, *>)?.binding?.invalidateAll()
    }
}

private fun RecyclerView.initBindingAdapter(diffUtil: DiffUtil.ItemCallback<Any?>?) {
    if (null == layoutManager) layoutManager = LinearLayoutManager(context)
    if (null == adapter) adapter = Bdapter<Any, ViewModel>(diffUtil ?: basicDiffUtil)
}
