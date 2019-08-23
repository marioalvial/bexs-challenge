package br.com.bexs.backend.challenge.api.core.route

import br.com.bexs.backend.challenge.core.domain.Route

interface RouteManager {

    fun save(source: String, destination: String, price: Int)

    fun findBestRoute(source: String, destination: String): Route
}