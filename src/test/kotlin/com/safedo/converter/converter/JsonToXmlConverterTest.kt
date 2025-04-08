package com.safedo.converter.converter

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
class JsonToXmlConverterTest {
    @Inject
    lateinit var converter: JsonToXmlConverter
    var expectedObject = JSONObject()
        .put("testA", 123)
        .put("testB", "Hello World")
        .put(
            "testC", JSONObject()
                .put("first", "some")
                .put("second", "value")
        )

    var JsonString = "{\n" +
            "  \"testA\": 123,\n" +
            "  \"testB\": \"Hello World\",\n" +
            "  \"testC\": {\n" +
            "    \"first\": \"some\",\n" +
            "    \"second\": \"value\"\n" +
            "  }\n" +
            "}"
    var XmlString =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><testB>Hello World</testB><testC><first>some</first><second>value</second></testC><testA>123</testA>"


    @Test
    fun testToObject() {
        assertEquals(converter.toObject(JsonString).toString(), expectedObject.toString())
    }

    @Test
    fun testToString() {
        assertEquals(converter.toString(expectedObject), XmlString)
    }

    @Test
    fun testConvert() {
        assertEquals(converter.convert(JsonString), XmlString)
    }
}
