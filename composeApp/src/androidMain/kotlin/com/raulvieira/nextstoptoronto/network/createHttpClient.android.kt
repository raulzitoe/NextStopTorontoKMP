package com.raulvieira.nextstoptoronto.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual val httpClient: HttpClient = createHttpClient(OkHttp.create())
