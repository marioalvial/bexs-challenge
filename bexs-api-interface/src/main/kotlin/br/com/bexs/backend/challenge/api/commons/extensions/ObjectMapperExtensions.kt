package br.com.bexs.backend.challenge.api.commons.extensions

import br.com.bexs.backend.challenge.api.commons.errors.exceptions.DeserializationException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

inline fun <reified T> ObjectMapper.readAsTypedValue(json: String): T =
    runCatching { readValue<T>(json, object : TypeReference<T>() {}) }
        .getOrElse { throw DeserializationException(
            json,
            T::class.java.name
        )
        }
