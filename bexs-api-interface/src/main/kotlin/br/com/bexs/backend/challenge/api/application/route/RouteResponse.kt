package br.com.bexs.backend.challenge.api.application.route

import br.com.bexs.backend.challenge.core.domain.Route

class RouteResponse(
    route: Route
) {

    val path: String = route.path
    val price: Int = route.price

    override fun toString() = "RouteResponse(path='$path', price=$price)"
}
