package br.com.bexs.backend.challenge.api.commons.errors.exceptions

import br.com.bexs.backend.challenge.api.commons.errors.ErrorResponse
import io.ktor.http.HttpStatusCode

class ParameterCaptureException(
    private val parameter: String
) : BexsChallengeApiException() {

    override fun response() = ErrorResponse.create(
        "It wasn't possible to capture value from parameter",
        "parameter" to parameter
    )

    override fun statusCode() = HttpStatusCode.BadRequest
}
