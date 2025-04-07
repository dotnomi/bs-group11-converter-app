package com.safedo.converter.converter

import io.quarkus.test.junit.QuarkusTest
import netscape.javascript.JSObject
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
public class JsonToXmlConverterTest {
    @Inject
    lateinit var converter: JsonToXmlConverter

    @Test
    fun testToObject() {
        var expectedObject= JSONObject()
            .put("testA",123)
            .put("testB","Hello World")
        assertEquals()
    }
}
