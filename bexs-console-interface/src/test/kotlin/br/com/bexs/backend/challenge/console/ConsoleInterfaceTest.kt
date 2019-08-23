package br.com.bexs.backend.challenge.console

import br.com.bexs.backend.challenge.console.exceptions.InvalidInputException
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test
import java.util.Scanner

class ConsoleInterfaceTest {

    private val filePath = ClassLoader.getSystemResource("valid_input.txt").file

    @Test
    fun `given valid input should print best route and exit`() {
        val scanner = Scanner("GRU-CDG exit")
        val console = ConsoleInterface(scanner)

        assertThatCode { console.start(filePath) }.doesNotThrowAnyException()
    }

    @Test
    fun `given input in invalid format should throw InvalidInputException`() {
        val scanner = Scanner("GRUG-CDG")
        val console = ConsoleInterface(scanner)

        assertThatExceptionOfType(InvalidInputException::class.java)
            .isThrownBy { console.start(filePath) }
            .withMessage("Input [GRUG-CDG] is not valid")
    }
}