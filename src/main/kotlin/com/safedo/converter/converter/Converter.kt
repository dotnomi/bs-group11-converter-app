package com.safedo.converter.converter

abstract class Converter {
    open fun convert(input: String): String {
        return toString(toObject(input))
    }

    abstract fun toObject(input: String): Any
    abstract fun toString(output: Any): String
}