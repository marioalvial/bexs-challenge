package br.com.bexs.backend.challenge.api.commons.errors

data class ErrorObject(
    val message: String,
    val field: String,
    val invalidParameter: Any? = null
)
