package com.quartack.bdapter.annotationprocessor.printer

internal interface FilePrinter {
    val packageName: String
    val fileName: String

    fun print(): String
}
