package com.safedo.converter.converter

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
class XmlToJsonConverterTest {
    @Inject
    lateinit var converter: XmlToJsonConverter

    var expectedObject = JSONObject()
        .put("testA", 123)
        .put("testB", "Hello World")
        .put(
            "testC", JSONObject()
                .put("first", "some")
                .put("second", "value")
        )

    var JsonString = "{\n" +
            "    \"testB\": \"Hello World\",\n" +
            "    \"testC\": {\n" +
            "        \"first\": \"some\",\n" +
            "        \"second\": \"value\"\n" +
            "    },\n" +
            "    \"testA\": 123\n" +
            "}"
    var XmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "  <testA>123</testA>\n" +
            "  <testB>Hello World</testB>\n" +
            "  <testC>\n" +
            "    <first>some</first>\n" +
            "    <second>value</second>\n" +
            "  </testC>"

    @Test
    fun testToObject() {
        assertEquals(converter.toObject(XmlString).toString(), expectedObject.toString())
    }

    @Test
    fun testToString() {
        assertEquals(converter.toString(expectedObject), JsonString)
    }

    @Test
    fun testConvert() {
        assertEquals(converter.convert(XmlString), JsonString)
    }
}
