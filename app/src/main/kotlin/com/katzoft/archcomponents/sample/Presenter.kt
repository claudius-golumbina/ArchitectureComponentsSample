package com.katzoft.archcomponents.sample

interface Presenter<T> {
    var view: T?
    fun bind(view: T)
    fun unbind()
}