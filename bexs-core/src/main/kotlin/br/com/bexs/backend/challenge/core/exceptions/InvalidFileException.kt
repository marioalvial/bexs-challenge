package br.com.bexs.backend.challenge.core.exceptions

class InvalidFileException(name: String, line: String) : BexsChallengeCoreException() {

    override val message = "Line [$line] of file $name is in wrong format"
}
