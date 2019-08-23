package br.com.bexs.backend.challenge.core.exceptions

class AirportNotExistsException(
    airportName: String
) : BexsChallengeCoreException() {

    override val message = "Airport [$airportName] does not exists"
}