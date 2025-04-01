package com.safedo.converter

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jakarta.ws.rs.core.MediaType
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.IOException
import java.io.InputStream

@QuarkusTest
class ConverterResourceTest {
    @ParameterizedTest
    @CsvSource(value = [
        "json,orderCreationRequest.xml,orderCreationRequestResult.json,200",
        "json,orderModificationRequest.xml,orderModificationRequestResult.json,200",
        "json,orderDeletionRequest.xml,orderDeletionRequestResult.json,200",
    ])
    fun shouldFormatXmlToJson(formatParameter: String, inputFile: String, resultFile: String, statusCode: Int) {
        val inputXml: String = getResourceText("/test/converter/xml/$inputFile")
        val expectedBody: String = getResourceText("/test/converter/xml/$resultFile")

        Given {
            contentType(MediaType.APPLICATION_XML)
            body(inputXml)
        } When {
            post("/v1/converter/xml/$formatParameter")
        } Then {
            assertEquals(
                statusCode,
                extract().statusCode(),
                "Expected statusCode to be $statusCode"
            )

            assertEquals(
                expectedBody.replace("\\s".toRegex(), "").replace("\n", ""),
                extract().body().asString().replace("\\s".toRegex(), "").replace("\n", ""),
                "Expected body to be \"$expectedBody\""
            )
        }
    }

    @ParameterizedTest
    @CsvSource(value = [
        "json,${MediaType.APPLICATION_XML},<x>bla</x>,200",
        "nan,${MediaType.APPLICATION_XML},<x>bla</x>,400",
        "json,${MediaType.APPLICATION_JSON},<x>bla</x>,415",
        "json,${MediaType.APPLICATION_XML},${null},200",
        "json,${MediaType.APPLICATION_XML},<x>wrong<y>,500",
    ])
    fun shouldNotFormatXmlToJson(formatParameter: String, mediaType: String, body: String, statusCode: Int) {
        Given {
            contentType(mediaType)
            body(body)
        } When {
            post("/v1/converter/xml/$formatParameter")
        } Then {
            assertEquals(
                statusCode,
                extract().statusCode(),
                "Expected statusCode to be $statusCode"
            )
        }
    }

    @ParameterizedTest
    @CsvSource(value = [
        "xml,orderCreationResponse.json,orderCreationResponseResult.xml,200",
        "xml,orderModificationResponse.json,orderModificationResponseResult.xml,200",
        "xml,orderDeletionResponse.json,orderDeletionResponseResult.xml,200",
    ])
    fun shouldFormatJsonToXml(formatParameter: String, inputFile: String, resultFile: String, statusCode: Int) {
        val inputJson: String = getResourceText("/test/converter/json/$inputFile")
        val expectedBody: String = getResourceText("/test/converter/json/$resultFile")

        Given {
            contentType(MediaType.APPLICATION_JSON)
            body(inputJson)
        } When {
            post("/v1/converter/json/$formatParameter")
        } Then {
            assertEquals(
                statusCode,
                extract().statusCode(),
                "Expected statusCode to be $statusCode"
            )

            assertEquals(
                expectedBody.replace("\\s".toRegex(), "").replace("\n", ""),
                extract().body().asString().replace("\\s".toRegex(), "").replace("\n", ""),
                "Expected body to be \"$expectedBody\""
            )
        }
    }

    @ParameterizedTest
    @CsvSource(value = [
        "xml,${MediaType.APPLICATION_JSON},{},200",
        "nan,${MediaType.APPLICATION_JSON},{},400",
        "xml,${MediaType.APPLICATION_XML},{},415",
        "xml,${MediaType.APPLICATION_JSON},${null},500",
        "xml,${MediaType.APPLICATION_JSON},wrong format,500",
    ])
    fun shouldNotFormatJsonToXml(formatParameter: String, mediaType: String, body: String, statusCode: Int) {
        Given {
            contentType(mediaType)
            body(body)
        } When {
            post("/v1/converter/json/$formatParameter")
        } Then {
            assertEquals(
                statusCode,
                extract().statusCode(),
                "Expected statusCode to be $statusCode"
            )
        }
    }

    private fun getResourceText(path: String): String {
        val resourceStream: InputStream? = this::class.java.getResourceAsStream(path)
        if (resourceStream == null) {
            fail("Test resource not found at path: '$path'. Check classpath and path string.")
        }

        return try {
            resourceStream.bufferedReader().use { reader -> reader.readText() }
        } catch (exception: IOException) {
            fail("Failed to read test resource '$path': ${exception.message}", exception)
        }
    }
}