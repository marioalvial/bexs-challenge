package br.com.bexs.backend.challenge.api.commons.errors.exceptions

import br.com.bexs.backend.challenge.api.commons.errors.ErrorResponse
import io.ktor.http.HttpStatusCode

abstract class BexsChallengeApiException : RuntimeException() {

    abstract fun response(): ErrorResponse

    abstract fun statusCode(): HttpStatusCode
}
