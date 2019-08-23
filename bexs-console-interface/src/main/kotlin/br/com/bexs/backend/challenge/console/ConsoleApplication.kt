package br.com.bexs.backend.challenge.console

import br.com.bexs.backend.challenge.core.exceptions.MissingArgumentException
import java.util.Scanner

fun main(args: Array<String>) {
    require(args.isNotEmpty()) { throw MissingArgumentException() }

    val scanner = Scanner(System.`in`)

    ConsoleInterface(scanner).start(args[0])
}