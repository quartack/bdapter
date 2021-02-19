package com.quartack.bdapter.annotation

import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

const val BDAPTER_VIEW_HOLDER_PACKAGE = "com.quartack.bdapter"
const val BDAPTER_VIEW_HOLDER_CLASS = "BdapterViewHolder"

fun Element.getPackageNameOfElement(): String {
    var item: Element = this
    while (item.kind !== ElementKind.PACKAGE) {
        item = item.enclosingElement
    }
    return item.toString()
}

fun createViewHolderClassName(element: Element) =
    createViewHolderClassName(
        element.toString()
            .replace(element.getPackageNameOfElement(), "")
            .replace(".", "")
    )

fun <T> createViewHolderClassName(klass: Class<T>) =
    createViewHolderClassName(
        klass.name.replace("$", "")
    )

fun createViewHolderClassName(modelClass: String) =
    "$modelClass$BDAPTER_VIEW_HOLDER_CLASS"
