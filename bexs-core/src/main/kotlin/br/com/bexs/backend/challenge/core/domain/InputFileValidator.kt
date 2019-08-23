package br.com.bexs.backend.challenge.core.domain

import br.com.bexs.backend.challenge.core.exceptions.InvalidFileException
import java.io.File

object InputFileValidator {

    private val regex = "^([A-Za-z]){3},([A-Za-z]){3},[+]?[0-9][0-9]*\$".toRegex()

    fun validate(file: File) = file.forEachLine { if (!isValid(it)) throw InvalidFileException(file.name, it) }

    private fun isValid(line: String) = line.matches(regex)
}