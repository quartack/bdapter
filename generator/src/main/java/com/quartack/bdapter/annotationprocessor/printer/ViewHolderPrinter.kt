package com.quartack.bdapter.annotationprocessor.printer

import com.quartack.bdapter.annotation.BDAPTER_VIEW_HOLDER_CLASS
import com.quartack.bdapter.annotation.BDAPTER_VIEW_HOLDER_PACKAGE

class ViewHolderPrinter(
    override val packageName: String,
    private val className: String,
    private val modelClassPath: String,
    private val viewModelClass: String,
    private val viewModelVariableName: String,
    private val itemVariableName: String,
    private val dataBinding: String,
    private val eventListener: String
) : FilePrinter {
    override val fileName = "$className.kt"

    override fun print() =
        """package $packageName

import androidx.annotation.Keep
import $BDAPTER_VIEW_HOLDER_PACKAGE.$BDAPTER_VIEW_HOLDER_CLASS
import $modelClassPath as Model
import $dataBinding as DataBinding
import $viewModelClass as ViewModel
import $eventListener as EventListener

@Keep
class $className(override val binding: DataBinding) :
    $BDAPTER_VIEW_HOLDER_CLASS<Model, ViewModel>(binding) {

    private val eventListener = EventListener()

    override fun onBindViewHolder(
        item: Model,
        holder: BdapterViewHolder<Model, ViewModel>,
        viewModel: ViewModel
    ) {
        binding.$itemVariableName = item
        binding.$viewModelVariableName = viewModel
        binding.executePendingBindings()

        eventListener.onBindViewHolder(item, holder as $className, viewModel)
    }

    override fun onViewAttachedToWindow(
        item: Model,
        holder: BdapterViewHolder<Model, ViewModel>,
        viewModel: ViewModel
    ) {
        eventListener.onViewAttachedToWindow(item, holder as $className, viewModel)
    }

    override fun onViewDetachedFromWindow(
        holder: BdapterViewHolder<Model, ViewModel>,
        viewModel: ViewModel
    ) {
        eventListener.onViewDetachedFromWindow(holder as $className, viewModel)
    }

    override fun onViewRecycled(holder: BdapterViewHolder<Model, ViewModel>) {
        eventListener.onViewRecycled(holder as $className)
    }

    override fun onFailedToRecycleView(holder: BdapterViewHolder<Model, ViewModel>): Boolean {
        return eventListener.onFailedToRecycleView(holder as $className)
    }
}
"""
}
