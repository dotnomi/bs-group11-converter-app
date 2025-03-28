package com.safedo.converter

import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/v1/converter")
class ConverterResource {
    @POST
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    fun fromYamlToJson() = "Hello from Quarkus REST"

    @POST()
    @Produces(MediaType.TEXT_PLAIN)
    fun fromJsonToYaml() = "Hello from Quarkus REST"
}