package com.safedo.converter.converter

interface Converter<F, T> {
    fun convert(value: F): T
}