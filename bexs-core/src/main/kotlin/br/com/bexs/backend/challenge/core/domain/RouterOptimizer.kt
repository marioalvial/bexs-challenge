package br.com.bexs.backend.challenge.core.domain

import br.com.bexs.backend.challenge.core.exceptions.AirportNotExistsException
import br.com.bexs.backend.challenge.core.exceptions.PathNotExistsException
import br.com.bexs.backend.challenge.core.exceptions.PriceForDestinationNotFoundException
import java.util.LinkedList

class RouterOptimizer(
    private val airport: Set<Airport>,
    private val connections: Set<Connection>
) {

    private val settledAirports: MutableSet<Airport> = mutableSetOf()
    private val unsettledAirports: MutableSet<Airport> = mutableSetOf()
    private val predecessors: MutableMap<Airport, Airport> = mutableMapOf()
    private val priceFromSource: MutableMap<Airport, Int> = mutableMapOf()

    fun findBestPath(source: String, destination: String): Route {
        val sourceAirport = Airport(source)
        val destinationAirport = Airport(destination)

        verifyIfAirportExists(sourceAirport)
        verifyIfAirportExists(destinationAirport)

        if (sourceAirport == destinationAirport) return Route("$source - $destination", 0)

        executeAlgorithm(sourceAirport)

        val (path: LinkedList<Airport>, price) = getPath(sourceAirport, destinationAirport)
        val formattedPath = path.joinToString(" - ") { it.name }

        return Route(formattedPath, price)
    }

    private fun verifyIfAirportExists(sourceAirport: Airport) {
        require(airport.contains(sourceAirport)) { throw AirportNotExistsException(sourceAirport.name) }
    }

    private fun executeAlgorithm(source: Airport) {
        priceFromSource[source] = 0
        unsettledAirports.add(source)

        while (unsettledAirports.size > 0) {
            getAirportWithLowestPrice(unsettledAirports).also {
                settledAirports.add(it)
                unsettledAirports.remove(it)
                findMinimalPrice(it)
            }
        }
    }

    private fun getPath(source: Airport, destination: Airport): Pair<LinkedList<Airport>, Int> {
        val path = LinkedList<Airport>()
        var step = destination

        if (predecessors[step] == null) throw PathNotExistsException(source.name, destination.name)

        path.add(step)

        while (predecessors[step] != null) {
            step = predecessors[step]!!
            path.add(step)
        }

        path.reverse()

        val price = priceFromSource[destination] ?: throw PriceForDestinationNotFoundException(source, destination)

        return path to price
    }

    private fun findMinimalPrice(airport: Airport) = getAdjacentAirports(airport).forEach {
        if (getPrice(it) > (getPrice(airport) + getPriceBetweenAirports(airport, it))) {
            priceFromSource[it] = getPrice(airport) + getPriceBetweenAirports(airport, it)
            predecessors[it] = airport
            unsettledAirports.add(it)
        }
    }

    private fun getPriceBetweenAirports(source: Airport, destination: Airport): Int = connections
        .firstOrNull { it.source == source && it.destination == destination }
        ?.price
        ?: throw PathNotExistsException(source.name, destination.name)

    private fun getAdjacentAirports(airport: Airport): MutableList<Airport> = connections
        .fold(mutableListOf()) { adjacents, connection ->
            if (connection.source == airport && !isSettled(connection.destination)) {
                adjacents.add(connection.destination)
            }
            adjacents
        }

    private fun getAirportWithLowestPrice(unsettledAirports: Set<Airport>): Airport {
        var airport: Airport = unsettledAirports.first()

        unsettledAirports.forEach { if (getPrice(it) < getPrice(airport)) airport = it }

        return airport
    }

    private fun isSettled(airport: Airport): Boolean = settledAirports.contains(airport)

    private fun getPrice(destination: Airport?): Int = priceFromSource[destination] ?: Integer.MAX_VALUE
}