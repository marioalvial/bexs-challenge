package br.com.bexs.backend.challenge.api.application.route

import io.ktor.application.call
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route

fun Routing.routeRoutes(routeController: RouteController) {
    route("/routes") {
        get { routeController.getRoute(call) }
        post { routeController.createRoute(call) }
    }
}