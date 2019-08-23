package br.com.bexs.backend.challenge.core.domain

import br.com.bexs.backend.challenge.core.exceptions.InvalidFileException
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test
import java.io.File

class InputFileValidatorTest {

    @Test
    fun `given input file in a valid format should validate with success`() {
        val file = File(ClassLoader.getSystemResource("valid_input.txt").file)

        assertThatCode { InputFileValidator.validate(file) }.doesNotThrowAnyException()
    }

    @Test
    fun `given input file in an invalid format should throw InvalidFileException`() {
        val file = File(ClassLoader.getSystemResource("invalid_input.txt").file)

        assertThatExceptionOfType(InvalidFileException::class.java)
            .isThrownBy { InputFileValidator.validate(file) }
            .withMessage("Line [SCL,CDGG,2] of file invalid_input.txt is in wrong format")
    }
}