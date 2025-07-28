package com.raulvieira.nextstoptoronto.data

import com.raulvieira.nextstoptoronto.domain.model.Line
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteListResponse(
    @SerialName("route")
    val routes: List<RouteLineModel>
) {
    fun toDomain() = routes.map { it.toDomain() }
}

@Serializable
data class RouteLineModel(
    @SerialName("tag")
    val routeTag: String,
    val title: String
) {
    fun toDomain() = Line(
        title = title
    )
}
