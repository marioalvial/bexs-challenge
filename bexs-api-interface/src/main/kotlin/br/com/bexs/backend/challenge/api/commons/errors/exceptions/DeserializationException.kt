package br.com.bexs.backend.challenge.api.commons.errors.exceptions

import br.com.bexs.backend.challenge.api.commons.errors.ErrorResponse
import io.ktor.http.HttpStatusCode

class DeserializationException(
    private val json: String,
    private val clazzName: String
) : BexsChallengeApiException() {

    override fun response() = ErrorResponse.create("Cannot deserialize json to requested class",
        "json" to json,
        "requested_class" to clazzName
    )

    override fun statusCode() = HttpStatusCode.BadRequest
}
