package br.com.bexs.backend.challenge.core.domain

import br.com.bexs.backend.challenge.core.exceptions.RouteAlreadyExistsException
import java.io.File

class RouteRegister(filePath: String) {

    private val file = File(filePath).also { InputFileValidator.validate(it) }

    fun graphOfAirportsAndConnections(): Graph {
        val airports = mutableSetOf<Airport>()
        val connections = mutableSetOf<Connection>()

        file.forEachLine {
            val splittedLine = it.split(",")
            val source = splittedLine[0]
            val destination = splittedLine[1]
            val price = splittedLine[2].toInt()

            val sourceAirport = Airport(source)
            val destinationAirport = Airport(destination)

            airports.add(sourceAirport)
            airports.add(destinationAirport)
            connections.add(Connection(sourceAirport, destinationAirport, price))
        }

        return Graph(airports, connections)
    }

    fun add(source: String, destination: String, price: Int) {
        val graph = graphOfAirportsAndConnections()

        checkIfExists(source, destination, graph.connections)
        addToFile(source, destination, price)
    }

    private fun addToFile(source: String, destination: String, price: Int) {
        file.appendText("\n")
        file.appendText("$source,$destination,$price")
    }

    private fun checkIfExists(source: String, destination: String, connections: Set<Connection>) {
        require(!connections.any { it.source.name == source && it.destination.name == destination }) {
            throw RouteAlreadyExistsException(source, destination)
        }
    }
}
