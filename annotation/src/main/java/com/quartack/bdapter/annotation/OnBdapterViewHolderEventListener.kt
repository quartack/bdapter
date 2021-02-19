package com.quartack.bdapter.annotation

open class OnBdapterViewHolderEventListener<M, V, VM> {
    // From RecyclerView.Adapter#onBindViewHolder
    open fun onBindViewHolder(item: M, holder: V, viewModel: VM) {
    }

    // From RecyclerView.Adapter#onViewAttachedToWindow
    open fun onViewAttachedToWindow(item: M, holder: V, viewModel: VM) {
    }

    // From RecyclerView.Adapter#onViewDetachedFromWindow
    open fun onViewDetachedFromWindow(holder: V, viewModel: VM) {
    }

    // From RecyclerView.Adapter#onViewRecycled
    open fun onViewRecycled(holder: V) {
    }

    // From RecyclerView.Adapter#onFailedToRecycleView
    open fun onFailedToRecycleView(holder: V): Boolean {
        return false
    }
}

class EmptyBdapterViewHolderEventListener : OnBdapterViewHolderEventListener<Any, Any, Any>()
