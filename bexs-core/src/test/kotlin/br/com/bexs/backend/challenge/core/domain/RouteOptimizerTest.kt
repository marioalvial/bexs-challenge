package br.com.bexs.backend.challenge.core.domain

import br.com.bexs.backend.challenge.core.exceptions.AirportNotExistsException
import br.com.bexs.backend.challenge.core.exceptions.PathNotExistsException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test

class RouteOptimizerTest {

    private val filePath = ClassLoader.getSystemResource("valid_input.txt").file
    private val routeRegister = RouteRegister(filePath)
    private val graph = routeRegister.graphOfAirportsAndConnections()

    @Test
    fun `given valid source and destination should find the best path`() {
        val optimizer = RouterOptimizer(graph.airports, graph.connections)

        val route = optimizer.findBestPath("GRU", "CDG")

        assertThat(route.path).isEqualTo("GRU - BRC - SCL - ORL - CDG")
        assertThat(route.price).isEqualTo(40)
    }

    @Test
    fun `given non existent airport should throw AirportNotExistsException`() {
        val optimizer = RouterOptimizer(graph.airports, graph.connections)

        assertThatExceptionOfType(AirportNotExistsException::class.java)
            .isThrownBy { optimizer.findBestPath("ZZZ", "CDG") }
            .withMessage("Airport [ZZZ] does not exists")
    }

    @Test
    fun `given same value for source and destination should return price equal 0`() {
        val optimizer = RouterOptimizer(graph.airports, graph.connections)

        val route = optimizer.findBestPath("GRU", "GRU")

        assertThat(route.path).isEqualTo("GRU - GRU")
        assertThat(route.price).isEqualTo(0)
    }

    @Test
    fun `given an impossible path from source to destination should throw PathNotExistsException`() {
        val optimizer = RouterOptimizer(graph.airports, graph.connections)

        assertThatExceptionOfType(PathNotExistsException::class.java)
            .isThrownBy { optimizer.findBestPath("CDG", "ORL") }
            .withMessage("Path from CDG to ORL does not exists")
    }
}