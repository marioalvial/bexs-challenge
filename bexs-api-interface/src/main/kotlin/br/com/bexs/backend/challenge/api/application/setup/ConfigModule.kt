package br.com.bexs.backend.challenge.api.application.setup

import org.koin.dsl.module

object ConfigModule {

    val module = module {
        single { ObjectMapperProvider.provide() }
    }
}
