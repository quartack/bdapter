package com.quartack.bdapter.annotationprocessor

import com.quartack.bdapter.annotationprocessor.job.ViewHolderJob
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

class BdapterAnnotationProcessor : AbstractProcessor() {
    private val annotationJobs by lazy {
        setOf(
            ViewHolderJob(processingEnv)
        )
    }

    override fun getSupportedAnnotationTypes() =
        annotationJobs
            .map {
                it.annotation.canonicalName
            }.toSet()

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {
        if (annotations.isNullOrEmpty()) return true

        val failed = annotationJobs.filter {
            !it.process(annotations, roundEnv)
        }

        return failed.isNotEmpty()
    }
}
