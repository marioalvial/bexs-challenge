package br.com.bexs.backend.challenge.api.commons.errors.exceptions

import br.com.bexs.backend.challenge.api.commons.errors.ErrorObject
import br.com.bexs.backend.challenge.api.commons.errors.ErrorResponse
import io.ktor.http.HttpStatusCode

class MethodArgumentNotValidException(
    private val errors: List<ErrorObject>
) : BexsChallengeApiException() {

    override fun response() = ErrorResponse
        .create("Request cannot be processed because it contains invalid data", "errors" to errors)

    override fun statusCode() = HttpStatusCode.BadRequest
}
