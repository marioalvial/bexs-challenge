package br.com.bexs.backend.challenge.api.application.route

import br.com.bexs.backend.challenge.api.core.route.RouteManager
import br.com.bexs.backend.challenge.api.core.route.RouteService
import br.com.bexs.backend.challenge.api.infrastructure.route.RouteManagerAdapter
import org.koin.dsl.module

object RouteModule {

    val module = module {
        single<RouteManager> { RouteManagerAdapter(get()) }
        single { RouteService(get()) }
        single { RouteController(get(), get()) }
    }
}