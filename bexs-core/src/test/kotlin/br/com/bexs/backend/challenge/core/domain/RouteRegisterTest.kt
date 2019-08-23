package br.com.bexs.backend.challenge.core.domain

import br.com.bexs.backend.challenge.core.exceptions.RouteAlreadyExistsException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import java.time.Instant.now

class RouteRegisterTest {

    private lateinit var tempInputFile: File

    @Before
    fun setup() {
        tempInputFile = File("src/test/resources/${now().toEpochMilli()}.txt").apply { writeText("GRU,BRC,10") }
    }

    @After
    fun tearDown() {
        tempInputFile.delete()
    }

    @Test
    fun `given valid input file should create graph with airports and connections`() {
        val filePath = ClassLoader.getSystemResource("valid_input.txt").file
        val routeRegister = RouteRegister(filePath)

        val graph = routeRegister.graphOfAirportsAndConnections()

        assertThat(graph.airports).hasSize(5)
        assertThat(graph.connections).hasSize(7)
    }

    @Test
    fun `given input file with duplicate connections should only save one of them`() {
        val filePath = ClassLoader.getSystemResource("duplicate_input.txt").file
        val routeRegister = RouteRegister(filePath)

        val graph = routeRegister.graphOfAirportsAndConnections()

        assertThat(graph.airports).hasSize(5)
        assertThat(graph.connections).hasSize(7)
    }

    @Test
    fun `given new connection should add it to input file`() {
        val routeRegister = RouteRegister(tempInputFile.path)

        routeRegister.add("SCL", "CDG", 2)

        val graph = routeRegister.graphOfAirportsAndConnections()

        assertThat(graph.airports).hasSize(4)
        assertThat(graph.connections).hasSize(2)
    }

    @Test
    fun `given existent connection should throw RouteAlreadyExistsException`() {
        val routeRegister = RouteRegister(tempInputFile.path)

        assertThatExceptionOfType(RouteAlreadyExistsException::class.java)
            .isThrownBy { routeRegister.add("GRU", "BRC", 10) }
            .withMessage("Route from GRU to BRC already exist")
    }
}