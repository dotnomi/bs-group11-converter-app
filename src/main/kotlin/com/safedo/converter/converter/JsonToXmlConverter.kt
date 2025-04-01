package com.safedo.converter.converter

import jakarta.enterprise.context.ApplicationScoped
import org.json.JSONObject
import org.json.XML

@ApplicationScoped
class JsonToXmlConverter: Converter() {
    override fun toObject(input: String): Any {
        return JSONObject(input)
    }

    override fun toString(output: Any): String {
        val jsonObject = output as JSONObject
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>${XML.toString(jsonObject)}"
    }
}