package br.com.bexs.backend.challenge.api

import br.com.bexs.backend.challenge.api.application.route.RouteModule
import br.com.bexs.backend.challenge.api.application.route.routeRoutes
import br.com.bexs.backend.challenge.api.application.setup.ConfigModule
import br.com.bexs.backend.challenge.api.commons.errors.ErrorResponse
import br.com.bexs.backend.challenge.api.commons.errors.exceptions.BexsChallengeApiException
import br.com.bexs.backend.challenge.core.domain.RouteRegister
import br.com.bexs.backend.challenge.core.exceptions.BexsChallengeCoreException
import br.com.bexs.backend.challenge.core.exceptions.MissingArgumentException
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.jackson.JacksonConverter
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.logger.PrintLogger
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { throw MissingArgumentException() }

    val filePath = args[0]

    embeddedServer(Netty, 8080) { mainModule(filePath) }.start(true)
}

fun Application.mainModule(inputFilePath: String) {
    install(Koin) {
        val graphModule = module { single { RouteRegister(inputFilePath) } }

        modules(listOf(graphModule, ConfigModule.module, RouteModule.module))
        logger(PrintLogger())
    }
    install(CallLogging)
    install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(get())) }
    install(StatusPages) {
        exception<BexsChallengeApiException> { call.respond(it.statusCode(), it.response()) }
        exception<BexsChallengeCoreException> { call.respond(BadRequest, ErrorResponse.create(it.message)) }
    }
    routing {
        routeRoutes(get())
    }
}