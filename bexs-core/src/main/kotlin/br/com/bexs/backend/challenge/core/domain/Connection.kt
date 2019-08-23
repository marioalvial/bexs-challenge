package br.com.bexs.backend.challenge.core.domain

data class Connection(
    val source: Airport,
    val destination: Airport,
    val price: Int
)