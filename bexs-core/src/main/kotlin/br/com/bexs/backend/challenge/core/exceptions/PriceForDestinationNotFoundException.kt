package br.com.bexs.backend.challenge.core.exceptions

import br.com.bexs.backend.challenge.core.domain.Airport

class PriceForDestinationNotFoundException(
    source: Airport,
    destination: Airport
) : BexsChallengeCoreException() {

    override val message = "It wasn't possible to calculate price from ${source.name} to ${destination.name}"
}