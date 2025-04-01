package com.safedo.converter.converter

import jakarta.enterprise.context.ApplicationScoped
import org.json.JSONObject
import org.json.XML

@ApplicationScoped
class XmlToJsonConverter: Converter() {
    override fun toObject(input: String): Any {
        return XML.toJSONObject(input)
    }

    override fun toString(output: Any): String {
        val jsonObject = output as JSONObject
        return jsonObject.toString(4)
    }
}