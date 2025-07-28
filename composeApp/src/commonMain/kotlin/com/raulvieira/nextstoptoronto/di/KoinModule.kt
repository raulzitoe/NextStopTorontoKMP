package com.raulvieira.nextstoptoronto.di

import com.raulvieira.nextstoptoronto.ui.home.HomeViewModel
import com.raulvieira.nextstoptoronto.network.Repository
import com.raulvieira.nextstoptoronto.network.RepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val koinModule = module {
    singleOf(::RepositoryImpl) { bind<Repository>() }
    viewModelOf(::HomeViewModel)
}
