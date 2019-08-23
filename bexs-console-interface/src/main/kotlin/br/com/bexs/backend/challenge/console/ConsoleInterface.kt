package br.com.bexs.backend.challenge.console

import br.com.bexs.backend.challenge.console.validators.InputValidator
import br.com.bexs.backend.challenge.core.domain.RouteRegister
import br.com.bexs.backend.challenge.core.domain.RouterOptimizer
import java.util.Scanner

class ConsoleInterface(
    private val scanner: Scanner
) {

    fun start(inputFilePath: String) {
        val register = RouteRegister(inputFilePath)

        println("Welcome to Bexs Console Interface. If you want to quit, please type 'exit'")

        while (true) {
            print("please enter the route: ")

            val input = scanner.next()

            if (input == "exit") break

            InputValidator.validate(input)

            val (source, destination) = getParameters(input)

            val graph = register.graphOfAirportsAndConnections()
            val route = RouterOptimizer(graph.airports, graph.connections).findBestPath(source, destination)

            val formattedPriceResponse = "\$${route.price}"

            println("best route: ${route.path} > $formattedPriceResponse")
        }

        scanner.close()
        print("Good Bye!")
    }

    private fun getParameters(input: String) = input
        .split("-")
        .let { it[0] to it[1] }
}