package br.com.bexs.backend.challenge.console.exceptions

class InvalidInputException(
    input: String
) : RuntimeException() {

    override val message = "Input [$input] is not valid"
}
