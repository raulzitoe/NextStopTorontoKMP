package com.raulvieira.nextstoptoronto.network

import com.raulvieira.nextstoptoronto.data.RouteListResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

interface Repository {
    suspend fun getRouteList(agency: String = "ttc"): Result<RouteListResponse>

}

class RepositoryImpl : Repository {
    val client = httpClient

    override suspend fun getRouteList(agency: String): Result<RouteListResponse> {
        return try {
            val response = client.get(ApiRoutes.ROUTE_LIST) {
                parameter("a", agency)
            }

            if (response.status.isSuccess()) {
                Result.success(response.body())
            } else{
                Result.failure(Exception(response.bodyAsText()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
