package br.com.bexs.backend.challenge.api.core.route

data class RouteRequest(
    val source: String,
    val destination: String,
    val price: Int
)