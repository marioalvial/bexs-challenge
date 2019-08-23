package br.com.bexs.backend.challenge.api.core.route

class RouteService(
    private val routeManager: RouteManager
) {

    fun findBestRoute(source: String, destination: String) = routeManager.findBestRoute(source, destination)

    fun createRoute(routeRequest: RouteRequest) = routeManager
        .save(routeRequest.source, routeRequest.destination, routeRequest.price)
}
