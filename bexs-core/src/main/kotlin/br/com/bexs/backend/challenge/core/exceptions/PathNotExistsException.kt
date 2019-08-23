package br.com.bexs.backend.challenge.core.exceptions

class PathNotExistsException(
    sourceAirportName: String,
    destinationAirportName: String
) : BexsChallengeCoreException() {

    override val message = "Path from $sourceAirportName to $destinationAirportName does not exists"
}