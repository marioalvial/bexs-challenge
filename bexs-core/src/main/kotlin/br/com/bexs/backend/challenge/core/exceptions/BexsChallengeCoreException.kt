package br.com.bexs.backend.challenge.core.exceptions

abstract class BexsChallengeCoreException : RuntimeException() {

    abstract override val message: String
}