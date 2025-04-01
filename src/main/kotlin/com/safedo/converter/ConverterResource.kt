package com.safedo.converter

import com.safedo.converter.converter.JsonToXmlConverter
import com.safedo.converter.converter.XmlToJsonConverter
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response

@Path("/v1/converter")
class ConverterResource(
    private val xmlToJsonConverter: XmlToJsonConverter,
    private val jsonToXmlConverter: JsonToXmlConverter,
) {
    @POST
    @Path("/xml/{outFormat}")
    @Consumes(MediaType.APPLICATION_XML)
    fun formatXml(
        @PathParam("outFormat") outFormat: String,
        body: String
    ): Response {
        return when (outFormat.lowercase()) {
            "json" -> {
                Response.ok().header("Content-Type", MediaType.APPLICATION_JSON)
                    .entity(xmlToJsonConverter.convert(body)).build()
            }
            else -> Response.status(Response.Status.BAD_REQUEST).build()
        }
    }

    @POST
    @Path("/json/{outFormat}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun formatJson(
        @PathParam("outFormat") outFormat: String,
        body: String
    ): Response {
        return when (outFormat.lowercase()) {
            "xml" -> {
                Response.ok().header("Content-Type", MediaType.APPLICATION_XML)
                    .entity(jsonToXmlConverter.convert(body)).build()
            }
            else -> Response.status(Response.Status.BAD_REQUEST).build()
        }
    }
}