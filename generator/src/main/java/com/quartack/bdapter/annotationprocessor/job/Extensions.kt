package com.quartack.bdapter.annotationprocessor.job

import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.MirroredTypeException

internal fun MirroredTypeException.toClassName(): String {
    val classTypeMirror = typeMirror as DeclaredType
    val classTypeElement = classTypeMirror.asElement() as TypeElement

    return classTypeElement.qualifiedName.toString()
}
