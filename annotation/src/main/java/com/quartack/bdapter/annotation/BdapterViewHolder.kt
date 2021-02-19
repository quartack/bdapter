package com.quartack.bdapter.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BdapterViewHolder(
    val dataBinding: KClass<*>,
    val viewModelClass: KClass<*>,
    val viewModelVariableName: String = "viewModel",
    val itemVariableName: String = "item",
    val eventListener: KClass<out OnBdapterViewHolderEventListener<*, *, *>> =
        EmptyBdapterViewHolderEventListener::class
)
