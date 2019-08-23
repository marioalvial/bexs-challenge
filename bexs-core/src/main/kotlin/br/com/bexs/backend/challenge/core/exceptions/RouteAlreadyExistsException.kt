package br.com.bexs.backend.challenge.core.exceptions

class RouteAlreadyExistsException(
    val source: String,
    val destination: String
) : BexsChallengeCoreException() {

    override val message = "Route from $source to $destination already exist"
}
