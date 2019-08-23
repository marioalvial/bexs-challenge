package br.com.bexs.backend.challenge.api.application.route

import br.com.bexs.backend.challenge.api.commons.errors.exceptions.ParameterCaptureException
import br.com.bexs.backend.challenge.api.commons.extensions.readAsTypedValue
import br.com.bexs.backend.challenge.api.commons.extensions.validation
import br.com.bexs.backend.challenge.api.core.route.RouteRequest
import br.com.bexs.backend.challenge.api.core.route.RouteService
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import org.slf4j.LoggerFactory
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.functions.isPositive

class RouteController(
    private val routeService: RouteService,
    private val mapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    suspend fun getRoute(call: ApplicationCall) {
        val source = call.parameters["source"] ?: throw ParameterCaptureException("source")
        val destination = call.parameters["destination"] ?: throw ParameterCaptureException("destination")

        logger.info("Starting get best route process with source $source and destination $destination")

        val response = RouteResponse(routeService.findBestRoute(source, destination))

        logger.info("Finished get best route process with response $response")

        call.respond(HttpStatusCode.OK, response)
    }

    suspend fun createRoute(call: ApplicationCall) {
        val json = call.receiveText()

        logger.info("Starting route creation process with json $json")

        val routeRequest = mapper.readAsTypedValue<RouteRequest>(json).validation {
            validate(RouteRequest::source).isNotNull().isNotBlank()
            validate(RouteRequest::destination).isNotNull().isNotBlank()
            validate(RouteRequest::price).isNotNull().isPositive()
        }

        logger.info("Json deserialized to $routeRequest")

        routeService.createRoute(routeRequest)

        logger.info("Finished route creation process")

        call.respond(HttpStatusCode.Created)
    }
}