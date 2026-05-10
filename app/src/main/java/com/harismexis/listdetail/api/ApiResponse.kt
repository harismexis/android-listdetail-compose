package com.harismexis.listdetail.api

/**
 * API response from Rick and Morty API.
 * https://rickandmortyapi.com/documentation
 * Example url:
 * https://rickandmortyapi.com/api/character/?page=2
 */
data class ApiResponse(
    val info: Info?,
    val results: List<Character>?,
)

data class Character(
    val id: Int?,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val image: String?
)

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



