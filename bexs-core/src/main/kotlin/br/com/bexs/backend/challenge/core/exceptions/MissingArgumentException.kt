package br.com.bexs.backend.challenge.core.exceptions

class MissingArgumentException : BexsChallengeCoreException() {

    override val message = "Missing input file"
}
