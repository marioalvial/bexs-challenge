package br.com.bexs.backend.challenge.api.componentTest

import br.com.bexs.backend.challenge.api.mainModule
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.io.File
import kotlin.system.exitProcess

class RouteComponentTest {

    companion object {
        lateinit var tempFile: File

        @JvmStatic
        @BeforeClass
        fun classSetup() {
            val file = File(ClassLoader.getSystemResource("valid_input.txt").file)
            tempFile = File("src/test/resources/temp-input-file.txt").apply {
                file.forEachLine { line ->
                    appendText(line)
                    appendText("\n")
                }
            }
        }

        @JvmStatic
        @AfterClass
        fun classTearDown() {
            tempFile.delete()
            exitProcess(0)
        }
    }

    @Test
    fun `given valid request to find best route should return it with status code equal to 200`() {
        val expected = ClassLoader.getSystemResource("success_find_best_route_response.json").readText()

        withTestApplication({ this.mainModule(tempFile.path) }) {
            val response = handleRequest(HttpMethod.Get, "/routes?source=GRU&destination=CDG").response

            assertThat(response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.content).isEqualToIgnoringWhitespace(expected)
        }
    }

    @Test
    fun `given request without destination parameter should throw ParameterCaptureException`() {
        val expected = ClassLoader.getSystemResource("parameter_capture_exception_response.json").readText()

        withTestApplication({ this.mainModule(tempFile.path) }) {
            val response = handleRequest(HttpMethod.Get, "/routes?source=GRU").response

            assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
            assertThat(response.content).isEqualToIgnoringWhitespace(expected)
        }
    }

    @Test
    fun `given request with an impossible path should throw BexsChallengeCoreException`() {
        val expected = ClassLoader.getSystemResource("bexs_core_exception_response.json").readText()

        withTestApplication({ this.mainModule(tempFile.path) }) {
            val response = handleRequest(HttpMethod.Get, "/routes?source=CDG&&destination=ORL").response

            assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
            assertThat(response.content).isEqualToIgnoringWhitespace(expected)
        }
    }

    @Test
    fun `given valid request to create a new route should execute it with success and return 201`() {
        val json = ClassLoader.getSystemResource("valid_create_route_request.json").readText()

        withTestApplication({ this.mainModule(tempFile.path) }) {
            val response = handleRequest(HttpMethod.Post, "/routes") {
                setBody(json)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }.response

            println(response.content)

            assertThat(response.status()).isEqualTo(HttpStatusCode.Created)
        }
    }

    @Test
    fun `given invalid request to create a new route should throw MethodArgumentNotValidException`() {
        val json = ClassLoader.getSystemResource("invalid_create_route_request.json").readText()
        val expected = ClassLoader.getSystemResource("invalid_create_route_response.json").readText()

        withTestApplication({ this.mainModule(tempFile.path) }) {
            val response = handleRequest(HttpMethod.Post, "/routes") {
                setBody(json)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }.response

            assertThat(response.status()).isEqualTo(HttpStatusCode.BadRequest)
            assertThat(response.content).isEqualToIgnoringWhitespace(expected)
        }
    }
}