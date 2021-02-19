package com.quartack.bdapter.annotationprocessor.job

import com.quartack.bdapter.annotation.BdapterViewHolder
import com.quartack.bdapter.annotation.createViewHolderClassName
import com.quartack.bdapter.annotation.getPackageNameOfElement
import com.quartack.bdapter.annotationprocessor.printer.ViewHolderPrinter
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException

class ViewHolderJob(
    processingEnv: ProcessingEnvironment
) : Job(processingEnv) {
    override val annotation = BdapterViewHolder::class.java

    override fun process(
        elements: Set<Element>,
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        elements.forEach { element ->
            ViewHolderPrinter(
                element.getPackageNameOfElement(),
                createViewHolderClassName(element),
                element.toString(),
                element.getViewModelClass(),
                element.getviewModelVariableName(),
                element.getItemVariableName(),
                element.getDataBindingClass(),
                element.getEventListenerClass()
            ).also { print ->
                shouldPrint(print, element)
            }
        }

        return true
    }

    private fun Element.getDataBindingClass() =
        try {
            getAnnotation(annotation).dataBinding.toString()
        } catch (e: MirroredTypeException) {
            e.toClassName()
        }

    private fun Element.getViewModelClass() =
        try {
            getAnnotation(annotation).viewModelClass.toString()
        } catch (e: MirroredTypeException) {
            e.toClassName()
        }

    private fun Element.getviewModelVariableName() =
        try {
            getAnnotation(annotation).viewModelVariableName
        } catch (e: MirroredTypeException) {
            e.toClassName()
        }

    private fun Element.getItemVariableName() =
        try {
            getAnnotation(annotation).itemVariableName
        } catch (e: MirroredTypeException) {
            e.toClassName()
        }

    private fun Element.getEventListenerClass() =
        try {
            getAnnotation(annotation).eventListener.toString()
        } catch (e: MirroredTypeException) {
            e.toClassName()
        }
}
