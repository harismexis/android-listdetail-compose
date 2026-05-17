package com.harismexis.listdetail.data.api

import kotlinx.serialization.Serializable

/**
 * API response from Rick and Morty API.
 * The response contains a list of characters and pagination info.
 * Example url:
 * https://rickandmortyapi.com/api/character/?page=2
 * More info:
 * https://rickandmortyapi.com/documentation/
 */
@Serializable
data class ApiResponse(
    val info: Info?,
    val results: List<Character>?,
)

@Serializable
data class Character(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val image: String?
)

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?, // "https://rickandmortyapi.com/api/character/?page=2",
    val prev: String?,
)

fun String?.getValueOrNa(): String {
    return if (!isNullOrEmpty()) {
        this
    } else {
        "N/A"
    }
}
