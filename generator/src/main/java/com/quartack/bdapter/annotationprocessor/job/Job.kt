package com.quartack.bdapter.annotationprocessor.job

import com.quartack.bdapter.annotationprocessor.printer.FilePrinter
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation

abstract class Job(
    private val processingEnv: ProcessingEnvironment
) {
    abstract val annotation: Class<out Annotation>

    fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean =
        process(
            roundEnv.getElementsAnnotatedWith(annotation),
            annotations,
            roundEnv
        )

    protected abstract fun process(
        elements: Set<Element>,
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean

    protected fun throwErrorMessage(
        message: String,
        element: Element? = null
    ): Int {
        processingEnv.messager.printMessage(
            Diagnostic.Kind.ERROR,
            "${message}\r",
            element
        )
        return -1
    }

    internal fun shouldPrint(
        printer: FilePrinter,
        element: Element
    ) {
        processingEnv.filer.createResource(
            StandardLocation.SOURCE_OUTPUT,
            printer.packageName,
            printer.fileName,
            element
        ).openWriter().use {
            it.write(printer.print())
        }
    }
}
