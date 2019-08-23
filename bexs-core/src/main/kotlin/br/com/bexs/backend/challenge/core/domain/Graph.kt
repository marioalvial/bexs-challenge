package br.com.bexs.backend.challenge.core.domain

data class Graph(
    val airports: Set<Airport>,
    val connections: Set<Connection>
)