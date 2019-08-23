package br.com.bexs.backend.challenge.console.validators

import br.com.bexs.backend.challenge.console.exceptions.InvalidInputException

object InputValidator {

    private val regex = "^([A-Za-z]){3}-([A-Za-z]){3}".toRegex()

    fun validate(input: String) {
        require(isValid(input)) { throw InvalidInputException(input) }
    }

    private fun isValid(input: String) = input.matches(regex)
}