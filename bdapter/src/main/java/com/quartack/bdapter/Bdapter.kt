package com.quartack.bdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.quartack.bdapter.annotation.createViewHolderClassName
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import com.quartack.bdapter.annotation.BdapterViewHolder as BdapterViewHolderAnnotation

val basicDiffUtil = object : DiffUtil.ItemCallback<Any?>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
}

@Keep
@Suppress("UNCHECKED_CAST")
class Bdapter<M : Any, VM : ViewModel>(
    diffUtil: DiffUtil.ItemCallback<Any?>
) : ListAdapter<Any, BdapterViewHolder<M, VM>>(diffUtil) {
    var viewModel: ViewModel? = null

    private val viewTypeCounter = AtomicInteger(1)
    private val viewTypeMap: MutableMap<Class<out Any>, Int> = mutableMapOf()
    private val inflaterMap: MutableMap<Int, KFunction<*>> = mutableMapOf()
    private val viewHolderMap: MutableMap<Int, KClass<out BdapterViewHolder<M, VM>>> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BdapterViewHolder<M, VM> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflaterMap[viewType]!!.call(inflater, parent, false)
        return viewHolderMap[viewType]!!.constructors.first().call(binding)
    }

    public override fun getItem(position: Int): Any {
        return super.getItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewType(getItem(position)::class.java)
    }

    fun getItemViewType(klass: Class<*>): Int {
        if (viewTypeMap[klass] == null) {
            with(klass.findBdapterViewHolderAnnotation()) {
                val viewTypeCode = viewTypeCounter.incrementAndGet()
                viewTypeMap[klass] = viewTypeCode
                inflaterMap[viewTypeCode] = dataBinding.findInflateFunction()
                viewHolderMap[viewTypeCode] =
                    Class.forName(createViewHolderClassName(klass)).kotlin as KClass<out BdapterViewHolder<M, VM>>
            }
        }

        return viewTypeMap[klass]!!
    }

    fun getViewHolderByItemType(viewType: Int) = viewHolderMap[viewType]

    override fun onBindViewHolder(holder: BdapterViewHolder<M, VM>, position: Int) {
        holder.onBindViewHolder(
            getItem(holder.adapterPosition) as M,
            holder,
            viewModel as VM
        )
    }

    override fun onViewAttachedToWindow(holder: BdapterViewHolder<M, VM>) {
        holder.onViewAttachedToWindow(
            getItem(holder.adapterPosition) as M,
            holder,
            viewModel as VM
        )
    }

    override fun onViewDetachedFromWindow(holder: BdapterViewHolder<M, VM>) {
        holder.onViewDetachedFromWindow(holder, viewModel as VM)
    }

    override fun onViewRecycled(holder: BdapterViewHolder<M, VM>) {
        holder.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: BdapterViewHolder<M, VM>): Boolean {
        return holder.onFailedToRecycleView(holder)
    }

    private fun Class<*>.findBdapterViewHolderAnnotation() =
        annotations.firstOrNull {
            it is BdapterViewHolderAnnotation
        } as? BdapterViewHolderAnnotation
            ?: throw IllegalAccessException("Declare the @BdapterViewHolder annotation in the $canonicalName")

    private fun KClass<*>.findInflateFunction() =
        declaredFunctions.first {
            it.name == "inflate" && it.parameters.size == 3
        }
}
