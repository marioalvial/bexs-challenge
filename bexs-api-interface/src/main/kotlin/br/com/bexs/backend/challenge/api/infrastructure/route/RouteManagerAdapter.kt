package br.com.bexs.backend.challenge.api.infrastructure.route

import br.com.bexs.backend.challenge.api.core.route.RouteManager
import br.com.bexs.backend.challenge.core.domain.Route
import br.com.bexs.backend.challenge.core.domain.RouteRegister
import br.com.bexs.backend.challenge.core.domain.RouterOptimizer
import org.slf4j.LoggerFactory

class RouteManagerAdapter(
    private val routeRegister: RouteRegister
) : RouteManager {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun save(source: String, destination: String, price: Int) {
        logger.info("Creating route from $source to $destination with price $price")

        routeRegister.add(source, destination, price)
    }

    override fun findBestRoute(source: String, destination: String): Route {
        logger.info("Finding best route from $source to $destination")

        val graph = routeRegister.graphOfAirportsAndConnections()

        logger.info("Searching for best route using graph $graph")

        val optimizer = RouterOptimizer(graph.airports, graph.connections)

        return optimizer.findBestPath(source, destination)
    }
}