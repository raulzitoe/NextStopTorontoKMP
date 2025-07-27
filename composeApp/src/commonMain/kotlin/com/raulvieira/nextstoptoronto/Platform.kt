package com.raulvieira.nextstoptoronto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform